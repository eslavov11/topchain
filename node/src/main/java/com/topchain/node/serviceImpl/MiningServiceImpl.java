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
import static com.topchain.node.util.NodeUtils.serializeJSON;

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
        blockCandidate.setTransactions(this.node.getPendingTransactions());
        blockCandidate.setDifficulty(this.node.getDifficulty());
        blockCandidate.setBlockDataHash(hashText(serializeJSON(blockCandidate, false)));
        blockCandidate.setMinedBy(minerAddress);
        blockCandidate.setPreviousBlockHash(
                this.node.getBlocks().get(this.node.getBlocks().size() - 1).getBlockHash());

        //TODO:    blockCandidate bean??

        this.node.addMiningJob(blockCandidate.getBlockDataHash(), blockCandidate);

        BlockCandidateViewModel blockCandidateViewModel = new BlockCandidateViewModel();
        blockCandidateViewModel.setIndex(blockCandidate.getIndex());
        blockCandidateViewModel.setDifficulty(blockCandidate.getDifficulty());
        blockCandidateViewModel.setTransactionsIncluded(blockCandidate.getTransactions().size());
        //TODO: blockCandidateViewModel.setRewardAddress("addr???");
        blockCandidateViewModel.setBlockDataHash(blockCandidate.getBlockDataHash());

        return blockCandidateViewModel;
    }

    @Override
    public MinedBlockStatusViewModel submitBLock(MinedBlockModel minedBlockModel, String minerAddress) {
        return null;
    }
}
