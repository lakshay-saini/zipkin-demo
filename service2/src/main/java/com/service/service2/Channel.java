package com.service.service2;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channel {
  public static final String INPUT = "inputchannel";

  @Input(value = "inputchannel")
  public SubscribableChannel input();
}
