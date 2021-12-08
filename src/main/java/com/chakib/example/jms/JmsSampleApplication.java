package com.chakib.example.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JmsSampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(JmsSampleApplication.class, args);
    //Utils.startPointToPointMessagingDomain("testQueueConnectionFactory","testQueue");
  }

}
