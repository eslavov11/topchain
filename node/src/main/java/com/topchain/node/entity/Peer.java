package com.topchain.node.entity;

import java.net.URL;

public class Peer {
    private URL address;

    public Peer() {}

    public URL getAddress() {
        return address;
    }

    public void setAddress(URL address) {
        this.address = address;
    }
}
