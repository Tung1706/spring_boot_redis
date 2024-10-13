package com.codejava.pubsub.subscriber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class CreateSubscriber implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateSubscriber.class);

    @Override
    public void onMessage(Message message, byte[] pattern) {
        LOGGER.info("Create message received: {}", message);
    }
}
