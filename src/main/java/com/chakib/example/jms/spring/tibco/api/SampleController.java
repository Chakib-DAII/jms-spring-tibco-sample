package com.chakib.example.jms.spring.tibco.api;

import com.chakib.example.jms.spring.tibco.service.JmsSenderService;
import javax.jms.JMSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SampleController {

	@Autowired
	private JmsSenderService jmsService;

	@PostMapping(path = "/api/v1/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public String send(@RequestBody String message) {
		try {
			jmsService.send(message);
			return String.format("{\"message\":\"%s\"}", message);
		} catch (JmsException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}
		@GetMapping(path = "/api/v1/message", produces = MediaType.APPLICATION_JSON_VALUE)
		public String receive() {
			try {
				String message = jmsService.read();
				return String.format("{\"message\":\"%s\"}", message);
			} catch (JmsException | JMSException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
						e.getMessage());
			}
	}

	@PostMapping(path = "/api/v2/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object send(@RequestBody Object message) {
		try {
			jmsService.send(message);
			return message;
		} catch (JmsException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}
	@GetMapping(path = "/api/v2/message", produces = MediaType.APPLICATION_JSON_VALUE)
	public Object receiveMessage() {
		try {
			return jmsService.readMessage();
		} catch (JmsException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					e.getMessage());
		}
	}

}
