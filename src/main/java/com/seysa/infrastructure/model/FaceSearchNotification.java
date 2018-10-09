package com.seysa.infrastructure.model;

import com.google.gson.annotations.SerializedName;

public class FaceSearchNotification {

    @SerializedName("JobId")
    private String jobId;
    @SerializedName("Status")
    private String status;
    @SerializedName("API")
    private String api;
    @SerializedName("Timestamp")
    private long timestamp;
    @SerializedName("Video")
    private Video video;

    public FaceSearchNotification() {
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "FaceSearchNotification{" + "jobId='" + jobId + '\'' + ", status='" + status + '\'' + ", api='" + api
                + '\'' + ", timestamp=" + timestamp + ", video=" + video + '}';
    }
}
