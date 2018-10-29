package com.seysa.infrastructure.repository;

import com.seysa.infrastructure.repository.model.LabelItem;

public interface LabelRepository {

    LabelItem get(final String id);

    LabelItem create(final LabelItem item);

}
