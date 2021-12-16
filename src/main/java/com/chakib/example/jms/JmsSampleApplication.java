package com.chakib.example.jms;

import com.chakib.example.jms.utils.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JmsSampleApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(JmsSampleApplication.class, args);
    Utils.startPointToPointMessagingDomain("QueueConnectionFactory", context.getEnvironment().getProperty("ems.queue"));
  }

}
