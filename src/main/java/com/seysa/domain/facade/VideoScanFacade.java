package com.seysa.domain.facade;

import com.seysa.domain.model.Face;
import com.seysa.domain.model.FaceSearch;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;

import java.util.List;

public interface VideoScanFacade {

    String faceSearchScan(final Video video);

    String labelDetectionScan(final Video video);

    String faceDetectionScan(final Video video);

    List<FaceSearch> getFaceSearchResults(final String jobId);

    List<Label> getLabelDetectionResults(final String jobId);

    List<Face> getFaceDetectionResults(final String jobId);
}
