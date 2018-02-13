package com.topchain.node.entity;

import java.util.Date;

public class Transaction {
    private String fromAddress;
    private String toAddress;
    private Long value;
    private Long fee;
    private Date dateCreated;
    private String senderPublicKey;
    private String[] senderSignature;
    private String transactionHash;
    private int minedInBlockIndex;
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

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
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

    public int getMinedInBlockIndex() {
        return minedInBlockIndex;
    }

    public void setMinedInBlockIndex(int minedInBlockIndex) {
        this.minedInBlockIndex = minedInBlockIndex;
    }

    public Boolean getTransferSuccessful() {
        return transferSuccessful;
    }

    public void setTransferSuccessful(Boolean transferSuccessful) {
        this.transferSuccessful = transferSuccessful;
    }
}
