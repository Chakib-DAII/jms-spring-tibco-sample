package com.chakib.example.jms.listeners;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JmsListener implements MessageListener {

  @Override
  public void onMessage(Message message) {
    TextMessage textMessage = (TextMessage) message;
    try {
      System.out.println("following message is received:"+textMessage.getText());
    } catch (JMSException e) {
      System.err.println(e);
    }
  }
}
