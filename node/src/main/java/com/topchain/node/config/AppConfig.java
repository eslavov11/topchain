package com.topchain.node.config;

import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

import static com.topchain.node.util.NodeUtils.collectionToJSON;
import static com.topchain.node.util.NodeUtils.hashText;

@Configuration
public class AppConfig {
    private static final int GENESIS_BLOCK_INDEX = 0;
    private static final String INIT_MINER_ADDRESS = "0000000000000000000000000000000000000000";

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
    public Node generateNode() {
        Node node = new Node();
        node.setDifficulty(nodeDifficulty);
        node.addBlock(createGenesisBlock());

        return node;
    }

    private Block createGenesisBlock() {
        Block genesis = new Block();
        genesis.setIndex(GENESIS_BLOCK_INDEX);

        //TODO: faucet transaction
        Transaction t1 = new Transaction();
        t1.setFromAddress(INIT_MINER_ADDRESS);
        t1.setToAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t1.setValue(100L);
        t1.setDateCreated(new Date());
        t1.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t1.setTransferSuccessful(true);

        Transaction t2 = new Transaction();
        t2.setFromAddress(INIT_MINER_ADDRESS);
        t2.setToAddress("feff4ac90f8fcc68bfbdf882d52a806e8ac738ad");
        t2.setValue(20L);
        t2.setDateCreated(new Date());
        t2.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t2.setTransferSuccessful(true);

        genesis.addTransaction(t1);
        genesis.addTransaction(t2);
        genesis.setDateCreated(new Date());
        genesis.setDifficulty(this.nodeDifficulty);
        genesis.setMinedBy(INIT_MINER_ADDRESS);
        genesis.setPreviousBlockHash(null);
        genesis.setNonce(0L);
        genesis.setBlockDataHash(hashText(genesis.getIndex() +
                collectionToJSON(genesis.getTransactions()) +
                genesis.getDifficulty() +
                genesis.getPreviousBlockHash() +
                genesis.getMinedBy()));
        genesis.setBlockHash(hashText(genesis.getBlockDataHash() +
                genesis.getDateCreated() +
                genesis.getDifficulty()));

        return genesis;
    }

    @Bean
    public NodeInfoViewModel generateNodeInfoViewModel() {
        NodeInfoViewModel nodeInfoViewModel = new NodeInfoViewModel();
        nodeInfoViewModel.setAbout(nodeAbout);
        nodeInfoViewModel.setNodeName(nodeName);

        return nodeInfoViewModel;
    }
}
