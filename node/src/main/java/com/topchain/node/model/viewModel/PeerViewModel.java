package com.topchain.node.model.viewModel;

import java.net.URL;

public class PeerViewModel {
    private URL address;

    public PeerViewModel() {
    }

    public URL getAddress() {
        return address;
    }

    public void setAddress(URL address) {
        this.address = address;
    }
}
