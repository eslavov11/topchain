package com.topchain.node.service;

import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.model.viewModel.TransactionsForAddressViewModel;

import java.util.Set;

public interface TransactionService {
    NewTransactionViewModel createTransaction(TransactionModel transactionModel);

    TransactionViewModel getTransactionByHash(String hash);

    FullBalanceViewModel getBalanceByAddress(String address);

    Set<TransactionViewModel> getPendingTransactions();

    Set<TransactionViewModel> getConfirmedTransactions();

    TransactionsForAddressViewModel getTransactionsForAddress(String address);
}
