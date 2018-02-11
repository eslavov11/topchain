package com.topchain.node.model.viewModel;

public class FullBalanceViewModel {
    private String address;
    private BalanceViewModel confirmedBalance;
    private BalanceViewModel lastMinedBalance;
    private BalanceViewModel pendingBalance;

    public FullBalanceViewModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BalanceViewModel getConfirmedBalance() {
        return confirmedBalance;
    }

    public void setConfirmedBalance(BalanceViewModel confirmedBalance) {
        this.confirmedBalance = confirmedBalance;
    }

    public BalanceViewModel getLastMinedBalance() {
        return lastMinedBalance;
    }

    public void setLastMinedBalance(BalanceViewModel lastMinedBalance) {
        this.lastMinedBalance = lastMinedBalance;
    }

    public BalanceViewModel getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(BalanceViewModel pendingBalance) {
        this.pendingBalance = pendingBalance;
    }
}
