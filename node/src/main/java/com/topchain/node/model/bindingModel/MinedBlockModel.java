package com.topchain.node.model.bindingModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class MinedBlockModel {
    private long nonce;

    private String dateCreated;
    private String blockDataHash;

    public MinedBlockModel() {
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }
}
