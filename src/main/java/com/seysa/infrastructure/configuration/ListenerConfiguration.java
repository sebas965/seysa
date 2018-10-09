package com.seysa.infrastructure.configuration;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.seysa.infrastructure.listener.SQSListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
public class ListenerConfiguration {

    @Value("${local.sqs.endpoint}")
    private String endpoint;
    @Value("${local.sqs.video.recognition.queue.name}")
    private String queueName;
    @Autowired
    private SQSListener sqsListener;
    @Autowired
    private AWSStaticCredentialsProvider staticCredentialsProvider;
    @Autowired
    private AmazonSQS amazonSQSclient;
    @Autowired
    private SQSConnectionFactory sqsConnectionFactory;

    @Bean
    public DefaultMessageListenerContainer jmsListenerContainer() {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(sqsConnectionFactory);
        dmlc.setDestinationName(queueName);
        dmlc.setMessageListener(sqsListener);
        return dmlc;
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory() {
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        providerConfiguration.setNumberOfMessagesToPrefetch(10);
        SQSConnectionFactory sqsConnectionFactory = new SQSConnectionFactory(providerConfiguration, amazonSQSclient);
        return sqsConnectionFactory;
    }

    @Bean
    public JmsTemplate createJMSTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(sqsConnectionFactory);
        jmsTemplate.setDefaultDestinationName(queueName);
        jmsTemplate.setDeliveryPersistent(false);
        return jmsTemplate;
    }
}
