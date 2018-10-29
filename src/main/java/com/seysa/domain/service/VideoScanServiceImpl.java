package com.seysa.domain.service;

import com.seysa.domain.facade.VideoScanFacade;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;
import com.seysa.infrastructure.facade.LabelFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoScanServiceImpl implements VideoScanService {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private VideoScanFacade videoFacade;
    @Autowired
    private LabelFacade labelFacade;

    @Override
    public String faceSearchScan(final Video video) {
        return videoFacade.faceSearchScan(video);
    }

    @Override
    public String labelDetectionScan(final Video video) {
        return videoFacade.labelDetectionScan(video);
    }

    @Override
    public void getFaceSearchResults(final VideoScan videoScan) {
        videoFacade.getFaceSearchResults(videoScan.scanId());
    }

    @Override
    public void getLabelDetectionResults(final VideoScan videoScan) {
        List<Label> labelsDetected = videoFacade.getLabelDetectionResults(videoScan.scanId());
        logger.info("Labels detected: {}", labelsDetected.size());
        labelsDetected.stream().forEach(x -> labelFacade.create(x));
    }

    @Override
    public String faceDetectionScan(final Video video) {
        return null;
    }

    @Override
    public void getFaceDetectionResults(final VideoScan videoScan) {
    }
}
