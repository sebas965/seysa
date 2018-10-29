package com.seysa.domain.model;

import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.FaceDetail;

public class FaceSearch {

    private String faceId;
    private String imageId;
    private String externalImageId;
    private Float similarity;
    private Float confidence;
    private Long timestamp;
    private Long index;
    private FaceDetail faceDetail;
    private BoundingBox boundingBox;

    public FaceSearch(String faceId, String imageId, String externalImageId, Float similarity, Float confidence,
            Long timestamp, Long index, FaceDetail faceDetail, BoundingBox boundingBox) {
        this.faceId = faceId;
        this.imageId = imageId;
        this.externalImageId = externalImageId;
        this.similarity = similarity;
        this.confidence = confidence;
        this.timestamp = timestamp;
        this.index = index;
        this.faceDetail = faceDetail;
        this.boundingBox = boundingBox;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getExternalImageId() {
        return externalImageId;
    }

    public void setExternalImageId(String externalImageId) {
        this.externalImageId = externalImageId;
    }

    public Float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Float similarity) {
        this.similarity = similarity;
    }

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public FaceDetail getFaceDetail() {
        return faceDetail;
    }

    public void setFaceDetail(FaceDetail faceDetail) {
        this.faceDetail = faceDetail;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }
}
