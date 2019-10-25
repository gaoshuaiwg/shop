package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author admin
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageServer {
    public static void main(String[] args) {
        SpringApplication.run(MessageServer.class,args);
    }
}
