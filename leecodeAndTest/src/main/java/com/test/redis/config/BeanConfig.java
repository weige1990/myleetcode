package com.test.redis.config;

import com.test.redis.demo.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testng.annotations.Test;

import java.util.*;

@Configuration
public class BeanConfig {

    @Bean
    public List<User> buildInUsers()
    {
        List<User> users=new ArrayList<>();

        users.addAll(Arrays.asList(new User("小A", 19),
                new User("小B",20),
                new User("小C",21),new User("小D",22),
                new User("小E",23)));
        return users;


    }

    @Bean
    public Map randomMap()
    {
        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i <= 1000; i++)
        {

            map.put(i+"",UUID.randomUUID().toString());
        }
        return map;

    }
}
