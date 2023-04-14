package com.example.whitecommunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WhiteCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhiteCommunityApplication.class, args);
    }

}
