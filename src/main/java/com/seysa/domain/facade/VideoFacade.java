package com.seysa.domain.facade;

import com.seysa.domain.model.Video;

public interface VideoFacade {

    Video get(final String id);

    Video update(final Video video);

    Video create(final Video video);

    Video getByName(final String videoName);

}
