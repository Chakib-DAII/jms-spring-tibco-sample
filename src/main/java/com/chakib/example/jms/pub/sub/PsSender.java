package com.chakib.example.jms.pub.sub;

import com.tibco.tibjms.TibjmsConnectionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PsSender implements Runnable{
  private InitialContext context;
  private TopicConnectionFactory topicConnectionFactory;
  private TopicConnection topicConnection;
  private TopicSession topicSession;
  private Topic topic;
  private TopicPublisher topicPublisher;
  private TextMessage textMessage;

  public PsSender(String topicConnectionFactoryName, String topicName) {
      try {
        //Create and start connection
        Properties props = new Properties();

        //setting initial context factory and provider
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.tibco.tibjms.naming.TibjmsInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,"tcp://localhost:7222");

        context = new InitialContext(props);
        topicConnectionFactory = (TopicConnectionFactory)context.lookup(topicConnectionFactoryName);
        //topicConnectionFactory = (TopicConnectionFactory)new TibjmsConnectionFactory();
        topicConnection = topicConnectionFactory.createTopicConnection();
        topicConnection.start();
        //2) create queue session
        topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        //3) get the Topic object
        topic = (Topic)context.lookup(topicName);
        //4)create TopicPublisher object
        topicPublisher = topicSession.createPublisher(topic);
        //5) create TextMessage object
        textMessage = topicSession.createTextMessage();
        System.out.println("Sender is created...");
      }catch (Throwable t){
        System.err.println(t);
      }
  }

  @Override
  public void run() {
    try{
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
        topicPublisher.publish(textMessage);
        System.out.println("Message successfully sent.");
      }
      //8) connection close
      topicConnection.close();
    }catch (Throwable t){
      System.err.println(t);
    }
  }
}
