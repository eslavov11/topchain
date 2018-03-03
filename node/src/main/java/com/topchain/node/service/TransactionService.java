package com.topchain.node.service;

import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.model.viewModel.TransactionsForAddressViewModel;

import java.util.Set;

public interface TransactionService {
    NewTransactionViewModel createTransaction(TransactionModel transactionModel);

    TransactionViewModel getTransactionByFromAddress(String fromAddress);

    FullBalanceViewModel getBalanceByAddressForConfirmations(String address, int confirmations);

    Set<TransactionViewModel> getPendingTransactions();

    Set<TransactionViewModel> getConfirmedTransactions();

    TransactionsForAddressViewModel getTransactionsForAddress(String address);
}
