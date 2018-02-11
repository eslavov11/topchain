package com.topchain.node.model.viewModel;

import java.math.BigDecimal;

public class BalanceViewModel {
    private Integer confirmations;
    private BigDecimal balance;

    public BalanceViewModel() {
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
