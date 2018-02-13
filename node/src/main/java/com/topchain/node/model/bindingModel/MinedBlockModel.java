package com.topchain.node.model.bindingModel;

import java.util.Date;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class MinedBlockModel {
    private Long nonce;

    public MinedBlockModel() {
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}
