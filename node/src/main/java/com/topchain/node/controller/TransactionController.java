package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions/send")
    public ResponseEntity<NewTransactionViewModel> crateTransaction(
            @RequestBody TransactionModel transactionModel) {
        NewTransactionViewModel newTransactionViewModel =
                this.transactionService.createTransaction(transactionModel);

        if (newTransactionViewModel.getTransactionHash() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(newTransactionViewModel, HttpStatus.OK);
    }

    @GetMapping("/transactions/{fromAddress}/info")
    public TransactionViewModel getTransactionByFromAddress(
            @PathVariable String fromAddress) {
        return this.transactionService.getTransactionByFromAddress(fromAddress);
    }

    @GetMapping("/balance/{address}/confirmations/{confirmations}")
    public FullBalanceViewModel getBalanceByAddressForConfirmations(@PathVariable String address,
                                                                    @PathVariable int confirmations) {
        //TODO: return status code 200 on every request
        return this.transactionService
                .getBalanceByAddressForConfirmations(address, confirmations);
    }

   @GetMapping("/transactions/pending")
    public Set<TransactionViewModel> getPendingTransaction() {

        return this.transactionService.getPendingTransactions();
    }

    @GetMapping("/transactions/confirmed")
    public Set<TransactionViewModel> getConfirmedTransaction() {

        return this.transactionService.getConfirmedTransactions();
    }

}
