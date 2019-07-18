package com.service.service2;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@EnableKafka
@SpringBootApplication
public class Service2Application {

  private static  final Logger LOGGER = LoggerFactory.getLogger(Service2Application.class);

  public static void main(String[] args) {
    SpringApplication.run(Service2Application.class, args);
  }

  @KafkaListener(topics = "backend")
  public void onMessage(ConsumerRecord<?, ?> message) {
    LOGGER.warn("{}", message);
  }
}
