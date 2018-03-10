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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.topchain.node.util.NodeUtils.*;

@Configuration
public class AppConfig {
    private static final int GENESIS_BLOCK_INDEX = 0;

    @Value("${node.about}")
    private String nodeAbout;

    @Value("${node.name}")
    private String nodeName;

    @Value("${faucet.coins}")
    private long faucetCoins;

    @Value("${network.difficulty}")
    private int nodeDifficulty;

    private String FAUCET_ADDRESS = "9a959a9a2eb68ab550e0355dfef656a5e6016d71";

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

        return node;
    }

    private Block createGenesisBlock() {
        Block genesis = new Block();
        genesis.setIndex(GENESIS_BLOCK_INDEX);

        Transaction faucetTransaction = new Transaction();
        faucetTransaction.setFromAddress(NIL_ADDRESS);
        faucetTransaction.setToAddress(FAUCET_ADDRESS);
        faucetTransaction.setValue(micCoinsFromCoins(faucetCoins));
        faucetTransaction.setDateCreated(new Date());
        faucetTransaction.setMinedInBlockIndex(GENESIS_BLOCK_INDEX);
        faucetTransaction.setTransferSuccessful(true);
        faucetTransaction.setSenderPubKey("");
        faucetTransaction.setSenderSignature(new String[] {"",""});
        faucetTransaction.setTransactionHash(hashText(serializeJSON(faucetTransaction, false)));

        genesis.addTransaction(faucetTransaction);
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

        String dateStr = "";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateStr = formatter.format(genesis.getDateCreated());

        genesis.setBlockHash(hashText(genesis.getBlockDataHash() +
                dateStr +
                genesis.getDifficulty()));

        return genesis;
    }

    @Bean
    public NodeInfoViewModel generateNodeInfoViewModel() {
        NodeInfoViewModel nodeInfoViewModel = new NodeInfoViewModel();
        nodeInfoViewModel.setAbout(nodeAbout);
        nodeInfoViewModel.setNodeName(nodeName);
        nodeInfoViewModel.setCumulativeDifficulty(nodeDifficulty);

        return nodeInfoViewModel;
    }
}
