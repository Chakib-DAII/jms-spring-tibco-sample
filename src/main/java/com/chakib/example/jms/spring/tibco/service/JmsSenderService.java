package com.chakib.example.jms.spring.tibco.service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JmsSenderService {
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${ems.queue}")
	private String queue;

	public void send(final String msg) {
		jmsTemplate.send(queue, session -> {
			TextMessage message = session.createTextMessage();
			message.setText(msg);
			return message;
		});
	}

	public String read() throws JMSException {
		TextMessage message = (TextMessage) jmsTemplate.receive(queue);
		if(message == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The queue is empty");
		return message.getText();
	}

	public void send(final Object msg) {
		jmsTemplate.convertAndSend(queue, msg);
	}

	public Object readMessage() {
		Object message =  jmsTemplate.receiveAndConvert(queue);
		if(message == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The queue is empty");
		return message;
	}

	public Object readMessage(final String messageSelector) {
		Object message =  jmsTemplate.receiveSelectedAndConvert(queue, messageSelector);
		if(message == null) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "The queue is empty");
		return message;
	}

}
