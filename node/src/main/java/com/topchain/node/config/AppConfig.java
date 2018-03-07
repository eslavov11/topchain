package com.topchain.node.config;


import com.topchain.node.entity.Block;
import com.topchain.node.entity.Node;
import com.topchain.node.entity.Transaction;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static com.topchain.node.util.NodeUtils.*;

@Configuration
public class AppConfig {
    private static final int GENESIS_BLOCK_INDEX = 0;

    @Value("${node.about}")
    private String nodeAbout;

    @Value("${node.name}")
    private String nodeName;

    @Value("${node.coins}")
    private long nodeCoins;

    @Value("${faucet.coins}")
    private long faucetCoins;

    @Value("${network.difficulty}")
    private int nodeDifficulty;

    private String FAUCET_ADDRESS = "f51362b7351ef62253a227a77751ad9b2302f911";

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Node generateNode() {
        Node node = new Node();
        node.setDifficulty(nodeDifficulty);
        node.addBlock(createGenesisBlock());

        //TODO: remove
        Transaction t1 = new Transaction();
        t1.setFromAddress(NIL_ADDRESS);
        t1.setToAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t1.setValue(100L);
        t1.setFee(10000L);
        t1.setDateCreated(new Date());
        t1.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t1.setTransferSuccessful(true);

        Transaction t2 = new Transaction();
        t2.setFromAddress(NIL_ADDRESS);
        t2.setToAddress("f51362b7351ef62253a227a77751ad9b2302f333");
        t2.setValue(20000L);
        t2.setFee(3);
        t2.setDateCreated(new Date());
        t2.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t2.setTransferSuccessful(true);

        node.addPendingTransaction(t1);
        node.addPendingTransaction(t2);

        return node;
    }

    private Block createGenesisBlock() {
        Block genesis = new Block();
        genesis.setIndex(GENESIS_BLOCK_INDEX);
        Transaction faucetTransaction = new Transaction();
        faucetTransaction.setFromAddress(NIL_ADDRESS);
        faucetTransaction.setToAddress(FAUCET_ADDRESS);
        faucetTransaction.setValue(faucetCoins);
        faucetTransaction.setDateCreated(new Date());
        faucetTransaction.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        faucetTransaction.setTransferSuccessful(true);

        Transaction t1 = new Transaction();
        t1.setFromAddress(NIL_ADDRESS);
        t1.setToAddress("f51362b7351ef62253a227a77751ad9b2302f911");
        t1.setValue(100L);
        t1.setDateCreated(new Date());
        t1.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t1.setTransferSuccessful(true);

        Transaction t2 = new Transaction();
        t2.setFromAddress(NIL_ADDRESS);
        t2.setToAddress("feff4ac90f8fcc68bfbdf882d52a806e8ac738ad");
        t2.setValue(20L);
        t2.setDateCreated(new Date());
        t2.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        t2.setTransferSuccessful(true);

        genesis.addTransaction(faucetTransaction);
        genesis.addTransaction(t1);
        genesis.addTransaction(t2);
        genesis.setDateCreated(new Date());
        genesis.setDifficulty(0);
        genesis.setMinedBy(NIL_ADDRESS);
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

        //TODO: properties are not set difficulty, etc.
        //TODO: call /info

        return nodeInfoViewModel;
    }

    //TODO: adjust difficulty
}
