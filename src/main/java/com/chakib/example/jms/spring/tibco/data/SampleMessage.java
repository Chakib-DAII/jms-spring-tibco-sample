package com.chakib.example.jms.spring.tibco.data;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author cdaii
 * @created 08/12/2021 - 12:31 AM
 * @project tibco-jms-spring-example
 **/

public class SampleMessage {
  private List<String> to;
  private String body;
  private String from;

  public SampleMessage() {
  }

  public SampleMessage(List<String> to, String body, String from) {
    this.to = to;
    this.body = body;
    this.from = from;
  }

  public List<String> getTo() {
    return to;
  }

  public void setTo(List<String> to) {
    this.to = to;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SampleMessage.class.getSimpleName() + "[", "]")
        .add("to=" + to)
        .add("body='" + body + "'")
        .add("from='" + from + "'")
        .toString();
  }
}
