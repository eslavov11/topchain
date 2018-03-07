package com.topchain.node.model.viewModel;

public class NodeInfoViewModel {
    private String about;
    private String nodeUrl;
    private String nodeName;
    private int peers;
    private int difficulty;
    private int blocks;
    private long cumulativeDifficulty;
    private int confirmedTransactions;
    private int pendingTransactions;

    public NodeInfoViewModel() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getPeers() {
        return peers;
    }

    public void setPeers(int peers) {
        this.peers = peers;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getBlocks() {
        return blocks;
    }

    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    public long getCumulativeDifficulty() {
        return cumulativeDifficulty;
    }

    public void setCumulativeDifficulty(long cumulativeDifficulty) {
        this.cumulativeDifficulty = cumulativeDifficulty;
    }

    public int getConfirmedTransactions() {
        return confirmedTransactions;
    }

    public void setConfirmedTransactions(int confirmedTransactions) {
        this.confirmedTransactions = confirmedTransactions;
    }

    public int getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(int pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

//    public Long getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(Long addresses) {
//        this.addresses = addresses;
//    }
//
//    public Long getCoins() {
//        return coins;
//    }
//
//    public void setCoins(Long coins) {
//        this.coins = coins;
//    }
}
