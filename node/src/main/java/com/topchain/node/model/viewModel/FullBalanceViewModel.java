package com.topchain.node.model.viewModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FullBalanceViewModel {
    private String address;
    private BalanceViewModel confirmedBalance;
    private BalanceViewModel lastMinedBalance;
    private BalanceViewModel pendingBalance;

    @JsonIgnore
    private boolean isExists;

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

    public boolean isExists() {
        return isExists;
    }

    public void setExists(boolean exists) {
        isExists = exists;
    }
}
