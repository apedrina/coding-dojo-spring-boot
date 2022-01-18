package com.alissonpedrina.java17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@SpringBootApplication(scanBasePackages = {"com.alissonpedrina.java17"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
