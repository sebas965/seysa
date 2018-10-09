package com.seysa.infrastructure.factory;

import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoFactory {

    public Video createVideo(final String fileName, final String bucketName) {
        S3Object s3Object = new S3Object().withBucket(bucketName).withName(fileName);
        return new Video().withS3Object(s3Object);
    }
}
