package com.topchain.node.model.bindingModel;

import com.topchain.node.entity.Peer;

public class NotifyBlockModel {
    private long index;
    private long cumulativeDifficulty;
    private Peer peer;

    public NotifyBlockModel() {
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getCumulativeDifficulty() {
        return cumulativeDifficulty;
    }

    public void setCumulativeDifficulty(long cumulativeDifficulty) {
        this.cumulativeDifficulty = cumulativeDifficulty;
    }

    public Peer getPeer() {
        return peer;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }
}
