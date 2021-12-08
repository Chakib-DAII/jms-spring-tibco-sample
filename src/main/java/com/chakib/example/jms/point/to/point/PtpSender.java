package com.chakib.example.jms.point.to.point;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PtpSender implements Runnable{
  private InitialContext context;
  private QueueConnectionFactory queueConnectionFactory;
  private QueueConnection queueConnection;
  private QueueSession queueSession;
  private Queue queue;
  private QueueSender queueSender;
  private TextMessage textMessage;

  public PtpSender(String queueConnectionFactoryName, String queueName) {
    try {
      //1)Create and start connection
      Properties props = new Properties();
      props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.impl.SerialInitContextFactory");
      props.setProperty(Context.PROVIDER_URL,"stcms://localhost:18007");
      context = new InitialContext(props);
      this.queueConnectionFactory = (QueueConnectionFactory)context.lookup(queueConnectionFactoryName);
      queueConnection = queueConnectionFactory.createQueueConnection();
      queueConnection.start();
      //2) create queue session
      queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      //3) get the Queue object
      this.queue = (Queue)context.lookup(queueName);
      //4)create QueueSender object
      queueSender = queueSession.createSender(queue);
      //5) create TextMessage object
      textMessage = queueSession.createTextMessage();
      System.out.println("Sender is created...");
    }catch (Throwable t){
      System.err.println(t);
    }

  }

  @Override
  public void run() {
    try {
      //6) write message
      BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
      while(true)
      {
        System.out.println("Enter Msg, end to terminate:");
        String s=b.readLine();
        if (s.equals("end"))
          break;
        textMessage.setText(s);
        //7) send message
        queueSender.send(textMessage);
        System.out.println("Message successfully sent.");
      }
      //8) connection close
      queueConnection.close();
    }catch (Throwable t){
      System.err.println(t);
    }
  }
}
