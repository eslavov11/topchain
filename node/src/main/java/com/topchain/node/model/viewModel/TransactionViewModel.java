package com.topchain.node.model.viewModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class TransactionViewModel {
    private String fromAddress;
    private String toAddress;
    private long value;
    private long fee;
    private Date dateCreated;
    private String senderPubKey;
    private String[] senderSignature;
    private String transactionHash;
    private long minedInBlockIndex;
    private Boolean transferSuccessful;

    @JsonIgnore
    private boolean exists;

    public TransactionViewModel() {
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getSenderPubKey() {
        return senderPubKey;
    }

    public void setSenderPubKey(String senderPublicKey) {
        this.senderPubKey = senderPubKey;
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

    public long getMinedInBlockIndex() {
        return minedInBlockIndex;
    }

    public void setMinedInBlockIndex(long minedInBlockIndex) {
        this.minedInBlockIndex = minedInBlockIndex;
    }

    public Boolean getTransferSuccessful() {
        return transferSuccessful;
    }

    public void setTransferSuccessful(Boolean transferSuccessful) {
        this.transferSuccessful = transferSuccessful;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
