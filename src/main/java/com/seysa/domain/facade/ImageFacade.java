package com.seysa.domain.facade;

import java.util.List;

public interface ImageFacade {

    List<String> getLabels();

    String search(String image);
}
