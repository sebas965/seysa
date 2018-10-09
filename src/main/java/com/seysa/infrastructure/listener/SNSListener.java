package com.seysa.infrastructure.listener;

import com.seysa.infrastructure.model.SnsNotification;
import com.seysa.infrastructure.util.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SNSListener extends SQSListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SNSListener.class);
    @Autowired
    private JsonSerializer jsonSerializer;

    @Override
    public void onMessage(final String payload) {
        SnsNotification notification = jsonSerializer.getObjectFromJson(payload, SnsNotification.class);
        LOGGER.info("SNS notification received: {}", notification.getMessage());
        onMessage(notification);
    }

    public abstract void onMessage(final SnsNotification snsNotification);
}
