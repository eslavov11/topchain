package com.topchain.node.entity;

import java.util.Map;
import java.util.Set;

public class Node {
    private Set<Peer> peers;
    private Set<Block> blocks;
    private Set<Transaction> transactions;
    private Map<String, Long> balances;
    private Long difficulty;
    private Map<String, Block> miningJobs;

    public Node() {
    }

    public Set<Peer> getPeers() {
        return peers;
    }

    public void setPeers(Set<Peer> peers) {
        this.peers = peers;
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Map<String, Long> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Long> balances) {
        this.balances = balances;
    }

    public Long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Long difficulty) {
        this.difficulty = difficulty;
    }

    public Map<String, Block> getMiningJobs() {
        return miningJobs;
    }

    public void setMiningJobs(Map<String, Block> miningJobs) {
        this.miningJobs = miningJobs;
    }
}
