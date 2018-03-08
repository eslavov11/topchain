package com.topchain.node.controller;

import com.sun.org.apache.bcel.internal.generic.I2F;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Peer;
import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.BlockView;
import java.util.List;

import static com.topchain.node.util.NodeUtils.getServerURL;
import static com.topchain.node.util.NodeUtils.notifyPeersForNewBlock;
import static com.topchain.node.util.NodeUtils.serializeJSON;

@RestController
public class BlockController {
    private BlockService blockService;
    private RestTemplate restTemplate;
    private NodeInfoViewModel nodeInfoViewModel;
    private Node node;


    @Autowired
    public BlockController(BlockService blockService,
                           RestTemplate restTemplate,
                           Node node,
                           NodeInfoViewModel nodeInfoViewModel) {
        this.blockService = blockService;
        this.restTemplate = restTemplate;
        this.node = node;
        this.nodeInfoViewModel = nodeInfoViewModel;
    }

    @GetMapping("/blocks")
    public List<BlockViewModel> getBlocks() {
        return this.blockService.getBlocks();
    }

    @GetMapping("/blocks/{index}")
    public ResponseEntity<BlockViewModel> getBlockByIndex(@PathVariable long index) {
        BlockViewModel blockViewModel = this.blockService.getBlockByIndex(index);

        if (!blockViewModel.isExists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(blockViewModel, HttpStatus.OK);
    }

    @PostMapping("/blocks/notify")
    public ResponseMessageViewModel notify(@RequestBody NotifyBlockModel notifyBlockModel) {
        if (notifyBlockModel.getIndex() == this.node.getBlocks().size() + 1 &&
                notifyBlockModel.getCumulativeDifficulty() >
                        this.nodeInfoViewModel.getCumulativeDifficulty()) {
            setLongestChain(notifyBlockModel.getPeer().getUrl());

            notifyPeersForNewBlock(notifyBlockModel, this.node.getPeers());
        }

        return new ResponseMessageViewModel("Thank you for the notification.");
    }

    private void setLongestChain(String url) {
        if (!peerChainIsLonger(url)) {
            return;
        }

        HttpEntity<List<BlockViewModel>> entity = this.restTemplate
                .exchange(url + "/blocks", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<BlockViewModel>>() {
                        });
        List<BlockViewModel> blockViewModels = entity.getBody();
        if (!this.blockService.peerBlocksAreValid(blockViewModels)) {
            return;
        }

        this.blockService.updateBlockchain(blockViewModels);
    }

    private boolean peerChainIsLonger(String url) {
        HttpEntity<NodeInfoViewModel> entity = this.restTemplate
                .getForEntity(url + "/info",
                        NodeInfoViewModel.class);
        NodeInfoViewModel peerInfo = entity.getBody();

        return peerInfo.getCumulativeDifficulty() >
                this.nodeInfoViewModel.getCumulativeDifficulty();
    }
}
