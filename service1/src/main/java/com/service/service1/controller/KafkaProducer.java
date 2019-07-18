package com.service.service1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.service1.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@EnableBinding
public class KafkaProducer {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

  @Value("${allowedchannels}")
  String allowedchannels;

  @Autowired
  private BinderAwareChannelResolver resolver;

  @GetMapping("/kafka_produce/{channel}")
  public String putMessageToKafka(@PathVariable("channel") String topic) throws JsonProcessingException {

    if (!Arrays.asList(allowedchannels.split(",")).contains(topic)) {
      return "channel does not exists";
    }

    Model kafkaModel = new Model();
    kafkaModel.setId(String.valueOf(Math.round(Math.random()*99999)));
    kafkaModel.setName("test");

    if ("outputchannel".contains(topic)) {
      MessageBuilder messageBuilder =  MessageBuilder.withPayload(new ObjectMapper().writeValueAsString(kafkaModel))
              .setHeader("messageSent", "true");
      resolver.resolveDestination("outputchannel").send(messageBuilder.build());
    }

    LOGGER.warn("Message successfully pushed to kafka");
    return "Message successfully pushed to kafka";
  }
}