package com.topchain.node.model.viewModel;

import java.util.Date;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class PendingBlockViewModel {
    private Long index;
    private Integer transactionsIncluded;
    private Long difficulty;
    private Date dateCreated;
    private Long expectedReward;
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

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getExpectedReward() {
        return expectedReward;
    }

    public void setExpectedReward(Long expectedReward) {
        this.expectedReward = expectedReward;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }

    @Override
    public String toString() {
        return "{" +
                "index:" + index +
                ",transactionsIncluded:" + transactionsIncluded +
                ",difficulty:" + difficulty +
                ",dateCreated:" + dateCreated +
                ",expectedReward:" + expectedReward +
                ",blockDataHash:'" + blockDataHash +
                '}';
    }
}
