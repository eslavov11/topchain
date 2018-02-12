package com.topchain.node.model.viewModel;

public class BalanceViewModel {
    private Integer confirmations;
    private Long balance;

    public BalanceViewModel() {
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
