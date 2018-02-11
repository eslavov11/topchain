package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions/send")
    public NewTransactionViewModel crateTransaction(
            @RequestBody TransactionModel transactionModel) {
        return this.transactionService.createTransaction(transactionModel);
    }

    @GetMapping("/transactions/{fromAddress}/info")
    public TransactionViewModel getTransactionByFromAddress(
            @PathVariable String fromAddress) {
        return this.transactionService.getTransactionByFromAddress(fromAddress);
    }

    @GetMapping("/balance/{address}/confirmations/{confirmations}")
    public FullBalanceViewModel getBalanceByAddressForConfirmations(@PathVariable String address,
                                                                    @PathVariable int confirmations) {
        return this.transactionService
                .getBalanceByAddressForConfirmations(address, confirmations);
    }
}
