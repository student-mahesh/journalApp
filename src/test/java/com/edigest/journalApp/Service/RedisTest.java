package com.edigest.journalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void testredis(){
        //redisTemplate.opsForValue().set("email","mahesh#email.com");
//        Object email=redisTemplate.opsForValue().get("email");
        redisTemplate.opsForValue().set("salary", "50000");
        Object salary=redisTemplate.opsForValue().get("salary");
        int a=1;
    }
}
