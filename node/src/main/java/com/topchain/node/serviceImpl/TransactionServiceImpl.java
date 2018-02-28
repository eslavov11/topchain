package com.topchain.node.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.topchain.node.util.NodeUtils.hashText;
import static com.topchain.node.util.NodeUtils.serializeJSON;

@Service
public class TransactionServiceImpl implements TransactionService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public TransactionServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    /** @Method: creates pending transaction
     * For each received transaction the Node does the following:
     * Calculates the transaction hash
     * Checks for collisions → duplicated transactions are skipped
     * Checks for missing / invalid fields
     * Validates the transaction signature
     * Puts the transaction in the "pending transactions" pool
     * Sends the transaction to all peer nodes through the REST API
     * The transaction is sent from peer to peer until it reaches the entire
     network
     * */
    @Override
    public NewTransactionViewModel createTransaction(TransactionModel transactionModel) {
        Transaction transaction = this.modelMapper.map(transactionModel, Transaction.class);
        NewTransactionViewModel newTransactionViewModel = new NewTransactionViewModel();
        String transactionHash = hashText(serializeJSON(transactionModel, false));
        if (!transactionIsValid(transactionModel, transactionHash)) {
            return newTransactionViewModel;
        }

        //TODO: validate pubK -> address == pAddress?

        transaction.setTransactionHash(transactionHash);
        newTransactionViewModel.setTransactionHash(transactionHash);
        this.node.addPendingTransaction(transaction);
        this.node.addPendingTransactionsHashes(transactionHash);
        //TODO: on block creation remove pending transactions & hashes

        return newTransactionViewModel;
    }

    @Override
    public TransactionViewModel getTransactionByFromAddress(String fromAddress) {
        Optional<Transaction> transaction = Optional.empty();
        for (Block block : this.node.getBlocks()) {
            if (transaction.isPresent()) {
                break;
            }

            transaction = block.getTransactions().stream()
                    .filter(t -> t.getFromAddress().equals(fromAddress)).findAny();
        }

        if (!transaction.isPresent()) {
            transaction = this.node.getPendingTransactions().stream()
                    .filter(t -> t.getFromAddress().equals(fromAddress)).findAny();
        }

        //TODO: if pending -> successful & blockIndex not shown!!
        TransactionViewModel transactionViewModel =
                this.modelMapper.map(transaction, TransactionViewModel.class);

        return transactionViewModel;
    }

    @Override
    public FullBalanceViewModel getBalanceByAddressForConfirmations(String address,
                                                                    int confirmations) {
        /** The address balance is calculated by iterating over all transactions
         For each block and for each successful transaction for the specified address,
         sum the values received and spent, matching the confirmations count
         * */
        return null;
    }

    public Set<TransactionViewModel> getPendingTransactions(){
        Set<TransactionViewModel> pendingTransactions = new HashSet<>();
        this.modelMapper.map(this.node.getPendingTransactions(), pendingTransactions);

        return pendingTransactions;
    }

    public Set<TransactionViewModel> getConfirmedTransactions(){
        Set<TransactionViewModel> confirmedTransactions = new HashSet<>();

        this.node.getBlocks().forEach((block) -> {
            block.getTransactions().forEach((tx)->{
                if(tx.getTransferSuccessful()){
                    TransactionViewModel txVM = this.modelMapper.map(tx,TransactionViewModel.class);
                    confirmedTransactions.add(txVM);
                }
            });
        });

        return confirmedTransactions;
    }

    private boolean transactionIsValid(TransactionModel transactionModel,
                                       String transactionHash) {
        return !this.node.getPendingTransactionsHashes().contains(transactionHash);
    }
}
