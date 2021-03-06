package com.chakib.example.jms.spring.tibco.config;

import com.chakib.example.jms.spring.handler.SampleJmsErrorHandler;
import com.tibco.tibjms.TibjmsConnectionFactory;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
@EnableConfigurationProperties({JmsProperties.class})
public class TibcoBusConfiguration {

	private final JmsProperties jmsProperties;

	public TibcoBusConfiguration(JmsProperties jmsProperties) {
		this.jmsProperties = jmsProperties;
	}

	@Bean(name = "jmsConnectionFactory")
	@ConditionalOnProperty(value = "ems.enabled", havingValue = "true")
	public ConnectionFactory jmsConnectionFactory() throws JMSException {
		final TibjmsConnectionFactory factory = new TibjmsConnectionFactory();

		factory.setServerUrl(serverURL());
		factory.setUserName(jmsProperties.getServers().get(0).getUser());
		factory.setUserPassword(jmsProperties.getServers().get(0).getPassword());

		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	@ConditionalOnProperty(value = "ems.enabled", havingValue = "true")
	public JmsTemplate jmsTemplate(@Autowired ConnectionFactory jmsConnectionFactory) {
		final JmsTemplate jmsTemplate = new JmsTemplate();

		jmsTemplate.setConnectionFactory(jmsConnectionFactory);
		jmsTemplate.setDefaultDestinationName(jmsProperties.getQueue());
		jmsTemplate.setExplicitQosEnabled(true);
		jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
		jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		jmsTemplate.setSessionTransacted(false);
		jmsTemplate.setReceiveTimeout(100);
		//if we don't need the Jackson serialisation, the default Message Converter is :
		// org.springframework.jms.support.converter.SimpleMessageConverter
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter());

		return jmsTemplate;
	}

	@Bean
	@ConditionalOnProperty(value = "ems.enabled", havingValue = "false")
	public JmsTemplate jmsTemplate(){
		return new JmsTemplate();
	}

	@Bean("jmsListenerContainerFactory")
	@ConditionalOnProperty(value = "ems.spring.listener.enabled", havingValue = "true")
	public JmsListenerContainerFactory<?> jmsListenerContainerFactory(@Autowired ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		factory.setErrorHandler(new SampleJmsErrorHandler(jmsProperties, connectionFactory));
		return factory;
	}

	@Bean
	@ConditionalOnBean(JmsListenerContainerFactory.class)
	public JmsListeners jmsListeners() {
		return new JmsListeners();
	}

	private String serverURL() {
		return "tcp://" + jmsProperties.getServers().get(0).getServer() + ":" + jmsProperties.getServers().get(0).getPort();
	}
}