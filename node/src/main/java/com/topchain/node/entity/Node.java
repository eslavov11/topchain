package com.topchain.node.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Node {
    private Set<Peer> peers;
    private Set<Block> blocks;
    private Set<Transaction> pendingTransactions;
    private Long difficulty;
    private Map<String, Block> miningJobs;
    private Map<String, Long> balances;

    public Node() {
        this.peers = new HashSet<>();
        this.blocks = new HashSet<>();
        this.pendingTransactions = new HashSet<>();
        this.balances = new HashMap<>();
        this.miningJobs = new HashMap<>();
    }

    public Set<Peer> getPeers() {
        return peers;
    }

    public void setPeers(Set<Peer> peers) {
        this.peers = peers;
    }

    public void addPeer(Peer peer) {
        this.peers.add(peer);
    }

    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public Set<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(Set<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
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

    public Map<String, Long> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Long> balances) {
        this.balances = balances;
    }
}
