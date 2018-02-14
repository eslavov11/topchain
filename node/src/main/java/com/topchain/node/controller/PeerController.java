package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static com.topchain.node.util.NodeUtils.getServerURL;
import static com.topchain.node.util.NodeUtils.serializeJSON;
import static com.topchain.node.util.NodeUtils.serverPort;

@RestController
public class PeerController {
    private PeerService peerService;
    private NodeInfoViewModel nodeInfoViewModel;

    @Autowired
    public PeerController(PeerService peerService, NodeInfoViewModel nodeInfoViewModel) {
        this.peerService = peerService;
        this.nodeInfoViewModel = nodeInfoViewModel;
    }

    @GetMapping("/peers")
    public Set<PeerViewModel> getPeers() {
        return this.peerService.getPeers();
    }

    @PostMapping("/peers")
    public ResponseMessageViewModel addPeer(@RequestBody PeerModel peerModel) {
        ResponseMessageViewModel responseMessageViewModel =
                this.peerService.addPeer(peerModel);
        if (!responseMessageViewModel.isExists()) {
            // make connection bidirectional
            connectToPeer(peerModel);

            // check for longer chain
            getLongestChain(peerModel);
        }

        return responseMessageViewModel;
    }

    private void getLongestChain(PeerModel peerModel) {
        if (!peerChainIsLonger(peerModel)) {

        }

    }

    private boolean peerChainIsLonger(PeerModel peerModel) {
        // TODO: rest tepmlate @bean
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<NodeInfoViewModel> entity = restTemplate.getForEntity(peerModel.getUrl() + ":" + serverPort, NodeInfoViewModel.class);

        NodeInfoViewModel peerInfo = entity.getBody();
        return peerInfo.getCumulativeDifficulty() > this.nodeInfoViewModel.getCumulativeDifficulty();
    }

    private void connectToPeer(PeerModel peerModel) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        PeerModel nodePeerModel = new PeerModel();
        nodePeerModel.setUrl(getServerURL());

        HttpEntity<String> request = new HttpEntity<>(
                serializeJSON(nodePeerModel, false), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .postForEntity(peerModel.getUrl(), request, String.class);
    }
}
