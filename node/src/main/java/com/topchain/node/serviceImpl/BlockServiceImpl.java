package com.topchain.node.serviceImpl;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.BlockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BlockServiceImpl implements BlockService {
    private ModelMapper modelMapper;
    private Node node;

    @Autowired
    public BlockServiceImpl(ModelMapper modelMapper, Node node) {
        this.modelMapper = modelMapper;
        this.node = node;
    }

    @Override
    public Set<BlockViewModel> getBlocks() {
        Set<BlockViewModel> blockViewModels = new HashSet<>();
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

        return new ResponseMessageViewModel("Thank you");
    }

    //TODO: new block
    /**
     * A special coinbase transaction is inserted before all user
     transactions in the block, to transfer the block reward + fees

     No sender public key and signature
     The Coinbase Transaction (Reward)
     {
         "from": "0000000000000000000000000000000000000000",
         "to": "9a9f082f37270ff54c5ca4204a0e4da6951fe917",
         "value": 5000350,
         "fee": 0,
         "dateCreated": "2018-02-10T17:53:48.972Z",
         "transactionHash": "4dfc3e0ef89ed603ed54e47435a18b836b…176a",
         "transferSuccessful": true,
     }
     */
}
