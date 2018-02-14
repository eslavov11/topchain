package com.topchain.node.service;

import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.BlockCandidateViewModel;

public interface MiningService {
    BlockCandidateViewModel getBlockCandidate(String minerAddress);

    MinedBlockStatusViewModel submitBLock(MinedBlockModel minedBlockModel, String minerAddress);
}
