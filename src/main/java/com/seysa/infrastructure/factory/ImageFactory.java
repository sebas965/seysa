package com.seysa.infrastructure.factory;

import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import org.springframework.stereotype.Component;

@Component
public class ImageFactory {

    public Image createImage(final String fileName, final String bucketName) {
        S3Object s3Object = new S3Object().withBucket(bucketName).withName(fileName);
        return new Image().withS3Object(s3Object);
    }

}
