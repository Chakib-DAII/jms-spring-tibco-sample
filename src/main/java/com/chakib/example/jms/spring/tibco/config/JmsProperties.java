package com.chakib.example.jms.spring.tibco.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author cdaii
 * @created 07/04/2022 - 10:21 AM
 * @project jms-sample
 **/
@ConfigurationProperties(prefix = "ems", ignoreUnknownFields = false)
public class JmsProperties {

  private String enabled;
  private List<Server> servers;
  private String queue;
  private final Listener listener = new Listener();

  public String getEnabled() {
    return enabled;
  }

  public void setEnabled(String enabled) {
    this.enabled = enabled;
  }

  public List<Server> getServers() {
    return servers;
  }

  public void setServers(
      List<Server> servers) {
    this.servers = servers;
  }

  public String getQueue() {
    return queue;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public Listener getListener() {
    return listener;
  }

  public static class Listener{
    private String enabled;

    public String getEnabled() {
      return enabled;
    }

    public void setEnabled(String enabled) {
      this.enabled = enabled;
    }
  }

  public static class Server{
    private String server;
    private String port;
    private String user;
    private String password;

    public String getServer() {
      return server;
    }

    public void setServer(String server) {
      this.server = server;
    }

    public String getPort() {
      return port;
    }

    public void setPort(String port) {
      this.port = port;
    }

    public String getUser() {
      return user;
    }

    public void setUser(String user) {
      this.user = user;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }


}
