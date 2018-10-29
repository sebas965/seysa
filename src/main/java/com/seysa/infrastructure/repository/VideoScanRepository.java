package com.seysa.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.seysa.infrastructure.repository.model.VideoScanItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VideoScanRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private BaseRepository<VideoScanItem> baseRepository;
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public VideoScanItem get(final String id) {
        return baseRepository.get(id, VideoScanItem.class);
    }

    ;

    public VideoScanItem create(final VideoScanItem item) {
        return baseRepository.create(item, VideoScanItem.class);
    }

    ;

    public VideoScanItem update(final VideoScanItem item) {
        return baseRepository.update(item, VideoScanItem.class);
    }

    ;

}
