package com.test.redis.operate;

import com.test.redis.RedisOperateStarter;
import com.test.redis.demo.domain.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(classes = RedisOperateStarter.class)

public class CollectionTest extends AbstractTestNGSpringContextTests {

    @Test
    public void testHashMap() {

        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i <= 1000; i++) {
           /* if(i>=990)
            {
                map.put(i+"",UUID.randomUUID().toString());

            }
            else
            {
                map.put(i+"",UUID.randomUUID().toString());
            }*/
            map.put(i + "", UUID.randomUUID().toString());
        }
        User userA = new User("小A", 19);
        User userB = new User("小B", 20);
        User userC = new User("小C", 21);
        User userD = new User("小D", 22);
        User userE = new User("小E", 23);

    }


    @Test
    public void testSout()
    {

        System.out.println("{1},你好{1},{2}");
    }
}
