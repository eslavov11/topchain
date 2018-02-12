package com.topchain.node.service;

import com.topchain.node.model.viewModel.PendingBlockViewModel;

public interface MiningService {
    PendingBlockViewModel getPendingBlock(String minerAddress);
}
