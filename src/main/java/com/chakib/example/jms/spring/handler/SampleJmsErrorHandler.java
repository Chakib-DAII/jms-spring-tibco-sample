package com.chakib.example.jms.spring.handler;

import com.chakib.example.jms.spring.tibco.config.JmsProperties;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

/**
 * @author cdaii
 * @created 07/12/2021 - 1:52 PM
 * @project jms-sample
 **/

public class SampleJmsErrorHandler implements ErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleJmsErrorHandler.class);

  private final JmsProperties jmsProperties;

  private final ConnectionFactory connectionFactory;

  private final FailOverHandler failOverHandler;

  public SampleJmsErrorHandler(JmsProperties jmsProperties, ConnectionFactory connectionFactory) {
    this.jmsProperties = jmsProperties;
    this.connectionFactory = connectionFactory;
    this.failOverHandler = new FailOverHandler(jmsProperties, connectionFactory);
  }

  @Override
  public void handleError(Throwable t) {
    LOGGER.warn("In default jms error handler...");
    LOGGER.error("Error Message : {}", t.getCause().getMessage());
    if(t instanceof SocketTimeoutException ||  t instanceof ConnectException)
      try {
        failOverHandler.handleFailOver();
      } catch (JMSException e) {
        e.printStackTrace();
      }
  }

}
