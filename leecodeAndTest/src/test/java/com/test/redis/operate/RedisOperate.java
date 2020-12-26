package com.test.redis.operate;

import com.test.redis.TestStarter;
import com.test.redis.demo.domain.ActivityPathieticConsumer;
import com.test.redis.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestStarter.class )//这个是一个扫描bean的启动类
public class RedisOperate extends AbstractTestNGSpringContextTests {




    @Autowired
    @Qualifier("humanRedisTemplate")
   private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate defaultRedisTemplate;


    @Test
    public void deleteRedisValue()
    {


        Boolean delete = redisTemplate.delete("activity:11:product:16:count:");
        System.out.println(delete);


    }


    @Test
    public void getRedisKeys()
    {


        Set keys = redisTemplate.keys("*");

        System.out.println(keys);
    }


    @Test
    public void setProcuctCountIntoRedis(){
        redisTemplate.opsForValue().set("activity:11:product:16:count",100);
    }
    //测试分布式锁,抢购例子
    @Test(threadPoolSize = 1000,invocationCount = 1000)
    public void testDevisionLock()
    {

        //redis中有activty:${activityId}product:${productid}:count:${leisure}
        //activty:11:product:16:count:4 有4件商品剩余,10个人抢,抢完就打印业务处理,且把商品数-1
//        activty:11:product:16:serial:1
        //1同时开10个线程
        Long expireTime=5000L;
        String uuid= UUID.randomUUID().toString();

            new ActivityPathieticConsumer(16L, uuid, 11L, LocalDateTime.now(), redisTemplate, expireTime)
//                    , "thread--" + uuid)
                    .run();





    }
    @Test
    public void testHashMapper()
    {

        HashMapper<Object,byte[],byte[]> mapper = new ObjectHashMapper();
        User userA = new User("小a", 12);
        Map<byte[], byte[]> map = mapper.toHash(userA);

        defaultRedisTemplate.opsForHash().putAll("useNow",map);
//        真的不如直接存一个对象

    }

    @Test
    public void testStoreObject()
    {


        User userA = new User("小a", 12);

        defaultRedisTemplate.opsForValue().set("useNow",userA);
//        真的不如直接存一个对象

    }














}
