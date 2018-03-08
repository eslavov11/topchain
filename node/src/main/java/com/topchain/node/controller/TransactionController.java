package com.topchain.node.controller;

import com.topchain.node.entity.Node;
import com.topchain.node.entity.Peer;
import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.bindingModel.TransactionModel;
import com.topchain.node.model.viewModel.FullBalanceViewModel;
import com.topchain.node.model.viewModel.NewTransactionViewModel;
import com.topchain.node.model.viewModel.TransactionViewModel;
import com.topchain.node.model.viewModel.TransactionsForAddressViewModel;
import com.topchain.node.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.topchain.node.util.NodeUtils.getServerURL;
import static com.topchain.node.util.NodeUtils.serializeJSON;

@RestController
public class TransactionController {
    private TransactionService transactionService;
    private RestTemplate restTemplate;
    private Node node;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 RestTemplate restTemplate,
                                 Node node) {
        this.transactionService = transactionService;
        this.restTemplate = restTemplate;
        this.node = node;
    }

    @PostMapping("/transactions/send")
    public ResponseEntity<NewTransactionViewModel> crateTransaction(
            @RequestBody TransactionModel transactionModel) {
        NewTransactionViewModel newTransactionViewModel =
                this.transactionService.createTransaction(transactionModel);

        if (newTransactionViewModel.getTransactionHash() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Notify all peers about new transaction
        this.node.getPeers().forEach(p ->
                new Thread(new TransactionNotifyPeersRunnable(transactionModel, p))
                        .start());

        return new ResponseEntity<>(newTransactionViewModel, HttpStatus.OK);
    }

    @GetMapping("/transactions/{hash}")
    public ResponseEntity<TransactionViewModel> getTransaction(
            @PathVariable String hash) {
        TransactionViewModel transactionViewModel = this.transactionService
                .getTransactionByHash(hash);

        if (!transactionViewModel.isExists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(transactionViewModel, HttpStatus.OK);
    }

    @GetMapping("/address/{address}/balance")
    public ResponseEntity<FullBalanceViewModel> getBalanceByAddressForConfirmations(
            @PathVariable String address) {
        FullBalanceViewModel fullBalanceViewModel = this.transactionService
                .getBalanceByAddress(address);

        if (!fullBalanceViewModel.isExists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(fullBalanceViewModel, HttpStatus.OK);
    }

    @GetMapping("/transactions/pending")
    public Set<TransactionViewModel> getPendingTransaction() {
        return this.transactionService.getPendingTransactions();
    }

    @GetMapping("/transactions/confirmed")
    public Set<TransactionViewModel> getConfirmedTransaction() {
        return this.transactionService.getConfirmedTransactions();
    }

    @GetMapping("/address/{address}/transactions")
    public ResponseEntity<TransactionsForAddressViewModel> getTransactionsForAddress(@PathVariable String address) {
        TransactionsForAddressViewModel transactionsForAddressViewModel =
                this.transactionService.getTransactionsForAddress(address);
        if (transactionsForAddressViewModel.getTransactions().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(transactionsForAddressViewModel, HttpStatus.OK);
    }

    private class TransactionNotifyPeersRunnable implements Runnable {
        private TransactionModel transactionModel;
        private Peer peer;

        public TransactionNotifyPeersRunnable(TransactionModel transactionModel, Peer peer) {
            this.transactionModel = transactionModel;
        }

        public void run() {
            sendNewTransactionToPeers(this.transactionModel, this.peer);
        }
    }

    private void sendNewTransactionToPeers(TransactionModel transactionModel,
                                           Peer peer) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                serializeJSON(transactionModel, false), httpHeaders);

        ResponseEntity<String> response = this.restTemplate
                .postForEntity(peer.getUrl() + "/transactions/send", request, String.class);
    }
}
