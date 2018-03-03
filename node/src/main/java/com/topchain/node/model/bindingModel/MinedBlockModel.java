package com.topchain.node.model.bindingModel;

import java.util.Date;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class MinedBlockModel {
    private Long nonce;
    private Date dateCreated;
    private String blockDataHash;

    public MinedBlockModel() {
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }
}
