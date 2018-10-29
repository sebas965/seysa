package com.seysa.infrastructure.listener.model;

import com.google.gson.annotations.SerializedName;

public class VideoScanNotification {

    @SerializedName("JobId")
    private String jobId;
    @SerializedName("Status")
    private String status;
    @SerializedName("API")
    private String api;
    @SerializedName("Timestamp")
    private long timestamp;
    @SerializedName("Video")
    private VideoScanMetaData videoScanMetaData;

    public VideoScanNotification() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public VideoScanMetaData getVideoScanMetaData() {
        return videoScanMetaData;
    }

    public void setVideoScanMetaData(VideoScanMetaData videoScanMetaData) {
        this.videoScanMetaData = videoScanMetaData;
    }

    @Override
    public String toString() {
        return "VideoScanNotification{" + "jobId='" + jobId + '\'' + ", status='" + status + '\'' + ", api='" + api
                + '\'' + ", timestamp=" + timestamp + ", videoScanMetaData=" + videoScanMetaData + '}';
    }
}
