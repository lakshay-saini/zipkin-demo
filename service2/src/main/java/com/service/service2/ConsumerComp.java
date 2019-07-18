package com.service.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.instrument.messaging.TraceChannelInterceptor;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ConsumerComp {

  @Autowired
  private BindingService bindingService;

  @Autowired
  private TraceChannelInterceptor channelInterceptor;

  @PostConstruct
  public void onInit() {

    DirectChannel newChannel = new DirectChannel();
    newChannel.subscribe(new ConsumerHandler());
    newChannel.addInterceptor(channelInterceptor);

    bindingService.bindConsumer(newChannel, "testchannel");
  }
}