package com.seysa.domain.service;

import com.seysa.domain.facade.VideoFacade;
import com.seysa.domain.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService{

    @Autowired
    private VideoFacade videoFacade;


    public Video get(final String id) {
        return videoFacade.get(id);
    }

    public Video update(final Video video) {
        return videoFacade.update(video);
    }

    public Video create(final Video video) {
        return videoFacade.create(video);
    }

    public Video getByName(final String name) {
        return videoFacade.getByName(name);
    }
}
