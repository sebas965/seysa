package com.seysa.domain.service;

import com.seysa.domain.facade.ImageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Qualifier("ImageFacadeImpl")
    @Autowired
    private ImageFacade imageFacade;

    @Override
    public List<String> getLabels() {
        return imageFacade.getLabels();
    }

    @Override
    public String search(String image) {
        return imageFacade.search(image);
    }

    @Override
    public void addImageToCollection(final String image) {
        imageFacade.addImageToCollection(image);
    }
}
