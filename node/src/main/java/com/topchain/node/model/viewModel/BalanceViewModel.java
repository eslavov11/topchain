package com.topchain.node.model.viewModel;

public class BalanceViewModel {
    private int confirmations;
    private long balance;

    public BalanceViewModel() {
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
