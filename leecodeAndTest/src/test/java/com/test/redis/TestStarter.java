package com.test.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName ={"com.test.redis.web.controller.**"} )
public class TestStarter {
    public static void main(String[] args) {
        SpringApplication.run(TestStarter.class);
    }
}
