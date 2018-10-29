package com.seysa.infrastructure.listener;

import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;
import com.seysa.domain.service.VideoScanService;
import com.seysa.domain.service.VideoService;
import com.seysa.infrastructure.listener.model.SnsNotification;
import com.seysa.infrastructure.listener.model.VideoScanNotification;
import com.seysa.infrastructure.listener.model.VideoScanType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoScanListener extends SNSListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoScanListener.class);
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoScanService videoScanService;

    @Override
    public void onMessage(final SnsNotification snsNotification) {
        VideoScanNotification videoScanNotification = gsonSerializer
                .getObjectFromJson(snsNotification.getMessage(), VideoScanNotification.class);
        LOGGER.info("Video scan notification received: {}", videoScanNotification.toString());
        VideoScanType videoScanType = VideoScanType.get(videoScanNotification.getApi());
        Video video = Video.builder().location(videoScanNotification.getVideoScanMetaData().getS3Bucket())
                .name(videoScanNotification.getVideoScanMetaData().getS3ObjectName()).build();



        VideoScan videoScan = VideoScan.builder().scanId(videoScanNotification.getJobId())
                .timestamp(videoScanNotification.getTimestamp()).build();
        switch (videoScanType) {
        case FACE_SEARCH:
            videoScanService.getFaceSearchResults(videoScan);
            break;
        case FACE_DETECTION:
            videoScanService.getFaceDetectionResults(videoScan);
            break;
        case LABEL_DETECTION:
            videoScanService.getLabelDetectionResults(videoScan);
            break;
        default:
            LOGGER.error("Unable to detect video scan process.");
        }
    }
}
