package com.test.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.test.redis"})
public class RedisOperateStarter
{
    public static void main( String[] args )
    {
        SpringApplication.run(RedisOperateStarter.class);
        System.out.println("------RedisOperateStarter--start successfully!");
    }
}
