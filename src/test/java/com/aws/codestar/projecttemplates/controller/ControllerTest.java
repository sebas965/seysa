package com.aws.codestar.projecttemplates.controller;

import com.seysa.infrastructure.api.HelloWorldController;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    private static final String EXPECTED_RESPONSE_VALUE = "Hello AWS CodeStar!";
    private static final String INPUT_NAME = "AWS CodeStar";
    @InjectMocks
    private HelloWorldController helloWorldController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetRequest() throws Exception {
        ResponseEntity responseEntity = helloWorldController.helloWorldGet(INPUT_NAME);
        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(responseEntity.getBody().toString());
        Assert.assertEquals(EXPECTED_RESPONSE_VALUE, jsonObjectFromResponse.get("Output"));
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testPostRequest() {
        ResponseEntity responseEntity = helloWorldController.helloWorldPost(INPUT_NAME);
        // Verify the response obtained matches the values we expect.
        JSONObject jsonObjectFromResponse = new JSONObject(responseEntity.getBody().toString());
        Assert.assertEquals(EXPECTED_RESPONSE_VALUE, jsonObjectFromResponse.get("Output"));
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
