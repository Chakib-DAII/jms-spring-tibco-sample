package com.chakib.example.jms.utils;

import com.chakib.example.jms.point.to.point.PtpSender;
import com.chakib.example.jms.pub.sub.PsReceiver;
import com.chakib.example.jms.pub.sub.PsSender;
import com.chakib.example.jms.point.to.point.PtpReceiver;

public class Utils {

    private Utils() {
    }

    public static void startPointToPointMessagingDomain(String queueConnectionFactoryName, String queueName){
        PtpSender ptpSender = new PtpSender(queueConnectionFactoryName,queueName);
        PtpReceiver ptpReceiver = new PtpReceiver(queueConnectionFactoryName, queueName);
        Thread sender = new Thread(ptpSender);
        sender.start();
        Thread receiver = new Thread(ptpReceiver);
        receiver.start();
    }
    
    public static void startPubSubMessagingDomain(String topicConnectionFactoryName, String topicName){
        PsSender ptpSender = new PsSender(topicConnectionFactoryName,topicName);
        PsReceiver ptpReceiver = new PsReceiver(topicConnectionFactoryName, topicName);
        Thread sender = new Thread(ptpSender);
        sender.start();
        Thread receiver = new Thread(ptpReceiver);
        receiver.start();
    }

    private void usage()
    {
        System.err.println("nUsage: java JmsExample [options] ");
        System.err.println("n");
        System.err.println(" where options are:");
        System.err.println("");
        System.err.println("-ptp, publish subscribe messaging domain mode activated");
        System.err.println("-ps, point to point messaging domain mode activated");
        System.err.println(" -topicCF <connection-factory-name> – topic connection factory name, default is \"topic.cf.sample\"");
        System.err.println(" -topic <topic-name> – topic name, default is \"topic.sample\"");
        System.err.println(" -queueCF <connection-factory-name> – queue connection factory name, no default");
        System.err.println(" -queue <queue-name> – queue name, no default");
        System.err.println(" -help-ssl – help on ssl parameters");
        System.exit(0);
    }
}
