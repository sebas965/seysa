package com.seysa.domain.service;

import com.seysa.domain.facade.FaceFacade;
import com.seysa.domain.facade.LabelFacade;
import com.seysa.domain.facade.VideoScanFacade;
import com.seysa.domain.model.Face;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoScanService {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private VideoScanFacade videoScanFacade;
    @Autowired
    private LabelFacade labelFacade;
    @Autowired
    private FaceFacade faceFacade;

    public VideoScan faceSearchScan(final Video video) {

        String scanId = videoScanFacade.faceSearchScan(video);
        VideoScan videoScan = VideoScan.builder().scanId(scanId).video(video).build();
        return videoScanFacade.create(videoScan);
    }

    public VideoScan labelDetectionScan(final Video video) {
        String scanId = videoScanFacade.labelDetectionScan(video);
        VideoScan videoScan = VideoScan.builder().scanId(scanId).video(video).build();
        return videoScanFacade.create(videoScan);
    }

    public VideoScan faceDetectionScan(final Video video) {

        String scanId = videoScanFacade.faceDetectionScan(video);
        VideoScan videoScan = VideoScan.builder().scanId(scanId).video(video).build();
        return videoScanFacade.create(videoScan);
    }

    public void getFaceSearchResults(final VideoScan videoScan) {
        videoScanFacade.getFaceSearchResults(videoScan);
    }

    public void getLabelDetectionResults(final VideoScan videoScan) {
        List<Label> labelsDetected = videoScanFacade.getLabelDetectionResults(videoScan);
        logger.info("Labels detected: {} for video scan: {}", labelsDetected.size(), videoScan.id());
        labelsDetected.stream().forEach(x -> labelFacade.create(x));
    }

    public void getFaceDetectionResults(final VideoScan videoScan) {
        List<Face> faceDetections = videoScanFacade.getFaceDetectionResults(videoScan);
        logger.info("Faces detected: {} for video scan: {}", faceDetections.size(), videoScan.id());
        faceDetections.stream().forEach(x -> faceFacade.create(x));
    }

    public VideoScan getByScanId(final String scanId){
       return videoScanFacade.getByScanId(scanId);
    }

    public VideoScan update(final VideoScan videoScan){
        return videoScanFacade.update(videoScan);
    }

    public VideoScan updateTimestamp(final String scanId, final Long timestamp) {
        VideoScan videoScanRetrieved = getByScanId(scanId);
        VideoScan videoScan = VideoScan.builder().video(videoScanRetrieved.video()).timestamp(timestamp)
                .scanId(videoScanRetrieved.scanId()).createdDate(videoScanRetrieved.createdDate())
                .id(videoScanRetrieved.id()).updatedDate(videoScanRetrieved.updatedDate()).build();
        return videoScanFacade.update(videoScan);
    }
}
