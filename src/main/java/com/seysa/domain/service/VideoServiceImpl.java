package com.seysa.domain.service;

import com.seysa.domain.facade.VideoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoFacade videoFacade;

    @Override
    public void scan(final String fileName) {
        videoFacade.scan(fileName);
    }

    @Override
    public void getResultsFaceSearchCollection(final String jobId) {
        videoFacade.getResultsFaceSearchCollection(jobId);
    }
}
