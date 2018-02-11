package com.topchain.node.config;

import com.topchain.node.entity.Node;
import com.topchain.node.model.viewModel.NodeInfoViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${node.about}")
    private String nodeAbout;

    @Value("${node.name}")
    private String nodeName;

    @Value("${node.coins}")
    private Long nodeCoins;

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

        return node;
    }

    @Bean
    public NodeInfoViewModel getNodeInfoViewModel() {
        NodeInfoViewModel nodeInfoViewModel =  new NodeInfoViewModel();
        nodeInfoViewModel.setAbout(nodeAbout);
        nodeInfoViewModel.setNodeName(nodeName);
        nodeInfoViewModel.setCoins(nodeCoins);

        return nodeInfoViewModel;
    }
}
