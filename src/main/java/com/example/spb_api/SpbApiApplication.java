package com.example.spb_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class SpbApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpbApiApplication.class, args);
    }

}
