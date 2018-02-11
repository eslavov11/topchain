package com.topchain.node.model.bindingModel;

public class NotifyBlockModel {
    private Long index;
    private Long cumulativeDifficulty;

    public NotifyBlockModel() {
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Long getCumulativeDifficulty() {
        return cumulativeDifficulty;
    }

    public void setCumulativeDifficulty(Long cumulativeDifficulty) {
        this.cumulativeDifficulty = cumulativeDifficulty;
    }
}
