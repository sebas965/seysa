package com.seysa.infrastructure.facade;

import com.seysa.domain.facade.LabelFacade;
import com.seysa.domain.model.Label;
import com.seysa.infrastructure.repository.LabelRepository;
import com.seysa.infrastructure.repository.model.LabelItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LabelFacadeImpl implements LabelFacade {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Label get(final String id) {
        LabelItem labelItem = labelRepository.get(id);
        return Label.builder().timestamp(labelItem.getTimestamp()).name(labelItem.getName())
                .confidence(labelItem.getConfidence()).createdDate(labelItem.getCreatedDate())
                .updatedDate(labelItem.getUpdatedDate()).build();
    }

    @Override
    public Label create(final Label label) {
        LabelItem labelItem = new LabelItem();
        labelItem.setConfidence(label.confidence());
        labelItem.setName(label.name());
        labelItem.setTimestamp(label.timestamp());
        labelItem.setVideoScanId(label.videoScanId());
        LabelItem createdItem = labelRepository.create(labelItem);
        return Label.builder().timestamp(createdItem.getTimestamp()).name(createdItem.getName())
                .confidence(createdItem.getConfidence()).createdDate(createdItem.getCreatedDate())
                .updatedDate(createdItem.getUpdatedDate()).videoScanId(createdItem.getVideoScanId()).build();
    }
}
