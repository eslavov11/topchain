package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class NodeServiceImpl implements NodeService {
    private NodeInfoViewModel nodeInfoViewModel;
    private Node node;

    @Autowired
    public NodeServiceImpl(NodeInfoViewModel nodeInfoViewModel, Node node) {
        this.nodeInfoViewModel = nodeInfoViewModel;
        this.node = node;
    }

    @Override
    public NodeInfoViewModel getNodeInfo() {
        this.nodeInfoViewModel.setBlocks(this.node.getBlocks().size());
        this.nodeInfoViewModel.setPeers(this.node.getPeers().size());
        this.nodeInfoViewModel.setDifficulty(this.node.getDifficulty());
        //TODO: sum all difficulties from all blocks?
        // this.nodeInfoViewModel.setCumulativeDifficulty();
        this.nodeInfoViewModel.setPendingTransactions(this.node
                .getPendingTransactions().stream().filter(pt -> !pt.getTransferSuccessful())
                .collect(Collectors.toSet()).size());
        this.nodeInfoViewModel.setConfirmedTransactions(this.node
                .getPendingTransactions().stream().filter(Transaction::getTransferSuccessful)
                .collect(Collectors.toSet()).size());

        return this.nodeInfoViewModel;
    }
}
