package com.chakib.example.jms.spring.converter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 * @author cdaii
 * @created 07/12/2021 - 1:46 PM
 * @project jms-sample
 **/

public class SampleMessageConverter implements MessageConverter {

  @Override
  public Message toMessage(Object object, Session session)
      throws JMSException, MessageConversionException {
    return null;
  }

  @Override
  public Object fromMessage(Message message) throws JMSException, MessageConversionException {
    return null;
  }
}
