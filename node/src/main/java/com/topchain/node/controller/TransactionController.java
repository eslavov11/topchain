package com.topchain.node.controller;

import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions/{fromAddress}/info")
    public TransactionViewModel getBlockByIndex(@PathVariable String fromAddress) {
        return this.transactionService.getTransactionByFromAddress(fromAddress);
    }
}
