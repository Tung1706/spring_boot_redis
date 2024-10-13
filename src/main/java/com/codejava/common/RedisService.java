package com.codejava.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public <T> T getRedis(String key, Class<T> entityClass) {
//        try {
//            String jsonValue = redisTemplate.opsForValue().get(key);
//            if (jsonValue != null) {
//                return objectMapper.readValue(jsonValue, entityClass);
//            }
//        } catch (Exception e) {
//            log.error("Exception", e);
//        }
//        return null;
//    }
//
//    public void setRedis(String key, Object object, Long time) {
//        try {
//            String jsonValue = objectMapper.writeValueAsString(object);
//            redisTemplate.opsForValue().set(key, jsonValue, time, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            log.error("Exception", e);
//        }
//    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public String getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? value.toString() : null;
    }

}
