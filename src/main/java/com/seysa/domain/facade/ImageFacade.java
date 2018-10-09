package com.seysa.domain.facade;

import java.util.List;

public interface ImageFacade {

    List<String> getLabels();

    String search(String image);

    void addImageToCollection(final String imageFileName);

    String createCollection(final String collectionId);

    String describeCollection(final String collectionId);
}
