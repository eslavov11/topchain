package com.topchain.node.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String fromAddress;
    private String toAddress;
    private BigDecimal value;
    private BigDecimal fee;
    private Date dateCreated;
    private String senderPublicKey;
    private String[] senderSignature;
    private String transactionHash;
    private Long minedInBlockIndex;
    private Boolean transferSuccessful;

    public Transaction() {
        this.setSenderSignature(new String[2]);
        this.setTransferSuccessful(false);
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

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getMinedInBlockIndex() {
        return minedInBlockIndex;
    }

    public void setMinedInBlockIndex(Long minedInBlockIndex) {
        this.minedInBlockIndex = minedInBlockIndex;
    }

    public Boolean getTransferSuccessful() {
        return transferSuccessful;
    }

    public void setTransferSuccessful(Boolean transferSuccessful) {
        this.transferSuccessful = transferSuccessful;
    }
}
