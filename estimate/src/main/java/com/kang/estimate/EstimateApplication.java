package com.kang.estimate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class EstimateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstimateApplication.class, args);
    }

}
