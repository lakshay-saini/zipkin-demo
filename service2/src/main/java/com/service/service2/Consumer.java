package com.service.service2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;

@EnableBinding(Channel.class)
public class Consumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

  @StreamListener(Channel.INPUT)
  public void receiveMessage(GenericMessage message) throws IOException {

    LOGGER.info("Got some message from kafka topic");
    LOGGER.info("Received Message from Kafka topic ::: topic : {}", message.toString() );

    Model model = new ObjectMapper().readValue((String) message.getPayload(), Model.class);
    LOGGER.info("message payload :: {}", model.toString());

    LOGGER.info("message : {} | headers : {}", message, message.getHeaders().toString());
  }
}
