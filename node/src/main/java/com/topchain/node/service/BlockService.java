package com.topchain.node.service;

import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;

import java.util.List;

public interface BlockService {
    List<BlockViewModel> getBlocks();

    BlockViewModel getBlockByIndex(long index);

    ResponseMessageViewModel notifyBlock(NotifyBlockModel notifyBlockModel);

    void updateBlockchain(List<BlockViewModel> blockViewModels);
}
