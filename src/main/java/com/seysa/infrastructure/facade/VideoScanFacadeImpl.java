package com.seysa.infrastructure.facade;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceAttributes;
import com.amazonaws.services.rekognition.model.FaceDetection;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.FaceSearchSortBy;
import com.amazonaws.services.rekognition.model.GetFaceDetectionRequest;
import com.amazonaws.services.rekognition.model.GetFaceDetectionResult;
import com.amazonaws.services.rekognition.model.GetFaceSearchRequest;
import com.amazonaws.services.rekognition.model.GetFaceSearchResult;
import com.amazonaws.services.rekognition.model.GetLabelDetectionRequest;
import com.amazonaws.services.rekognition.model.GetLabelDetectionResult;
import com.amazonaws.services.rekognition.model.LabelDetection;
import com.amazonaws.services.rekognition.model.LabelDetectionSortBy;
import com.amazonaws.services.rekognition.model.NotificationChannel;
import com.amazonaws.services.rekognition.model.PersonMatch;
import com.amazonaws.services.rekognition.model.StartFaceDetectionRequest;
import com.amazonaws.services.rekognition.model.StartFaceDetectionResult;
import com.amazonaws.services.rekognition.model.StartFaceSearchRequest;
import com.amazonaws.services.rekognition.model.StartFaceSearchResult;
import com.amazonaws.services.rekognition.model.StartLabelDetectionRequest;
import com.amazonaws.services.rekognition.model.StartLabelDetectionResult;
import com.seysa.domain.facade.VideoScanFacade;
import com.seysa.domain.model.Face;
import com.seysa.domain.model.FaceSearch;
import com.seysa.domain.model.Label;
import com.seysa.domain.model.Video;
import com.seysa.domain.model.VideoScan;
import com.seysa.domain.service.VideoService;
import com.seysa.infrastructure.factory.FaceDetectionFactory;
import com.seysa.infrastructure.factory.VideoFactory;
import com.seysa.infrastructure.repository.VideoScanRepository;
import com.seysa.infrastructure.repository.model.VideoScanItem;
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
    @Autowired
    private VideoScanRepository videoScanRepository;
    @Autowired
    private VideoService videoService;
    @Autowired
    private FaceDetectionFactory faceDetectionFactory;

    @Override
    public String faceSearchScan(final Video video) {
        com.amazonaws.services.rekognition.model.Video rekognitionVideo = videoFactory
                .createVideo(video.name(), video.location());
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
        com.amazonaws.services.rekognition.model.Video rekognitionVideo = videoFactory
                .createVideo(video.name(), video.location());
        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn(rekognitionRoleArn)
                .withSNSTopicArn(videoRecognitionEventArn);
        StartLabelDetectionRequest startLabelDetectionRequest = new StartLabelDetectionRequest()
                .withVideo(rekognitionVideo).withNotificationChannel(notificationChannel).withMinConfidence(70F)
                .withJobTag(jobTag);
        StartLabelDetectionResult startLabelDetectionResult = amazonRekognitionClient
                .startLabelDetection(startLabelDetectionRequest);
        logger.info(String.format("VideoScanMetaData scanning process for label detection started with ID %s",
                startLabelDetectionResult.getJobId()));
        return startLabelDetectionResult.getJobId();
    }

    @Override
    public String faceDetectionScan(final Video video) {
        final String jobTag = "Face";
        com.amazonaws.services.rekognition.model.Video rekognitionVideo = videoFactory
                .createVideo(video.name(), video.location());
        NotificationChannel notificationChannel = new NotificationChannel().withRoleArn(rekognitionRoleArn)
                .withSNSTopicArn(videoRecognitionEventArn);
        StartFaceDetectionRequest startFaceDetectionRequest = new StartFaceDetectionRequest()
                .withVideo(rekognitionVideo).withNotificationChannel(notificationChannel).withJobTag(jobTag).withFaceAttributes(
                        FaceAttributes.ALL);
        StartFaceDetectionResult startFaceDetectionResult = amazonRekognitionClient
                .startFaceDetection(startFaceDetectionRequest);
        logger.info(String.format("VideoScanMetaData scanning process for face detection started with ID %s",
                startFaceDetectionResult.getJobId()));
        return startFaceDetectionResult.getJobId();
    }

    @Override
    public List<Face> getFaceDetectionResults(final VideoScan videoScan) {
        List<Face> faceDetections = new ArrayList<>();
        GetFaceDetectionResult getFaceDetectionResult = null;
        String paginationToken = null;
        do {
            if (getFaceDetectionResult != null) {
                paginationToken = getFaceDetectionResult.getNextToken();
            }
            getFaceDetectionResult = amazonRekognitionClient.getFaceDetection(
                    new GetFaceDetectionRequest().withJobId(videoScan.scanId()).withMaxResults(MAX_RESULTS)
                            .withNextToken(paginationToken));
            List<FaceDetection> faces = getFaceDetectionResult.getFaces();
            for (FaceDetection face : faces) {
                faceDetections.add(faceDetectionFactory.create(face, videoScan));
            }
        } while (getFaceDetectionResult != null && getFaceDetectionResult.getNextToken() != null);
        return faceDetections;
    }

    @Override
    public List<Label> getLabelDetectionResults(final VideoScan videoScan) {
        List<Label> labels = new ArrayList<>();
        GetLabelDetectionResult labelDetectionResult = null;
        String paginationToken = null;
        do {
            if (labelDetectionResult != null) {
                paginationToken = labelDetectionResult.getNextToken();
            }
            labelDetectionResult = amazonRekognitionClient.getLabelDetection(
                    new GetLabelDetectionRequest().withMaxResults(MAX_RESULTS).withJobId(videoScan.scanId())
                            .withNextToken(paginationToken).withSortBy(LabelDetectionSortBy.TIMESTAMP));
            for (LabelDetection labelDetection : labelDetectionResult.getLabels()) {
                labels.add(Label.builder().confidence(labelDetection.getLabel().getConfidence())
                        .name(labelDetection.getLabel().getName()).timestamp(labelDetection.getTimestamp())
                        .videoScanId(videoScan.id()).build());
            }
        } while (labelDetectionResult != null && labelDetectionResult.getNextToken() != null);
        return labels;
    }

    @Override
    public List<FaceSearch> getFaceSearchResults(final VideoScan videoScan) {
        List<FaceSearch> faceSearches = new ArrayList<>();
        GetFaceSearchResult faceSearchResult = null;
        String paginationToken = null;
        do {
            if (faceSearchResult != null) {
                paginationToken = faceSearchResult.getNextToken();
            }
            faceSearchResult = amazonRekognitionClient.getFaceSearch(
                    new GetFaceSearchRequest().withJobId(videoScan.scanId()).withMaxResults(MAX_RESULTS)
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

    @Override
    public VideoScan get(final String id) {
        VideoScanItem videoScanItem = videoScanRepository.get(id);
        if (videoScanItem == null) {
            return null;
        }
        Video video = videoService.get(videoScanItem.getVideoId());
        return VideoScan.builder().scanId(videoScanItem.getScanId()).timestamp(videoScanItem.getTimestamp())
                .createdDate(videoScanItem.getCreatedDate()).updatedDate(videoScanItem.getUpdatedDate())
                .id(videoScanItem.getId()).video(video).build();
    }

    @Override
    public VideoScan update(final VideoScan videoScan) {
        VideoScanItem videoScanItem = new VideoScanItem();
        videoScanItem.setScanId(videoScan.scanId());
        videoScanItem.setVideoId(videoScan.video().id());
        videoScanItem.setTimestamp(videoScan.timestamp());
        videoScanItem.setId(videoScan.id());
        VideoScanItem updatedItem = videoScanRepository.update(videoScanItem);
        return VideoScan.builder().video(videoScan.video()).id(updatedItem.getId())
                .updatedDate(updatedItem.getUpdatedDate()).createdDate(updatedItem.getCreatedDate())
                .scanId(updatedItem.getScanId()).timestamp(updatedItem.getTimestamp()).build();
    }

    @Override
    public VideoScan create(final VideoScan videoScan) {
        VideoScanItem videoScanItem = new VideoScanItem();
        videoScanItem.setScanId(videoScan.scanId());
        videoScanItem.setVideoId(videoScan.video().id());
        VideoScanItem createdItem = videoScanRepository.create(videoScanItem);
        return VideoScan.builder().video(videoScan.video()).id(createdItem.getId())
                .updatedDate(createdItem.getUpdatedDate()).createdDate(createdItem.getCreatedDate())
                .scanId(createdItem.getScanId()).timestamp(createdItem.getTimestamp()).build();
    }

    @Override
    public VideoScan getByScanId(final String scanId) {
        VideoScanItem item = videoScanRepository.getByScanId(scanId);
        if (item == null) {
            return null;
        }
        Video video = videoService.get(item.getVideoId());
        return VideoScan.builder().video(video).id(item.getId()).updatedDate(item.getUpdatedDate())
                .createdDate(item.getCreatedDate()).scanId(item.getScanId()).timestamp(item.getTimestamp()).build();
    }
}
