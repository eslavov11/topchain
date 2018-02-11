package com.topchain.node.serviceImpl;

import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.service.NodeService;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
    @Override
    public NodeInfoViewModel getNodeInfo() {
        return new NodeInfoViewModel("topchain node",
                "Sofia-1",
                2,
                25,
                200L,
                7,
                12L,
                20000L);
    }
}
