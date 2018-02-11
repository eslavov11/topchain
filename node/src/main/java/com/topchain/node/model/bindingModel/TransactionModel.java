package com.topchain.node.model.bindingModel;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionModel {
    private String from;
    private String to;
    private BigDecimal value;
    private String senderPubKey;
    private String[] senderSignature;

    public TransactionModel() {
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getSenderPubKey() {
        return senderPubKey;
    }

    public void setSenderPubKey(String senderPubKey) {
        this.senderPubKey = senderPubKey;
    }

    public String[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String[] senderSignature) {
        this.senderSignature = senderSignature;
    }
}
