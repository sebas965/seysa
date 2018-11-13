package com.seysa.infrastructure.factory;

import com.amazonaws.services.rekognition.model.FaceDetection;
import com.seysa.domain.model.Face;
import com.seysa.domain.model.VideoScan;
import com.seysa.infrastructure.util.GsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FaceDetectionFactory {

    @Autowired
    private GsonSerializer gsonSerializer;

    public Face create(final FaceDetection faceDetection, final VideoScan videoScan){
        return Face.builder()
                .boundingBox(gsonSerializer.getJsonRepresentation(faceDetection.getFace().getBoundingBox()))
                .timestamp(faceDetection.getTimestamp())
                .videoScanId(videoScan.id())
                .maxAge(faceDetection.getFace().getAgeRange().getHigh())
                .minAge(faceDetection.getFace().getAgeRange().getLow())
                .smile(faceDetection.getFace().getSmile().getValue())
                .eyeglasses(faceDetection.getFace().getEyeglasses().getValue())
                .sunglasses(faceDetection.getFace().getSunglasses().getValue())
                .gender(faceDetection.getFace().getGender().getValue())
                .beard(faceDetection.getFace().getBeard().getValue())
                .mustache(faceDetection.getFace().getMustache().getValue())
                .eyeOpen(faceDetection.getFace().getEyesOpen().getValue())
                .mouthOpen(faceDetection.getFace().getMouthOpen().getValue())
                .emotions(faceDetection.getFace().getEmotions().stream().map(x -> x.getType()).collect(Collectors.toList()))
                .landmarks(faceDetection.getFace().getLandmarks().stream().map(x -> x.getType()).collect(Collectors.toList()))
                .pose(gsonSerializer.getJsonRepresentation(faceDetection.getFace().getPose()))
                .imageQuality(gsonSerializer.getJsonRepresentation(faceDetection.getFace().getQuality()))
                .confidence(faceDetection.getFace().getConfidence())
                .build();
    }

}
