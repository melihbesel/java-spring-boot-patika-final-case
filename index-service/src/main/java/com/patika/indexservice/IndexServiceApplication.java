package com.patika.indexservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IndexServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndexServiceApplication.class, args);
    }

}
