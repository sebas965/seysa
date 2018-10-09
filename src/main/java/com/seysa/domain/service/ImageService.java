package com.seysa.domain.service;

import java.util.List;

public interface ImageService {


    List<String> getLabels();

    String search(String image);

    void addImageToCollection(final String image);
}
