package com.topchain.node.entity;

public class NodeInfo {
    private String about;
    private String nodeName;
    private Integer peers;
    private Integer blocks;
    private Long confirmedTransactions;
    private Integer pendingTransactions;
    private Long addresses;
    private Long coins;

    public NodeInfo() {
    }

    public NodeInfo(String about, String nodeName, Integer peers,
                    Integer blocks, Long confirmedTransactions,
                    Integer pendingTransactions, Long addresses,
                    Long coins) {
        this.setAbout(about);
        this.setNodeName(nodeName);
        this.setPeers(peers);
        this.setBlocks(blocks);
        this.setConfirmedTransactions(confirmedTransactions);
        this.setPendingTransactions(pendingTransactions);
        this.setAddresses(addresses);
        this.setCoins(coins);
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getPeers() {
        return peers;
    }

    public void setPeers(Integer peers) {
        this.peers = peers;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Long getConfirmedTransactions() {
        return confirmedTransactions;
    }

    public void setConfirmedTransactions(Long confirmedTransactions) {
        this.confirmedTransactions = confirmedTransactions;
    }

    public Integer getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(Integer pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public Long getAddresses() {
        return addresses;
    }

    public void setAddresses(Long addresses) {
        this.addresses = addresses;
    }

    public Long getCoins() {
        return coins;
    }

    public void setCoins(Long coins) {
        this.coins = coins;
    }
}