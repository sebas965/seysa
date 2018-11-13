package com.seysa.domain.facade;

import com.seysa.domain.model.Face;

public interface FaceFacade {

    Face get(final String id);

    Face create(final Face face);

}
