package com.seysa.infrastructure.listener;

import com.seysa.infrastructure.util.GsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class SQSListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQSListener.class);

    @Autowired
    protected GsonSerializer gsonSerializer;

    @Override
    public void onMessage(final Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            LOGGER.info("Received message " + textMessage.getText());
            onMessage(textMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("Error processing message ", e);
        }
    }

    public abstract void onMessage(final String payload);
}
