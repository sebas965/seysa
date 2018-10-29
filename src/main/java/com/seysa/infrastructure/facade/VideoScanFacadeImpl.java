package com.seysa.infrastructure.facade;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceSearchSortBy;
import com.amazonaws.services.rekognition.model.GetFaceSearchRequest;
import com.amazonaws.services.rekognition.model.GetFaceSearchResult;
import com.amazonaws.services.rekognition.model.GetLabelDetectionRequest;
import com.amazonaws.services.rekognition.model.GetLabelDetectionResult;
import com.amazonaws.services.rekognition.model.LabelDetection;
import com.amazonaws.services.rekognition.model.LabelDetectionSortBy;
import com.amazonaws.services.rekognition.model.NotificationChannel;
import com.amazonaws.services.rekognition.model.PersonMatch;
import com.amazonaws.services.rekognition.model.StartFaceSearchRequest;
import com.amazonaws.services.rekognition.model.StartFaceSearchResult;
import com.amazonaws.services.rekognition.model.StartLabelDetectionRequest;
import com.amazonaws.services.rekognition.model.StartLabelDetectionResult;
import com.seysa.domain.facade.VideoScanFacade;
import com.seysa.domain.model.Face;
import com.seysa.domain.model.FaceSearch;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;
import com.seysa.infrastructure.factory.VideoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VideoScanFacadeImpl implements VideoScanFacade {

    private static final int MAX_RESULTS = 100;
    private Logger logger = LoggerFactory.getLogger(VideoScanFacadeImpl.class);
    @Autowired
    private AmazonRekognition amazonRekognitionClient;
    @Autowired
    private VideoFactory videoFactory;
    @Value("${local.sns.arn.video.recognition}")
    private String videoRecognitionEventArn;
    @Value("${rekognition.role.arn}")
    private String rekognitionRoleArn;
    @Value("${rekognition.collection.id}")
    private String rekognitionCollectionId;

    @Override
    public String faceSearchScan(final Video video) {
        com.amazonaws.services.rekognition.model.Video rekognitionVideo = videoFactory.createVideo(video.name(),video.location());
        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn(rekognitionRoleArn)
                .withSNSTopicArn(videoRecognitionEventArn);
        StartFaceSearchRequest req = new StartFaceSearchRequest().withCollectionId(rekognitionCollectionId)
                .withVideo(rekognitionVideo).withFaceMatchThreshold(70F).withNotificationChannel(notificationChannel);
        StartFaceSearchResult startPersonCollectionSearchResult = amazonRekognitionClient.startFaceSearch(req);
        logger.info(String.format("VideoScanMetaData scanning process for face search started with ID %s",
                startPersonCollectionSearchResult.getJobId()));
        return startPersonCollectionSearchResult.getJobId();
    }

    @Override
    public String labelDetectionScan(final Video video) {
        final String jobTag = "Label";
        com.amazonaws.services.rekognition.model.Video rekognitionVideo = videoFactory.createVideo(video.name(), video.location());
        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn(rekognitionRoleArn)
                .withSNSTopicArn(videoRecognitionEventArn);
        StartLabelDetectionRequest startLabelDetectionRequest = new StartLabelDetectionRequest().withVideo(rekognitionVideo)
                .withNotificationChannel(notificationChannel).withMinConfidence(70F).withJobTag(jobTag);
        StartLabelDetectionResult startLabelDetectionResult = amazonRekognitionClient
                .startLabelDetection(startLabelDetectionRequest);
        logger.info(String.format("VideoScanMetaData scanning process for label detection started with ID %s",
                startLabelDetectionResult.getJobId()));
        return startLabelDetectionResult.getJobId();
    }

    @Override
    public String faceDetectionScan(final Video video) {
        return null;
    }

    @Override
    public List<Face> getFaceDetectionResults(final String jobId) {
        List<Face> faces = new ArrayList<Face>();
        return faces;
    }

    @Override
    public List<Label> getLabelDetectionResults(final String jobId) {
        List<Label> labels = new ArrayList<>();
        GetLabelDetectionResult labelDetectionResult = null;
        String paginationToken = null;
        do {
            if (labelDetectionResult != null) {
                paginationToken = labelDetectionResult.getNextToken();
            }
            labelDetectionResult = amazonRekognitionClient.getLabelDetection(
                    new GetLabelDetectionRequest().withMaxResults(MAX_RESULTS).withJobId(jobId)
                            .withNextToken(paginationToken).withSortBy(LabelDetectionSortBy.TIMESTAMP));
            for (LabelDetection labelDetection : labelDetectionResult.getLabels()) {
                labels.add(Label.builder().confidence(labelDetection.getLabel().getConfidence())
                        .name(labelDetection.getLabel().getName()).timestamp(labelDetection.getTimestamp()).build());
            }
        } while (labelDetectionResult != null && labelDetectionResult.getNextToken() != null);
        return labels;
    }

    @Override
    public List<FaceSearch> getFaceSearchResults(final String jobId) {
        List<FaceSearch> faceSearches = new ArrayList<>();
        GetFaceSearchResult faceSearchResult = null;
        String paginationToken = null;
        do {
            if (faceSearchResult != null) {
                paginationToken = faceSearchResult.getNextToken();
            }
            faceSearchResult = amazonRekognitionClient.getFaceSearch(
                    new GetFaceSearchRequest().withJobId(jobId).withMaxResults(MAX_RESULTS)
                            .withNextToken(paginationToken).withSortBy(FaceSearchSortBy.TIMESTAMP));
            List<PersonMatch> matches = faceSearchResult.getPersons();
            for (PersonMatch match : matches) {
                long milliSeconds = match.getTimestamp();
                List<FaceMatch> faceMatches = match.getFaceMatches();
                if (faceMatches != null) {
                    for (FaceMatch faceMatch : faceMatches) {
                        logger.info(
                                String.format("Face: %s matches search.", faceMatch.getFace().getExternalImageId()));
                        FaceSearch faceSearch = new FaceSearch(faceMatch.getFace().getFaceId(),
                                faceMatch.getFace().getImageId(), faceMatch.getFace().getExternalImageId(),
                                faceMatch.getSimilarity(), faceMatch.getFace().getConfidence(), match.getTimestamp(),
                                match.getPerson().getIndex(), match.getPerson().getFace(),
                                match.getPerson().getBoundingBox());
                        faceSearches.add(faceSearch);
                    }
                }
            }
        } while (faceSearchResult != null && faceSearchResult.getNextToken() != null);
        return faceSearches;
    }
}
