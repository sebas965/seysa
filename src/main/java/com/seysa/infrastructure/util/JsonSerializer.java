package com.seysa.infrastructure.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class JsonSerializer {

    @Autowired
    private Gson gson;

    public String getJsonRepresentation(final Object object) {
        return gson.toJson(object);
    }

    public <T> T getObjectFromJson(final String jsonBody, final Class<T> objectClass) {
        return gson.fromJson(jsonBody, objectClass);
    }
}
