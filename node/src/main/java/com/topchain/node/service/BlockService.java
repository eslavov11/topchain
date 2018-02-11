package com.topchain.node.service;

import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.NotifyBlockViewModel;

import java.util.Set;

public interface BlockService {
    Set<BlockViewModel> getBlocks();

    BlockViewModel getBlockByIndex(long index);

    NotifyBlockViewModel notifyBlock(NotifyBlockModel notifyBlockModel);
}
