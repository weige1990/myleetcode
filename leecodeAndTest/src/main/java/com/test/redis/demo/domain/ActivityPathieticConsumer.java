package com.test.redis.demo.domain;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//参加抢购活动的顾客
public class ActivityPathieticConsumer implements Runnable {
    public static final String COUNT_KEY=":count";
    public static final String ACTIVITY_KEY="activity:";
    public static final String PRODUCT_KEY="product:";

    private Long expireTime;
    private Long productId;//1抢的商品的id
    private String customerId;//2抢商品顾客的id
    private Long activitId;//3抢购活动id

    private LocalDateTime resultUpdateTime;//5抢结果更新时间
    private RedisTemplate redisTemplate;
    private String lockKey;
    private String activityProductKey;//活动商品key

    public String getActivityProductKey() {
        return activityProductKey;
    }

    public void setActivityProductKey(String activityProductKey) {
        this.activityProductKey = activityProductKey;
    }

    public ActivityPathieticConsumer(Long productId, String customerId, Long activitId, LocalDateTime resultUpdateTime, RedisTemplate redisTemplate, Long expireTime) {
        this.productId = productId;
        this.customerId = customerId;
        this.activitId = activitId;

        this.resultUpdateTime = resultUpdateTime;
        this.redisTemplate = redisTemplate;
        this.expireTime = expireTime;

        this.lockKey=ACTIVITY_KEY+activitId+":"+
                PRODUCT_KEY+productId;
    }

    public String getLockKey() {
        return lockKey;
    }



    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getProductId() {
        return productId;
    }



    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getActivitId() {
        return activitId;
    }




    public LocalDateTime getResultUpdateTime() {
        return resultUpdateTime;
    }

    public void setResultUpdateTime(LocalDateTime resultUpdateTime) {
        this.resultUpdateTime = resultUpdateTime;
    }



    @Override
    public void run() {


        String lockMsg = customerId +"->"+UUID.randomUUID().toString();

//        2.1 上锁一段时间
        Boolean unLock = redisTemplate.opsForValue().setIfAbsent(lockKey, UUID.randomUUID().toString(), expireTime, TimeUnit.MILLISECONDS);

        if(unLock)
        {
//        2.2 业务逻辑
            Long leisure = redisTemplate.opsForValue().decrement(lockKey + COUNT_KEY);
            if(leisure>=0)
            {
                System.out.println(customerId +"执行业务逻辑,抢到:"+lockMsg);
                System.out.println("---------------------------");
                System.out.println("剩余"+leisure+"件");

                try {
                    Thread.sleep(2L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                redisTemplate.opsForValue().set(lockKey + COUNT_KEY,0);

                System.out.println("已经抢完了,剩"+leisure+"件");
            }



//        redis变量--
//        2.3释放锁

        redisTemplate.delete(lockKey);
       }
        else
        {
            System.out.println(customerId+"重新抢把!");
        }




    }
}
