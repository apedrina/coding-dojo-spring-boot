package com.alissonpedrina.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.alissonpedrina.dojo"})
public class ApplicationIT {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationIT.class, args);
    }

}
