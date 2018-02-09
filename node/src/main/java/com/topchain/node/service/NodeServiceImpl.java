package com.topchain.node.service;

import com.topchain.node.entity.NodeInfo;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
    @Override
    public NodeInfo getNodeInfo() {
        return new NodeInfo("topchain node",
                "Sofia-1",
                2,
                25,
                200L,
                7,
                12L,
                20000L);
    }
}
