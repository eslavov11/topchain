package com.topchain.node.service;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;

import java.util.Set;

public interface PeerService {
    Set<PeerViewModel> getPeers();

    ResponseMessageViewModel addPeer(PeerModel peerModel);
}
