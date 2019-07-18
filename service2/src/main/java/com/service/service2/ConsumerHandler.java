package com.service.service2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class ConsumerHandler implements MessageHandler {

  private  static final Logger LOGGER = LoggerFactory.getLogger(ConsumerHandler.class);

  @Override
  public void handleMessage(Message<?> message) throws MessagingException {
    try {
      LOGGER.info("Message received : {}", message.getHeaders());
      Model model = new ObjectMapper().readValue((String) message.getPayload(), Model.class);
      LOGGER.info("message payload :: {}", model.toString());
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

}