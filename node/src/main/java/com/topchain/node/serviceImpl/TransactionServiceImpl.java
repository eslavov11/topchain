package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.*;
import com.topchain.node.service.TransactionService;
import com.topchain.node.util.CryptoUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        transaction.setTransactionHash(transactionHash);
        newTransactionViewModel.setTransactionHash(transactionHash);
        this.node.addPendingTransaction(transaction);
        this.node.addPendingTransactionsHashes(transactionHash);

        return newTransactionViewModel;
    }

    @Override
    public TransactionViewModel getTransactionByHash(String hash) {
        Optional<Transaction> transaction = Optional.empty();
        for (Block block : this.node.getBlocks()) {
            if (transaction.isPresent()) {
                break;
            }

            transaction = block.getTransactions().stream()
                    .filter(t -> t.getFromAddress().equals(hash)).findAny();
        }

        if (!transaction.isPresent()) {
            transaction = this.node.getPendingTransactions().stream()
                    .filter(t -> t.getFromAddress().equals(hash)).findAny();
        }

        //TODO: if pending -> successful & blockIndex not shown!!
        TransactionViewModel transactionViewModel =
                this.modelMapper.map(transaction, TransactionViewModel.class);

        return transactionViewModel;
    }

    @Override
    public FullBalanceViewModel getBalanceByAddress(String address) {

        /** The address balance is calculated by iterating over all transactions
         For each block and for each successful transaction for the specified address,
         sum the values received and spent, matching the confirmations count
         * */
        FullBalanceViewModel ballanceForAddress = new FullBalanceViewModel();
        BalanceViewModel balanceVMForComfirmed = new BalanceViewModel();
        BalanceViewModel balanceVMForPending = new BalanceViewModel();
        BalanceViewModel balanceVMForLastMined = new BalanceViewModel();

        //get confirmed balance for given address
        //TODO:Dont take in account the latest 6 blocks or whatever number it is set to believe the transactions are confirmed
        this.node.getBlocks().forEach(x-> {
            x.getTransactions().forEach(tx-> {
                if(tx.getTransferSuccessful() && tx.getFromAddress() == address){
                    balanceVMForComfirmed.setBalance(balanceVMForComfirmed.getBalance() - tx.getValue());
                }
                if(tx.getTransferSuccessful() && tx.getToAddress() == address){
                    balanceVMForComfirmed.setBalance(balanceVMForComfirmed.getBalance() + tx.getValue());
                }
            });
        });
        //get pending transactions and all of the balance for given address in those transactions
        this.node.getPendingTransactions().forEach(x->{
            if(x.getFromAddress() == address) {
                balanceVMForPending.setBalance(balanceVMForPending.getBalance() - x.getValue());
            }
            if(x.getToAddress()==address){
                balanceVMForPending.setBalance((balanceVMForPending.getBalance()+ x.getValue()));
            }
        });
        //last mined
        //get latest block where toAdress or FromAdress == address and set value for last mined balance
        for (int i = this.node.getBlocks().size() - 1; i >= 0; i--) {
//            if(this.node.getBlocks().)
        }

        ballanceForAddress.setConfirmedBalance(balanceVMForComfirmed);
        ballanceForAddress.setPendingBalance(balanceVMForPending);
        ballanceForAddress.setLastMinedBalance(balanceVMForLastMined);

        return ballanceForAddress;
    }

    @Override
    public Set<TransactionViewModel> getPendingTransactions(){
        Set<TransactionViewModel> pendingTransactions = new HashSet<>();
        this.modelMapper.map(this.node.getPendingTransactions(), pendingTransactions);

        return pendingTransactions;
    }

    @Override
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

    @Override
    public TransactionsForAddressViewModel getTransactionsForAddress(String address) {
        List<Transaction> transactions = new ArrayList<>();
        this.node.getPendingTransactions().forEach(t -> {
            if (t.getFromAddress().equals(address) || t.getToAddress().equals(address)) {
                transactions.add(t);
            }
        });

        this.node.getBlocks().forEach(b -> {
            b.getTransactions().forEach(t -> {
                if (t.getFromAddress().equals(address) || t.getToAddress().equals(address)) {
                    transactions.add(t);
                }
            });
        });

        transactions.sort(Comparator.comparing(Transaction::getDateCreated));

        TransactionsForAddressViewModel transactionsForAddressViewModel = new TransactionsForAddressViewModel();
        transactionsForAddressViewModel.setAddress(address);
        transactionsForAddressViewModel.setTransactions(transactions);

        return transactionsForAddressViewModel;
    }

    private boolean transactionIsValid(TransactionModel transactionModel,
                                       String transactionHash) {
        boolean transactionExists = this.node
                .getPendingTransactionsHashes().contains(transactionHash);

        //TODO: messageJSON
//        boolean verified = CryptoUtils.verifySignature("",
//                transactionModel.getSenderSignature(),
//                transactionModel.getSenderPubKey());

        boolean verified = true;

        return !transactionExists && verified;
    }
}
