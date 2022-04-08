package com.chakib.example.jms.spring.handler;

import com.chakib.example.jms.spring.tibco.config.JmsProperties;
import com.tibco.tibjms.TibjmsConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * @author cdaii
 * @created 08/04/2022 - 1:21 AM
 * @project jms-sample
 **/

public class TibjmsFailOverHandler extends FailOverHandler{

  public TibjmsFailOverHandler(JmsProperties jmsProperties,
      ConnectionFactory connectionFactory) {
    super(jmsProperties, connectionFactory);
  }

  @Override
  protected Class<? extends ConnectionFactory> connectionFactoryType() {
    return TibjmsConnectionFactory.class;
  }

  @Override
  protected void setConnectionFactoryProperties() throws JMSException {
    ((TibjmsConnectionFactory)connectionFactory)
        .setServerUrl(serverURL(jmsProperties.getServers().get(nextServerIndex).getServer(), jmsProperties.getServers().get(nextServerIndex).getPort()));
    ((TibjmsConnectionFactory)connectionFactory).setUserName(jmsProperties.getServers().get(nextServerIndex).getUser());
    ((TibjmsConnectionFactory)connectionFactory).setUserPassword(jmsProperties.getServers().get(nextServerIndex).getPassword());
  }
}
