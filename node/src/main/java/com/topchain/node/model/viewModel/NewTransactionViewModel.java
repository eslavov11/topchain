package com.topchain.node.model.viewModel;

public class NewTransactionViewModel {
    private String transactionHash;

    public NewTransactionViewModel() {
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }
}
