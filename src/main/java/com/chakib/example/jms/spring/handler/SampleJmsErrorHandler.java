package com.chakib.example.jms.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

/**
 * @author cdaii
 * @created 07/12/2021 - 1:52 PM
 * @project jms-sample
 **/

@Service
public class SampleJmsErrorHandler implements ErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleJmsErrorHandler.class);
  @Override
  public void handleError(Throwable t) {
    LOGGER.warn("In default jms error handler...");
    LOGGER.error("Error Message : {}", t.getMessage());
  }

}
