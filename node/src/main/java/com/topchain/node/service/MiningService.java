package com.topchain.node.service;

import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.PendingBlockViewModel;

public interface MiningService {
    PendingBlockViewModel getPendingBlock(String minerAddress);

    MinedBlockStatusViewModel submitBLock(MinedBlockModel minedBlockModel);
}
