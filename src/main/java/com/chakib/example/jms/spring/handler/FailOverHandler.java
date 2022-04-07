package com.chakib.example.jms.spring.handler;

import com.chakib.example.jms.spring.tibco.config.JmsProperties;
import com.chakib.example.jms.spring.tibco.config.JmsProperties.Server;
import com.tibco.tibjms.TibjmsConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import org.springframework.stereotype.Service;

/**
 * @author cdaii
 * @created 07/04/2022 - 10:51 AM
 * @project jms-sample
 **/

public class FailOverHandler {

  private static int nextServerIndex = 1;

  private final JmsProperties jmsProperties;

  private final ConnectionFactory connectionFactory;

  public FailOverHandler(JmsProperties jmsProperties, ConnectionFactory connectionFactory) {
    this.jmsProperties = jmsProperties;
    this.connectionFactory = connectionFactory;
  }

  void handleFailOver() throws JMSException {
    if(nextServerIndex >= jmsProperties.getServers().size())
      nextServerIndex = 0;
    ((TibjmsConnectionFactory)connectionFactory)
        .setServerUrl(serverURL(jmsProperties.getServers().get(nextServerIndex).getServer(), jmsProperties.getServers().get(nextServerIndex).getPort()));
    ((TibjmsConnectionFactory)connectionFactory).setUserName(jmsProperties.getServers().get(nextServerIndex).getUser());
    ((TibjmsConnectionFactory)connectionFactory).setUserPassword(jmsProperties.getServers().get(nextServerIndex).getPassword());
    nextServerIndex++;
  }

  private String serverURL(String server, String port) {
    return "tcp://" + server + ":" + port;
  }
}
