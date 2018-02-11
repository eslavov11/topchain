package com.topchain.node.serviceImpl;

import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
    private NodeInfoViewModel nodeInfoViewModel;

    @Autowired
    public NodeServiceImpl(NodeInfoViewModel nodeInfoViewModel) {
        this.nodeInfoViewModel = nodeInfoViewModel;
    }


    @Override
    public NodeInfoViewModel getNodeInfo() {
        //this.nodeInfoViewModel.set
        this.nodeInfoViewModel.setAddresses(10L);

        return this.nodeInfoViewModel;
    }
}
