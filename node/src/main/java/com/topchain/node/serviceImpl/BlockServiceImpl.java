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
        return new ResponseMessageViewModel("Thank you");
    }
}
