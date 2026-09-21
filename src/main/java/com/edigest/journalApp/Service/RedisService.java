package com.edigest.journalApp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisService {


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T get(String key, Class<T> entityClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);  // value is taken from the redis store JSON  in object form that why object o
            if(o==null){

                return null;
            }
            ObjectMapper mapper = new ObjectMapper();  // object mapper is convertor from json to java and jav to json
            return mapper.readValue(o.toString(), entityClass);  // the object mapper through readValue it convert JSON (Object o) to java object(entity class)
        } catch (Exception e) {
        log.error("Exception",e);
        return null;
        }

    }

    public void set(String key, Object o,Long ttl) {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            String jsonValue=objectMapper.writeValueAsString(o);// the object mapper through readValue it convert Java comes in  (Object o) to JSON (jsonvalue)
            redisTemplate.opsForValue().set(key,jsonValue,ttl,TimeUnit.SECONDS);  // key is set in redis in JSON form and also we can set time how much time it is
        } catch (Exception e) {
            log.error("Exception",e);

        }

    }
}

/*         first weather fetch api data through restTemplate and convrt it into java object and stor or set in redis in JSON form
* WeatherService
     ↓
redisService.set("weather_of_Mumbai", weatherResponse, 300L)
     ↓
RedisService.set()
     ↓
Java WeatherResponse object
     ↓
ObjectMapper.writeValueAsString()
     ↓
JSON String
     ↓
RedisTemplate.opsForValue().set()
     ↓
Redis
     ↓
Store key + JSON value + TTL*/






/* WeatherService  now when whether required same from the redis the get call now data store in JSON form to Java object to wheather
     ↓
calls RedisService.get()
     ↓
RedisService asks Redis:
     "Give me value of weather_of_Mumbai"
     ↓
Redis returns JSON String
     ↓
ObjectMapper converts JSON → WeatherResponse object
     ↓
get() returns WeatherResponse object
     ↓
WeatherService receives it in weatherResponse variable*/