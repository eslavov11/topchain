package com.topchain.node.model.viewModel;

import java.math.BigDecimal;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class PendingBlockViewModel {
    private Long index;
    private Integer transactionsIncluded;
    private BigDecimal expectedReward;
    private String blockDataHash;

    public PendingBlockViewModel() {
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Integer getTransactionsIncluded() {
        return transactionsIncluded;
    }

    public void setTransactionsIncluded(Integer transactionsIncluded) {
        this.transactionsIncluded = transactionsIncluded;
    }

    public BigDecimal getExpectedReward() {
        return expectedReward;
    }

    public void setExpectedReward(BigDecimal expectedReward) {
        this.expectedReward = expectedReward;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }
}
