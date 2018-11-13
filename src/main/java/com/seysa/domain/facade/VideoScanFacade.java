package com.seysa.domain.facade;

import com.seysa.domain.model.Face;
import com.seysa.domain.model.FaceSearch;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;

import java.util.List;

public interface VideoScanFacade {

    String faceSearchScan(final Video video);

    String labelDetectionScan(final Video video);

    String faceDetectionScan(final Video video);

    List<FaceSearch> getFaceSearchResults(final VideoScan videoScan);

    List<Label> getLabelDetectionResults(final VideoScan videoScan);

    List<Face> getFaceDetectionResults(final VideoScan videoScan);

    VideoScan get(final String id);

    VideoScan update(final VideoScan videoScan);

    VideoScan create(final VideoScan videoScan);

    VideoScan getByScanId(final String scanId);
}
