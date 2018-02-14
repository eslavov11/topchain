package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.BlockCandidateViewModel;
import com.topchain.node.service.MiningService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.topchain.node.util.NodeUtils.hashText;

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
    public BlockCandidateViewModel getBlockCandidate(String minerAddress) {
        //TODO: create block and hash transactions
        Block blockCandidate = new Block();
        blockCandidate.setIndex(this.node.getBlocks().size() + 1);
//        blockCandidate.set
//TODO:    blockCandidate bean??

        this.node.addMiningJob(minerAddress, new Block());

        BlockCandidateViewModel blockCandidateViewModel = new BlockCandidateViewModel();
        blockCandidateViewModel.setIndex(blockCandidate.getIndex());
        blockCandidateViewModel.setDifficulty(this.node.getDifficulty());
        blockCandidateViewModel.setTransactionsIncluded(this.node.getPendingTransactions().size());

        blockCandidateViewModel.setBlockDataHash(hashText(blockCandidateViewModel.toString()));

        return blockCandidateViewModel;
    }

    @Override
    public MinedBlockStatusViewModel submitBLock(MinedBlockModel minedBlockModel, String minerAddress) {
        return null;
    }
}
