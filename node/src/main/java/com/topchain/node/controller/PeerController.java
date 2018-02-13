package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.PeerModel;
import com.topchain.node.model.viewModel.PeerViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.PeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import static com.topchain.node.util.NodeUtils.serializeJSON;

@RestController
public class PeerController {
    @Value("${server.port}")
    private String serverPort;

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
        ResponseMessageViewModel responseMessageViewModel =
                this.peerService.addPeer(peerModel);
        if (!responseMessageViewModel.isExists()) {
            connectToPeer(peerModel);
        }

        return responseMessageViewModel;
    }

    private void connectToPeer(PeerModel peerModel) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        PeerModel nodePeerModel = new PeerModel();
        try {
            nodePeerModel.setUrl("http://" +
                    InetAddress.getLocalHost().getHostAddress() + ":" +
                    this.serverPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(
                serializeJSON(nodePeerModel, false), httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate
                .postForEntity(peerModel.getUrl().toString(), request, String.class);
    }
}
