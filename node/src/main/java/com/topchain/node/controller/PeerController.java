package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Set;

@RestController
public class PeerController {
    private PeerService peerService;

    @Autowired
    public PeerController(PeerService peerService) {
        this.peerService = peerService;
    }

    @GetMapping("/peers")
    public Set<PeerViewModel> getPeers() {
        return this.peerService.getPeers();
    }

    @PostMapping("/peers")
    public ResponseMessageViewModel addPeer(@RequestBody PeerModel peerModel) {
        ResponseMessageViewModel responseMessageViewModel = this.peerService.addPeer(peerModel);
        if (responseMessageViewModel.isExists()) {
            connectToPeer(peerModel);
        }

        return responseMessageViewModel;
    }

    private void connectToPeer(PeerModel peerModel) {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection)peerModel.getUrl().openConnection();
            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.set
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
