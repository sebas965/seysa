package com.seysa.infrastructure.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class ListenerErrorHandler implements ErrorHandler {

    private static Logger log = LoggerFactory.getLogger(ListenerErrorHandler.class);

    @Override
    public void handleError(final Throwable throwable) {
        log.error("Error in listener", throwable);

    }
}
