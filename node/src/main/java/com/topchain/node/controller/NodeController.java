package com.topchain.node.controller;

import com.topchain.node.entity.NodeInfo;
import com.topchain.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodeController {
    private NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping("/info")
    public NodeInfo getNodeInfo() {
        return nodeService.getNodeInfo();
    }
}
