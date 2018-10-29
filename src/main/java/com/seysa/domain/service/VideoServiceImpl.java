package com.seysa.domain.service;

import com.seysa.domain.facade.VideoFacade;
import com.seysa.domain.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService{

    @Autowired
    private VideoFacade videoFacade;

    @Override
    public Video get(final String id) {
        return videoFacade.get(id);
    }

    @Override
    public Video update(final Video video) {
        return videoFacade.update(video);
    }

    @Override
    public Video create(final Video video) {
        return videoFacade.create(video);
    }

    @Override
    public Video getByName(final String name) {
        return videoFacade.getByName(name);
    }
}
