package com.topchain.node.model.viewModel;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class BlockCandidateViewModel {
    private int index;
    private Integer transactionsIncluded;
    private int difficulty;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
//    private Date dateCreated;

    private String rewardAddress;
    private long expectedReward;
    private String blockDataHash;

    public BlockCandidateViewModel() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getTransactionsIncluded() {
        return transactionsIncluded;
    }

    public void setTransactionsIncluded(Integer transactionsIncluded) {
        this.transactionsIncluded = transactionsIncluded;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getRewardAddress() {
        return rewardAddress;
    }

    public void setRewardAddress(String rewardAddress) {
        this.rewardAddress = rewardAddress;
    }

    public long getExpectedReward() {
        return expectedReward;
    }

    public void setExpectedReward(long expectedReward) {
        this.expectedReward = expectedReward;
    }

    public String getBlockDataHash() {
        return blockDataHash;
    }

    public void setBlockDataHash(String blockDataHash) {
        this.blockDataHash = blockDataHash;
    }
}
