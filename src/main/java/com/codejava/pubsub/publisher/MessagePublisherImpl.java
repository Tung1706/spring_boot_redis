package com.codejava.pubsub.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagePublisherImpl implements MessagePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisherImpl.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void publish(String topic, String message) {
        LOGGER.info("Topic: {} => send message: {}", topic, message);
        redisTemplate.convertAndSend(topic, message);
    }

}
