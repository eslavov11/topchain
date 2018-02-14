package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.BlockCandidateViewModel;
import com.topchain.node.service.MiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiningController {
    private MiningService miningService;

    @Autowired
    public MiningController(MiningService miningService) {
        this.miningService = miningService;
    }

    @GetMapping("/mining/get-block/{minerAddress}")
    public BlockCandidateViewModel getPendingBlock(@PathVariable String minerAddress) {
        return this.miningService.getBlockCandidate(minerAddress);
    }

    @PostMapping("/mining/submit-block/{minerAddress}")
    public MinedBlockStatusViewModel submitBlock(@PathVariable String minerAddress,
                                                 @RequestBody MinedBlockModel minedBlockModel) {
        return this.miningService.submitBLock(minedBlockModel, minerAddress);
    }
}
