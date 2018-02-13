package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Node;
import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.PendingBlockViewModel;
import com.topchain.node.service.MiningService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MiningServiceImpl implements MiningService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public MiningServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    @Override
    public PendingBlockViewModel getPendingBlock(String minerAddress) {
        PendingBlockViewModel pendingBlockViewModel = new PendingBlockViewModel();
        pendingBlockViewModel.setDateCreated(new Date());
        pendingBlockViewModel.setDifficulty(this.node.getDifficulty());
        pendingBlockViewModel.setTransactionsIncluded(this.node.getPendingTransactions().size());

        return pendingBlockViewModel;
    }

    @Override
    public MinedBlockStatusViewModel submitBLock(MinedBlockModel minedBlockModel, String minerAddress) {
        return null;
    }
}
