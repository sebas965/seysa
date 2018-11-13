package com.seysa.infrastructure.facade;

import com.seysa.domain.facade.FaceFacade;
import com.seysa.domain.model.Face;
import com.seysa.infrastructure.repository.FaceRepository;
import com.seysa.infrastructure.repository.model.FaceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FaceFacadeImpl implements FaceFacade {

    @Autowired
    private FaceRepository faceRepository;

    @Override
    public Face get(final String id) {
        FaceItem faceItem = faceRepository.get(id);
        return Face.builder()
                .timestamp(faceItem.getTimestamp())
                .videoScanId(faceItem.getVideoScanId())
                .createdDate(faceItem.getCreatedDate())
                .updatedDate(faceItem.getUpdatedDate())
                .id(faceItem.getId())
                .boundingBox(faceItem.getBoundingBox())
                .beard(faceItem.getBeard())
                .confidence(faceItem.getConfidence())
                .emotions(faceItem.getEmotions())
                .eyeglasses(faceItem.getEyeglasses())
                .eyeOpen(faceItem.getEyeOpen())
                .gender(faceItem.getGender())
                .imageQuality(faceItem.getImageQuality())
                .landmarks(faceItem.getLandmarks())
                .maxAge(faceItem.getMaxAge())
                .minAge(faceItem.getMinAge())
                .mouthOpen(faceItem.getMouthOpen())
                .mustache(faceItem.getMustache())
                .pose(faceItem.getPose())
                .smile(faceItem.getSmile())
                .sunglasses(faceItem.getSunglasses())
                .build();
    }

    @Override
    public Face create(final Face face) {
        FaceItem faceItem = new FaceItem();
        faceItem.setTimestamp(face.timestamp());
        faceItem.setVideoScanId(face.videoScanId());
        faceItem.setBoundingBox(face.boundingBox());
        faceItem.setBeard(face.beard());
        faceItem.setConfidence(face.confidence());
        faceItem.setEmotions(face.emotions());
        faceItem.setEyeglasses(face.eyeglasses());
        faceItem.setEyeOpen(face.eyeOpen());
        faceItem.setGender(face.gender());
        faceItem.setImageQuality(face.imageQuality());
        faceItem.setLandmarks(face.landmarks());
        faceItem.setMaxAge(face.maxAge());
        faceItem.setMinAge(face.minAge());
        faceItem.setMouthOpen(face.mouthOpen());
        faceItem.setMustache(face.mustache());
        faceItem.setPose(face.pose());
        faceItem.setSmile(face.smile());
        faceItem.setSunglasses(face.sunglasses());
        FaceItem createdItem = faceRepository.create(faceItem);
        return Face.builder().timestamp(createdItem.getTimestamp()).id(createdItem.getId())
                .updatedDate(createdItem.getUpdatedDate()).createdDate(createdItem.getCreatedDate())
                .videoScanId(createdItem.getVideoScanId()).boundingBox(createdItem.getBoundingBox())
                .beard(createdItem.getBeard()).confidence(createdItem.getConfidence())
                .emotions(createdItem.getEmotions()).eyeglasses(createdItem.getEyeglasses())
                .eyeOpen(createdItem.getEyeOpen()).gender(createdItem.getGender())
                .imageQuality(createdItem.getImageQuality()).landmarks(createdItem.getLandmarks())
                .maxAge(createdItem.getMaxAge()).minAge(createdItem.getMinAge()).mouthOpen(createdItem.getMouthOpen())
                .mustache(createdItem.getMustache()).pose(createdItem.getPose()).smile(createdItem.getSmile())
                .sunglasses(createdItem.getSunglasses()).build();
    }
}
