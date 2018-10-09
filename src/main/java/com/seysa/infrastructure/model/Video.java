package com.seysa.infrastructure.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("S3ObjectName")
    private String s3ObjectName;
    @SerializedName("S3Bucket")
    private String s3Bucket;

    public Video() {
    }

    @Override
    public String toString() {
        return "Video{" + "s3ObjectName='" + s3ObjectName + '\'' + ", s3Bucket='" + s3Bucket + '\'' + '}';
    }

    public String getS3ObjectName() {
        return s3ObjectName;
    }

    public void setS3ObjectName(String s3ObjectName) {
        this.s3ObjectName = s3ObjectName;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }
}
