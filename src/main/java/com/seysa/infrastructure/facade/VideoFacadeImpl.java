package com.seysa.infrastructure.facade;

import com.seysa.domain.facade.VideoFacade;
import com.seysa.domain.model.Video;
import com.seysa.infrastructure.repository.VideoRepository;
import com.seysa.infrastructure.repository.model.VideoItem;
import org.springframework.stereotype.Component;

@Component
public class VideoFacadeImpl implements VideoFacade {

    private VideoRepository videoRepository;

    @Override
    public Video get(final String id) {
        VideoItem videoItem = videoRepository.get(id);
        return Video.builder().location(videoItem.getLocation()).name(videoItem.getName())
                .createdDate(videoItem.getCreatedDate()).updatedDate(videoItem.getUpdatedDate()).id(videoItem.getId())
                .build();
    }

    @Override
    public Video update(final Video video) {
        VideoItem videoItem = new VideoItem();
        videoItem.setLocation(video.location());
        videoItem.setName(video.name());
        videoItem.setId(video.id());
        VideoItem updatedItem = videoRepository.update(videoItem);
        return build(updatedItem);
    }

    @Override
    public Video create(final Video video) {
        VideoItem videoItem = new VideoItem();
        videoItem.setLocation(video.location());
        videoItem.setName(video.name());
        videoItem.setId(video.id());
        VideoItem createdItem = videoRepository.create(videoItem);
        return build(createdItem);
    }

    @Override
    public Video getByName(final String name) {
        VideoItem videoItem = videoRepository.getByName(name);
        if(videoItem != null){
            return build(videoItem);
        }
        return null;
    }

    private Video build(final VideoItem videoItem) {
        return Video.builder().location(videoItem.getLocation()).name(videoItem.getName())
                .createdDate(videoItem.getCreatedDate()).updatedDate(videoItem.getUpdatedDate()).id(videoItem.getId())
                .build();
    }
}
