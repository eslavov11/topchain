package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.BlockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.topchain.node.util.NodeUtils.newString;

@Service
public class BlockServiceImpl implements BlockService {
    private ModelMapper modelMapper;
    private Node node;
    private NodeInfoViewModel nodeInfo;

    @Autowired
    public BlockServiceImpl(ModelMapper modelMapper, Node node, NodeInfoViewModel nodeInfo) {
        this.modelMapper = modelMapper;
        this.node = node;
        this.nodeInfo = nodeInfo;
    }

    @Override
    public List<BlockViewModel> getBlocks() {
        List<BlockViewModel> blockViewModels = new ArrayList<>();
        this.node.getBlocks().forEach((Block block) -> {
            BlockViewModel blockViewModel = this.modelMapper.map(block, BlockViewModel.class);
            blockViewModels.add(blockViewModel);
        });

        return blockViewModels;
    }

    @Override
    public BlockViewModel getBlockByIndex(long index) {
        Block block = this.node.getBlocks().stream()
                .filter(b -> b.getIndex() == index).findFirst().get();
        BlockViewModel blockViewModel = this.modelMapper.map(block, BlockViewModel.class);

        return blockViewModel;
    }

    @Override
    public ResponseMessageViewModel notifyBlock(NotifyBlockModel notifyBlockModel) {
        //TODO:

        if (notifyBlockModel.getCumulativeDifficulty() > this.nodeInfo.getCumulativeDifficulty()) {
            //TODO: get blockchain
        }
        //TODO: update nodeInfo on events, ex: blockchain update, new block etc.

        return new ResponseMessageViewModel("Thank you");
    }

    /**
     * A special coinbase transaction is inserted before all user
     * transactions in the block, to transfer the block reward + fees
     * <p>
     * No sender public key and signature
     * The Coinbase Transaction (Reward)
     * {
     * "from": "0000000000000000000000000000000000000000",
     * "to": "9a9f082f37270ff54c5ca4204a0e4da6951fe917",
     * "value": 5000350,
     * "fee": 0,
     * "dateCreated": "2018-02-10T17:53:48.972Z",
     * "transactionHash": "4dfc3e0ef89ed603ed54e47435a18b836b…176a",
     * "transferSuccessful": true,
     * }
     */

    @Override
    public void updateBlockchain(List<BlockViewModel> blockViewModels) {
        List<Block> blocks = new ArrayList<>();
        blockViewModels.forEach(b -> {
            blocks.add(this.modelMapper.map(b, Block.class));
        });

        this.node.setBlocks(blocks);
    }

    @Override
    public boolean peerBlocksAreValid(List<BlockViewModel> blockViewModels) {
        List<Block> blocks = new ArrayList<>();
        blockViewModels.forEach(b -> {
            blocks.add(this.modelMapper.map(b, Block.class));
        });

        for (int i = 1; i < blocks.size(); i++) {
            if (!blocks.get(i).getBlockHash().equals(hashBlock(blocks.get(i - 1))) ||
                    !blocks.get(i).getBlockHash()
                            .startsWith(newString("0", blocks.get(i).getDifficulty()))) {
                return false;
            }
        }

        return true;
    }

    private String hashBlock(Block block) {
        //TODO:
        return null;
    }
}
