package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.BalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public TransactionServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    @Override
    public NewTransactionViewModel createTransaction(TransactionModel transactionModel) {

        //TODO: validate pubK -> address == pAddress?

        Transaction transaction = this.modelMapper.map(transactionModel, Transaction.class);
        this.node.addPendingTransaction(transaction);

        NewTransactionViewModel newTransactionViewModel = new NewTransactionViewModel();
        newTransactionViewModel.setTransactionHash("");

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

        TransactionViewModel transactionViewModel = this.modelMapper.map(transaction, TransactionViewModel.class);

        return transactionViewModel;
    }

    @Override
    public BalanceViewModel getBalanceByAddressForConfirmations(String address,
                                                                int confirmations) {
        return null;
    }
}
