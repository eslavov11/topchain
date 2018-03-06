package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.MinedBlockModel;
import com.topchain.node.model.viewModel.MinedBlockStatusViewModel;
import com.topchain.node.model.viewModel.BlockCandidateViewModel;
import com.topchain.node.service.MiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MiningController {
    private MiningService miningService;

    @Autowired
    public MiningController(MiningService miningService) {
        this.miningService = miningService;
    }

    @GetMapping("/mining/get-mining-job/{minerAddress}")
    public BlockCandidateViewModel getPendingBlock(@PathVariable String minerAddress) {
        return this.miningService.getBlockCandidate(minerAddress);
    }

    @PostMapping("/mining/submit-mined-block")
    public ResponseEntity<MinedBlockStatusViewModel> submitBlock(
            @RequestBody MinedBlockModel minedBlockModel) {
        MinedBlockStatusViewModel minedBlockStatusViewModel =
                this.miningService.submitBlock(minedBlockModel);

        return new ResponseEntity<>(minedBlockStatusViewModel,
                minedBlockStatusViewModel.getStatus().equals("accepted") ?
                        HttpStatus.OK :
                        minedBlockStatusViewModel.getStatus().equals("rejected") ?
                                HttpStatus.BAD_REQUEST :
                                HttpStatus.NOT_FOUND);
    }
}
