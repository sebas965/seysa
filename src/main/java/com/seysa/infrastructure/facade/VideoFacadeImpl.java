package com.seysa.infrastructure.facade;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.NotificationChannel;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.StartFaceSearchRequest;
import com.amazonaws.services.rekognition.model.StartFaceSearchResult;
import com.amazonaws.services.rekognition.model.Video;
import com.seysa.domain.facade.VideoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoFacadeImpl implements VideoFacade{

//    @Autowired
//    private RekognitionClient rekognitionClient;

    @Autowired
    private AmazonRekognition amazonRekognitionClient;

    @Value("${s3.bucket}")
    private String bucketName;


    public void videoScan(){

//
//        Image sourceImage = Image.builder().s3Object(S3Object.builder().bucket(bucketName).name("sebas.jpg").build()).build();
//
//        IndexFacesRequest request = IndexFacesRequest.builder().collectionId("profileCollection").image(sourceImage).build();
//
//        IndexFacesResponse response = rekognitionClient.indexFaces(request);

        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn().withSNSTopicArn();

        StartFaceSearchRequest req = new StartFaceSearchRequest()
                .withCollectionId("CollectionId")
                .withVideo(new Video()
                        .withS3Object(new S3Object()
                                .withBucket(bucketName)
                                .withName("video.mp4")))
                                .withFaceMatchThreshold(70F)
                .withNotificationChannel(notificationChannel);

        StartFaceSearchResult startPersonCollectionSearchResult = amazonRekognitionClient.startFaceSearch(req);
        System.out.print(startPersonCollectionSearchResult.getJobId());

    }

}
