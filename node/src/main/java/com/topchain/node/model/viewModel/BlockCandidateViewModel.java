package com.topchain.node.model.viewModel;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class BlockCandidateViewModel {
    private Long index;
    private Integer transactionsIncluded;
    private Long difficulty;

//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'", timezone="GMT")
//    private Date dateCreated;

    private Long expectedReward;
    private String blockDataHash;

    public BlockCandidateViewModel() {
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
}
