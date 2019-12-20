package com.vicmob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PaysysEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaysysEurekaApplication.class, args);
    }

}
