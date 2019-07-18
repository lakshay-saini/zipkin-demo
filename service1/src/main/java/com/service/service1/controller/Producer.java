package com.service.service1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.messaging.TraceChannelInterceptor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@EnableBinding
public class Producer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

  @Autowired
  BindingService bindingService;

  @Autowired
  TraceChannelInterceptor channelInterceptor;

  private final Map<String, MessageChannel> topicProducer = new HashMap<>();

  @RequestMapping("/kafka_dynamic/{topic}")
  public String putMessage(@PathVariable("topic") String topic) {
    LOGGER.info("Putting message to kafka topic : {} ", topic);

    Message message = MessageBuilder.withPayload("{\'a\':\'test\'}").setHeader("messageSent", "true").build();
    if (createNewChannelOrTopicIfNotExists(topic)) {
      topicProducer.get(topic).send(message);
      LOGGER.info("Message send successfully");
    }

    return "successfully pushed";
  }

  private boolean createNewChannelOrTopicIfNotExists(String topicName) {
    try {
      if (topicProducer.get(topicName) == null) {
        LOGGER.info("Creating new topic/channel " + topicName);
        DirectChannel channel = new DirectChannel();
        channel.addInterceptor(channelInterceptor);
        channel.setComponentName(topicName);
        topicProducer.put(topicName, channel);
        bindingService.bindProducer(channel, topicName);
        LOGGER.info("Topic/Channel created successfully....");
        return true;
      }
    } catch (Exception e) {
      LOGGER.error("Error occur while creating topic/channel :: {}", e.getMessage());
      return false;
    }
    return true;
  }

}