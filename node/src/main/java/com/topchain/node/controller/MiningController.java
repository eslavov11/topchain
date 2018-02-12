package com.topchain.node.controller;

import com.topchain.node.model.viewModel.PendingBlockViewModel;
import com.topchain.node.service.MiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiningController {
    private MiningService miningService;

    @Autowired
    public MiningController(MiningService miningService) {
        this.miningService = miningService;
    }

    @GetMapping("/mining/get-block/{minerAddress}")
    public PendingBlockViewModel getPendingBlock(@PathVariable String minerAddress) {
        return this.miningService.getPendingBlock(minerAddress);
    }

//    @PostMapping("/blocks/notify")
//    public ResponseMessageViewModel notify(@RequestBody NotifyBlockModel notifyBlockModel) {
//        return this.blockService.notifyBlock(notifyBlockModel);
//
//    }
}
