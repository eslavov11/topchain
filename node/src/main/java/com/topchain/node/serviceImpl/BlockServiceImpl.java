package com.topchain.node.serviceImpl;

import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.NotifyBlockViewModel;
import com.topchain.node.service.BlockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BlockServiceImpl implements BlockService {
    ModelMapper modelMapper;

    @Autowired
    public BlockServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<BlockViewModel> getBlocks() {
        return null;
    }

    @Override
    public BlockViewModel getBlockByIndex(long index) {
        return null;
    }

    @Override
    public NotifyBlockViewModel notifyBlock(NotifyBlockModel notifyBlockModel) {
        return new NotifyBlockViewModel("Thank you");
    }
}
