package com.seysa.infrastructure.listener;

import com.seysa.infrastructure.listener.model.SnsNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SNSListener extends SQSListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SNSListener.class);

    @Override
    public void onMessage(final String payload) {
        SnsNotification notification = gsonSerializer.getObjectFromJson(payload, SnsNotification.class);
        LOGGER.info("SNS notification received: {}", notification.getMessage());
        onMessage(notification);
    }

    public abstract void onMessage(final SnsNotification snsNotification);
}
