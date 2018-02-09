package com.topchain.node.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String fromAddress;
    private String toAddress;
    private BigDecimal amount;
    private String senderPublicKey;
    //TODO: [] ?
    private String[] senderSignature;
    private String transactionHash;
    private Date dateReceived;
    private Long minedInBlockIndex;
    private Boolean paid;

    public Transaction() {
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public void setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
    }

    public String[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String[] senderSignature) {
        this.senderSignature = senderSignature;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Long getMinedInBlockIndex() {
        return minedInBlockIndex;
    }

    public void setMinedInBlockIndex(Long minedInBlockIndex) {
        this.minedInBlockIndex = minedInBlockIndex;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
