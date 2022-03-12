package com.chakib.example.jms.spring.converter;

import java.io.Serializable;
import java.util.Map;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.util.ObjectUtils;

/**
 * @author cdaii
 * @created 07/12/2021 - 1:46 PM
 * @project jms-sample
 **/

public class SampleMessageConverter implements MessageConverter {

  @Override
  public Message toMessage(Object object, Session session)
      throws JMSException, MessageConversionException {
    if (object instanceof Message) {
      return (Message)object;
    } else if (object instanceof String) {
      return session.createTextMessage((String)object);
    } else if (object instanceof byte[]) {
      BytesMessage message = session.createBytesMessage();
      message.writeBytes((byte[])((byte[])object));
      return message;
    } else if (object instanceof Serializable) {
      return session.createObjectMessage((Serializable)object);
    } else {
      throw new MessageConversionException("Cannot convert object of type [" + ObjectUtils.nullSafeClassName(object) + "] to JMS message. Supported message payloads are: String, byte array, Map<String,?>, Serializable object.");
    }
  }

  @Override
  public Object fromMessage(Message message) throws JMSException, MessageConversionException {
    if (message instanceof TextMessage) {
      return ((TextMessage) message).getText();
    } else if (message instanceof BytesMessage) {
      byte[] bytes = new byte[(int)((BytesMessage)message).getBodyLength()];
      ((BytesMessage)message).readBytes(bytes);
      return bytes;
    } else {
      return message instanceof ObjectMessage ? ((ObjectMessage) message).getObject() : message;
    }
  }
}
