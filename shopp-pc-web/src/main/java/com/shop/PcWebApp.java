package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author admin
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class PcWebApp {
    public static void main(String[] args) {
        SpringApplication.run(PcWebApp.class,args);
    }
}
