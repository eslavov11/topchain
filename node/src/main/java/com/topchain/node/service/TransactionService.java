package com.topchain.node.service;

import com.topchain.node.model.viewModel.BalanceViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;

public interface TransactionService {
    TransactionViewModel getTransactionByFromAddress(String fromAddress);

    BalanceViewModel getBalanceByAddressForConfirmations(String address, int confirmations);
}
