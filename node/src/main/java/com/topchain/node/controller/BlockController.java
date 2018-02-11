package com.topchain.node.controller;

import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BlockController {
    private BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/blocks")
    public Set<BlockViewModel> getBlocks() {
        return this.blockService.getBlocks();
    }

    @GetMapping("/blocks/{index}")
    public BlockViewModel getBlockByIndex(@PathVariable long index) {
        return this.blockService.getBlockByIndex(index);
    }
}
