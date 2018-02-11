package com.topchain.node.service;

import com.topchain.node.model.viewModel.BlockViewModel;

import java.util.Set;

public interface BlockService {
    Set<BlockViewModel> getBlocks();

    BlockViewModel getBlockByIndex(long index);
}
