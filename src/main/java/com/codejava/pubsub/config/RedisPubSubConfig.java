package com.codejava.pubsub.config;

import com.codejava.pubsub.subscriber.CreateSubscriber;
import com.codejava.pubsub.subscriber.DeleteSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    public static final String CREATE_TOPIC = "create_topic";
    public static final String DELETE_TOPIC = "delete_topic";

    @Bean
    public ChannelTopic createTopic() {
        return new ChannelTopic(CREATE_TOPIC);
    }

    @Bean
    public ChannelTopic deleteTopic() {
        return new ChannelTopic(DELETE_TOPIC);
    }

    @Bean
    public MessageListenerAdapter createListener() {
        return new MessageListenerAdapter(new CreateSubscriber());
    }

    @Bean
    public MessageListenerAdapter deleteListener() {
        return new MessageListenerAdapter(new DeleteSubscriber());
    }

    @Bean
    RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(createListener(), createTopic());
        container.addMessageListener(deleteListener(), deleteTopic());
        return container;
    }

}
