package com.codejava.pubsub.publisher;

public interface MessagePublisher {

    void publish(String topic, String message);

}
