package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.topchain.node.util.NodeUtils.getServerURL;
import static com.topchain.node.util.NodeUtils.serializeJSON;
import static com.topchain.node.util.NodeUtils.SERVER_PORT;

@RestController
public class PeerController {
    private static final Logger logger = Logger.getLogger(PeerController.class.getName());

    private PeerService peerService;
    private NodeInfoViewModel nodeInfoViewModel;
    private RestTemplate restTemplate;

    @Autowired
    public PeerController(PeerService peerService, NodeInfoViewModel nodeInfoViewModel, RestTemplate restTemplate) {
        this.peerService = peerService;
        this.nodeInfoViewModel = nodeInfoViewModel;
        this.restTemplate = restTemplate;
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
            // check for longer chain
            setLongestChain(peerModel);

            // make connection bidirectional
            connectToPeer(peerModel);
        }

        return responseMessageViewModel;
    }

    private void setLongestChain(PeerModel peerModel) {
        if (!peerChainIsLonger(peerModel) || !peerBlocksAreValid(peerModel)) {
            return;
        }

        // TODO: Update current node chain
    }

    private boolean peerBlocksAreValid(PeerModel peerModel) {
        HttpEntity<List<BlockViewModel>> entity = this.restTemplate
                .exchange(peerModel.getUrl() + "/blocks", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BlockViewModel>>() {
                        });
        List<BlockViewModel> blockViewModels = entity.getBody();


        return false;
    }

    private boolean peerChainIsLonger(PeerModel peerModel) {
        HttpEntity<NodeInfoViewModel> entity = this.restTemplate
                .getForEntity(peerModel.getUrl() + "/info",
                        NodeInfoViewModel.class);
        NodeInfoViewModel peerInfo = entity.getBody();

        return peerInfo.getCumulativeDifficulty() >
                this.nodeInfoViewModel.getCumulativeDifficulty();
    }

    private void connectToPeer(PeerModel peerModel) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        PeerModel nodePeerModel = new PeerModel();
        nodePeerModel.setUrl(getServerURL()); // "http://127.0.0.1:8080"  getServerURL()

        HttpEntity<String> request = new HttpEntity<>(
                serializeJSON(nodePeerModel, false), httpHeaders);

        logger.severe("me(nodepeermodel url)" + nodePeerModel.getUrl());
        logger.severe("send to peerModel.getURl: " + peerModel.getUrl() + "/peers");

        ResponseEntity<String> response = this.restTemplate
                .postForEntity(peerModel.getUrl() + "/peers", request, String.class);

    }
}
