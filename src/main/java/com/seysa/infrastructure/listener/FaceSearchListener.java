package com.seysa.infrastructure.listener;

import com.seysa.domain.service.VideoService;
import com.seysa.infrastructure.model.FaceSearchNotification;
import com.seysa.infrastructure.model.SnsNotification;
import com.seysa.infrastructure.util.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FaceSearchListener extends SNSListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FaceSearchListener.class);

    @Autowired
    private JsonSerializer jsonSerializer;

    @Autowired
    private VideoService videoService;

    @Override
    public void onMessage(final SnsNotification snsNotification) {
        FaceSearchNotification faceSearchNotification = jsonSerializer
                .getObjectFromJson(snsNotification.getMessage(), FaceSearchNotification.class);
        LOGGER.info("Face Search notification received: {}", faceSearchNotification.toString());
        videoService.getResultsFaceSearchCollection(faceSearchNotification.getJobId());
    }
}
