package com.topchain.node.entity;

import java.util.*;

public class Node {
    private Set<Peer> peers;
    private List<Block> blocks;
    private Set<Transaction> pendingTransactions;
    private Long difficulty;
    private Map<String, Block> miningJobs;
    private Map<String, Long> balances;

    public Node() {
        this.setPeers(new HashSet<>());
        this.setBlocks(new ArrayList<>());
        this.setPendingTransactions(new HashSet<>());
        this.setBalances(new HashMap<>());
        this.setMiningJobs(new HashMap<>());
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

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public void addBlock(Block blocks) {
        this.blocks.add(blocks);
    }

    public Set<Transaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(Set<Transaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public void addPendingTransaction(Transaction transaction) {
        this.pendingTransactions.add(transaction);
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
