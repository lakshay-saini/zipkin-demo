package com.service.service1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;

public class CustomSpanReporter implements ZipkinSpanReporter {
  private static final Logger LOGGER = LoggerFactory.getLogger(CustomSpanReporter.class);

  @Override
  public void report(zipkin.Span span) {
    LOGGER.info(span.toString());
  }
}