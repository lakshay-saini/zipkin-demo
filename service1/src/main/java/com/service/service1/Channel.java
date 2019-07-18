package com.service.service1;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channel {
  String OUTPUT = "outputchannel";
  String ANOTHER = "anotherchannel";

  @Output(value = OUTPUT)
  MessageChannel output();

  @Output(value = ANOTHER)
  MessageChannel anotheroutput();
}
