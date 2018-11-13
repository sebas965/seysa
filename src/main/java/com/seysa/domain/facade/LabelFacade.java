package com.seysa.domain.facade;

import com.seysa.domain.model.Label;

public interface LabelFacade {

    Label get(final String id);

    Label create(final Label label);
}
