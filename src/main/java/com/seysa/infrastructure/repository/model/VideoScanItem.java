package com.seysa.infrastructure.repository.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "video_scans")
public class VideoScanItem extends Item {

    private String scanId;
    private String videoId;
    private Long timestamp;

    @DynamoDBAttribute(attributeName = "videoId")
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @DynamoDBAttribute(attributeName = "scanId")
    public String getScanId() {
        return scanId;
    }

    public void setScanId(String scanId) {
        this.scanId = scanId;
    }

    @DynamoDBAttribute(attributeName = "timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
