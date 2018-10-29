package com.seysa.domain.service;

import com.seysa.domain.model.Video;

public interface VideoService {

    Video get(final String id);

    Video update(final Video video);

    Video create(final Video video);

    Video getByName(final String videoName);

}
