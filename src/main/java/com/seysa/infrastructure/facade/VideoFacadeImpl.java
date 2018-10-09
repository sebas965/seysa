package com.seysa.infrastructure.facade;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceSearchSortBy;
import com.amazonaws.services.rekognition.model.GetFaceSearchRequest;
import com.amazonaws.services.rekognition.model.GetFaceSearchResult;
import com.amazonaws.services.rekognition.model.NotificationChannel;
import com.amazonaws.services.rekognition.model.PersonMatch;
import com.amazonaws.services.rekognition.model.StartFaceSearchRequest;
import com.amazonaws.services.rekognition.model.StartFaceSearchResult;
import com.amazonaws.services.rekognition.model.Video;
import com.amazonaws.services.rekognition.model.VideoMetadata;
import com.seysa.domain.facade.VideoFacade;
import com.seysa.infrastructure.factory.VideoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoFacadeImpl implements VideoFacade {

    Logger logger = LoggerFactory.getLogger(VideoFacadeImpl.class);
    @Autowired
    private AmazonRekognition amazonRekognitionClient;
    @Autowired
    private VideoFactory videoFactory;
    @Value("${s3.video.bucket}")
    private String bucketName;
    @Value("${local.sns.arn.video.recognition}")
    private String videoRecognitionEventArn;
    @Value("${rekognition.role.arn}")
    private String rekognitionRoleArn;
    @Value("${rekognition.collection.id}")
    private String rekognitionCollectionId;

    @Override
    public void scan(final String fileName) {
        Video video = videoFactory.createVideo(fileName, bucketName);
        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn(rekognitionRoleArn)
                .withSNSTopicArn(videoRecognitionEventArn);
        StartFaceSearchRequest req = new StartFaceSearchRequest().withCollectionId(rekognitionCollectionId).withVideo(video)
                .withFaceMatchThreshold(70F).withNotificationChannel(notificationChannel);
        StartFaceSearchResult startPersonCollectionSearchResult = amazonRekognitionClient.startFaceSearch(req);
        logger.info(String.format("Asynchronous video scanning process started with ID %s",
                startPersonCollectionSearchResult.getJobId()));
    }

    @Override
    public void getResultsFaceSearchCollection(final String jobId) {
        GetFaceSearchResult faceSearchResult = null;
        int maxResults = 10;
        String paginationToken = null;
        do {
            if (faceSearchResult != null) {
                paginationToken = faceSearchResult.getNextToken();
            }
            faceSearchResult = amazonRekognitionClient.getFaceSearch(
                    new GetFaceSearchRequest().withJobId(jobId).withMaxResults(maxResults)
                            .withNextToken(paginationToken).withSortBy(FaceSearchSortBy.TIMESTAMP));
            VideoMetadata videoMetaData = faceSearchResult.getVideoMetadata();
            System.out.println("Format: " + videoMetaData.getFormat());
            System.out.println("Codec: " + videoMetaData.getCodec());
            System.out.println("Duration: " + videoMetaData.getDurationMillis());
            System.out.println("FrameRate: " + videoMetaData.getFrameRate());
            System.out.println();
            //Show search results
            List<PersonMatch> matches = faceSearchResult.getPersons();
            for (PersonMatch match : matches) {
                long milliSeconds = match.getTimestamp();
                System.out.print("Timestamp: " + Long.toString(milliSeconds));
                System.out.println(" Person number: " + match.getPerson().getIndex());
                List<FaceMatch> faceMatches = match.getFaceMatches();
                if (faceMatches != null) {
                    System.out.println("Matches in collection...");
                    for (FaceMatch faceMatch : faceMatches) {
                        Face face = faceMatch.getFace();
                        System.out.println("Face Id: " + face.getFaceId());
                        System.out.println("Similarity: " + faceMatch.getSimilarity().toString());
                        System.out.println();
                    }
                }
                System.out.println();
            }
            System.out.println();
        } while (faceSearchResult != null && faceSearchResult.getNextToken() != null);
    }
}
