package com.topchain.node.model.viewModel;

public class PeerViewModel {
    private String url;

    public PeerViewModel() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String address) {
        this.url = address;
    }
}
