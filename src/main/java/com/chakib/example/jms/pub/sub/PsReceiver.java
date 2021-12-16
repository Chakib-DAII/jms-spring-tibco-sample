package com.chakib.example.jms.pub.sub;
import com.chakib.example.jms.listeners.JmsListener;
import com.tibco.tibjms.TibjmsConnectionFactory;
import java.util.Properties;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

public class PsReceiver implements Runnable{
    private InitialContext context;
    private TopicConnectionFactory topicConnectionFactory;
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private Topic topic;
    private TopicSubscriber topicSubscriber;
    private JmsListener jmsListener;

    public PsReceiver(String topicConnectionFactoryName, String topicName) {
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
            //4)create TopicSubscriber
            topicSubscriber = topicSession.createSubscriber(topic);
            //5) create listener object
            jmsListener = new JmsListener();
            //6) register the listener object with subscriber
            topicSubscriber.setMessageListener(jmsListener);
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
