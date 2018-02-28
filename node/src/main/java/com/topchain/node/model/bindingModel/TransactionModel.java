package com.topchain.node.model.bindingModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class TransactionModel {
    @Size(min = 40, max = 40)
    private String from;

    @Size(min = 40, max = 40)
    private String to;

    @Size(min = 64, max = 64)
    private String senderPubKey;

    @Min(value = 1)
    private Long value;

    @Min(value = 1)
    private Long fee;

    @NotNull
    private Date dateCreated;

    @Size(min = 2, max = 2)
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

    public String getSenderPubKey() {
        return senderPubKey;
    }

    public void setSenderPubKey(String senderPubKey) {
        this.senderPubKey = senderPubKey;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String[] getSenderSignature() {
        return senderSignature;
    }

    public void setSenderSignature(String[] senderSignature) {
        this.senderSignature = senderSignature;
    }
}
