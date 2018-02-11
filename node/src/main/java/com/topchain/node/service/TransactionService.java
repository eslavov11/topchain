package com.topchain.node.service;

import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.BalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;

public interface TransactionService {
    NewTransactionViewModel createTransaction(TransactionModel transactionModel);

    TransactionViewModel getTransactionByFromAddress(String fromAddress);

    BalanceViewModel getBalanceByAddressForConfirmations(String address, int confirmations);
}
