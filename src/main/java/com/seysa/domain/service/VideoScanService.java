package com.seysa.domain.service;

import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;

public interface VideoScanService {
    String faceSearchScan(final Video video);

    String labelDetectionScan(final Video video);

    String faceDetectionScan(final Video video);

    void getFaceSearchResults(final VideoScan videoScan);

    void getLabelDetectionResults(final VideoScan videoScan);

    void getFaceDetectionResults(final VideoScan videoScan);
}
