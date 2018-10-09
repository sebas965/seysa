package com.seysa.infrastructure.api;

import com.seysa.domain.service.ImageService;
import com.seysa.domain.service.VideoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Basic Spring web service controller that handles all GET requests.
 */
@RestController
@RequestMapping("/")
public class HelloWorldController {

    private static final String MESSAGE_FORMAT = "Hello %s!";

    @Autowired
    private ImageService imageService;

    @Autowired
    private VideoService videoService;


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity helloWorldGet(@RequestParam(value = "image", defaultValue = "World") String image) {

        //return ResponseEntity.ok(createResponse(        imageService.getLabels()));
        //return ResponseEntity.ok(createResponse(name));
        imageService.addImageToCollection("juan.jpg");
        videoService.scan("video.mp4");

        return ResponseEntity.ok(createResponse(image));
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity helloWorldPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(createResponse(name));
    }

    private String createResponse(String name) {
        return new JSONObject().put("Output", String.format(MESSAGE_FORMAT, name)).toString();
    }

    private String createResponse(List<String> labels){
        return new JSONObject().put("Output", labels).toString();

    }
}
