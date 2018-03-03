package com.topchain.node.controller;

import com.topchain.node.model.bindingModel.NotifyBlockModel;
import com.topchain.node.model.viewModel.BlockViewModel;
import com.topchain.node.model.viewModel.ResponseMessageViewModel;
import com.topchain.node.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BlockController {
    private BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/blocks")
    public List<BlockViewModel> getBlocks() {
        return this.blockService.getBlocks();
    }

    @GetMapping("/blocks/{index}")
    public BlockViewModel getBlockByIndex(@PathVariable long index) {
        return this.blockService.getBlockByIndex(index);
    }

    @PostMapping("/blocks/notify")
    public ResponseMessageViewModel notify(@RequestBody NotifyBlockModel notifyBlockModel) {
        return this.blockService.notifyBlock(notifyBlockModel);
    }
}
