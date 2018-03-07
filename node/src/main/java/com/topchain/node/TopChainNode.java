package com.topchain.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopChainNode
//        extends SpringBootServletInitializer
{
    public static void main(String[] args) {
        SpringApplication.run(TopChainNode.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(TopChainNode.class);
//    }
}