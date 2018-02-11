package com.topchain.node.service;

import com.topchain.node.model.viewModel.TransactionViewModel;

public interface TransactionService {
    TransactionViewModel getTransactionByFromAddress(String fromAddress);
}
