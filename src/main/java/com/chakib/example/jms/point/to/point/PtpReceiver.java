package com.chakib.example.jms.point.to.point;

import com.chakib.example.jms.listeners.JmsListener;
import com.tibco.tibjms.TibjmsConnectionFactory;
import java.util.Properties;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PtpReceiver implements Runnable{
  private InitialContext context;
  private QueueConnectionFactory queueConnectionFactory;
  private QueueConnection queueConnection;
  private QueueSession queueSession;
  private Queue queue;
  private QueueReceiver queueReceiver;
  private JmsListener jmsListener;

  public PtpReceiver(String queueConnectionFactoryName, String queueName) {
    try {
      //1)Create and start connection
      Properties props = new Properties();

      //setting initial context factory and provider
      props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
      props.setProperty(Context.PROVIDER_URL,"tcp://localhost:7222");

      context = new InitialContext(props);
      this.queueConnectionFactory = (QueueConnectionFactory)context.lookup(queueConnectionFactoryName);
      //this.queueConnectionFactory = (QueueConnectionFactory)new TibjmsConnectionFactory();
      queueConnection = queueConnectionFactory.createQueueConnection();
      queueConnection.start();
      //2) create queue session
      queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
      //3) get the Queue object
      this.queue = (Queue)context.lookup(queueName);
      //4)create QueueReceiver
      queueReceiver = queueSession.createReceiver(queue);
      //5) create listener object
      jmsListener =new JmsListener();
      //6) register the listener object with receiver
      queueReceiver.setMessageListener(jmsListener);

      System.out.println("Receiver is ready, waiting for messages...");
    }catch (Throwable t){
      System.err.println(t);
    }

  }

  @Override
  public void run() {
    System.out.println("press Ctrl+c to shutdown...");
    while(true){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        System.err.println(e);
      }
    }
  }
}
