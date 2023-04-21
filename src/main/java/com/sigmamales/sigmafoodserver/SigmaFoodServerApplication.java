package com.sigmamales.sigmafoodserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SigmaFoodServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaFoodServerApplication.class, args);
    }

}
