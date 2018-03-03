package com.topchain.node.entity;

import java.util.*;

public class Node {
    private Set<Peer> peers;
    private List<Block> blocks;
    private Set<Transaction> pendingTransactions;
    private Set<String> pendingTransactionsHashes;
    private int difficulty;
    private Map<String, Block> miningJobs;

    public Node() {
        this.setPeers(new HashSet<>());
        this.setBlocks(new ArrayList<>());
        this.setPendingTransactions(new HashSet<>());
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

    public void addBlock(Block block) {
        this.blocks.add(block);
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

    public Set<String> getPendingTransactionsHashes() {
        return pendingTransactionsHashes;
    }

    public void setPendingTransactionsHashes(Set<String> pendingTransactionsHashes) {
        this.pendingTransactionsHashes = pendingTransactionsHashes;
    }

    public void addPendingTransactionsHashes(String transactionHash) {
        this.pendingTransactionsHashes.add(transactionHash);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Map<String, Block> getMiningJobs() {
        return miningJobs;
    }

    public void setMiningJobs(Map<String, Block> miningJobs) {
        this.miningJobs = miningJobs;
    }

    public void addMiningJob(String address, Block block) {
        this.miningJobs.put(address, block);
    }
}
