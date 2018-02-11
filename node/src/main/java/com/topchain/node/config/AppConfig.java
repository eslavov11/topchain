package com.topchain.node.config;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {
    @Value("${node.about}")
    private String nodeAbout;

    @Value("${node.name}")
    private String nodeName;

//    @Value("${node.coins}")
//    private Long nodeCoins;

    @Value("${node.difficulty}")
    private Long nodeDifficulty;

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Node getNode() {
        Node node = new Node();
        node.setDifficulty(nodeDifficulty);
        Set<Block> blocks = new HashSet<>();
        Block block1 = new Block();
        block1.setIndex(1L);
        Transaction t1 = new Transaction();
        t1.setFromAddress("feff4ac90f8fcc68bfbdf882d52a806e8ac738ad");
        t1.setToAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t1.setValue(new BigDecimal(100));
        t1.setDateCreated(new Date());
        t1.setMinedInBlockIndex(1L);
        t1.setTransferSuccessful(true);
        Transaction t2 = new Transaction();
        t2.setFromAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t2.setToAddress("feff4ac90f8fcc68bfbdf882d52a806e8ac738ad");
        t2.setValue(new BigDecimal(20));
        t2.setDateCreated(new Date());
        t2.setMinedInBlockIndex(1L);
        t2.setTransferSuccessful(true);

        block1.addTransaction(t1);
        block1.addTransaction(t2);


        Block block2 = new Block();
        block2.setIndex(2L);


        blocks.add(block1);
        blocks.add(block2);

        node.setBlocks(blocks);

        return node;
    }

    @Bean
    public NodeInfoViewModel getNodeInfoViewModel() {
        NodeInfoViewModel nodeInfoViewModel =  new NodeInfoViewModel();
        nodeInfoViewModel.setAbout(nodeAbout);
        nodeInfoViewModel.setNodeName(nodeName);

        return nodeInfoViewModel;
    }
}
