package com.topchain.node.model.viewModel;

import com.topchain.node.entity.Transaction;

import java.util.List;

public class TransactionsForAddressViewModel {
    private String address;
    private List<Transaction> transactions;

    public TransactionsForAddressViewModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
