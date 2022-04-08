package com.chakib.example.jms.spring.handler;

import com.chakib.example.jms.spring.tibco.config.JmsProperties;
import com.chakib.example.jms.spring.tibco.config.JmsProperties.Server;
import com.tibco.tibjms.TibjmsConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * @author cdaii
 * @created 07/04/2022 - 10:51 AM
 * @project jms-sample
 **/

public abstract class FailOverHandler {

  protected static int nextServerIndex = 1;

  protected final JmsProperties jmsProperties;

  protected final ConnectionFactory connectionFactory;

  public FailOverHandler(JmsProperties jmsProperties, ConnectionFactory connectionFactory) {
    this.jmsProperties = jmsProperties;
    this.connectionFactory = connectionFactory;
  }

  void handleFailOver() throws JMSException {
    if(nextServerIndex >= jmsProperties.getServers().size())
      nextServerIndex = 0;
    setConnectionFactoryProperties();
    nextServerIndex++;
  }

  protected String serverURL(String server, String port) {
    return "tcp://" + server + ":" + port;
  }

  protected abstract Class<? extends ConnectionFactory> connectionFactoryType();

  protected abstract void setConnectionFactoryProperties() throws JMSException;

}
