package com.seysa.infrastructure.listener.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum VideoScanType {
    FACE_SEARCH("StartFaceSearch"), LABEL_DETECTION("StartLabelDetection"), FACE_DETECTION("StartFaceDetection");
    private static final Map<String, VideoScanType> ENUM_MAP;

    static {
        Map<String, VideoScanType> map = new ConcurrentHashMap<>();
        for (VideoScanType instance : VideoScanType.values()) {
            map.put(instance.getType(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    private String type;

    VideoScanType(final String type) {
        this.type = type;
    }
    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    public static VideoScanType get(final String type) {
        return ENUM_MAP.get(type);
    }

    public String getType() {
        return type;
    }
}
