package com.topchain.node.model.bindingModel;

public class NotifyBlockModel {
    private long index;
    private long cumulativeDifficulty;

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
}
