package com.julianduru.oauthservicelib.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;


@Slf4j
@Component
public class TestKafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(1);

    private String payload;


    @PostConstruct
    public void init() {
        log.info("Test Kafka Consumer initialized.");
    }


    @KafkaListener(
        topics = "${code.config.kafka.oauth-service.topic-name}",
        clientIdPrefix = "oauth-service",
        groupId = "oauth-service-logs",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        payload = consumerRecord.toString();
        latch.countDown();
    }


    public void resetLatch() {
        latch = new CountDownLatch(1);
    }


    public CountDownLatch getLatch() {
        return latch;
    }


}



