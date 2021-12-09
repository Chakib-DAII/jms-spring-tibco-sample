package com.chakib.example.jms.spring.tibco.config;

import org.springframework.jms.annotation.JmsListener;

/**
 * @author cdaii
 * @created 08/12/2021 - 1:04 AM
 * @project tibco-jms-spring-example
 **/

public class JmsListeners {

  @JmsListener(destination = "${ems.queue}", containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(Object message) {
    System.out.println("Received <" + message + ">");
  }
}
