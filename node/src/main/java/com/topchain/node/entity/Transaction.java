package com.topchain.node.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Transaction {
    private String fromAddress;
    private String toAddress;
    private long value;
    private long fee;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
    private Date dateCreated;
    private String senderPubKey;
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

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public String getSenderPubKey() {
        return senderPubKey;
    }

    public void setSenderPubKey(String senderPublicKey) {
        this.senderPubKey = senderPublicKey;
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
