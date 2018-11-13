package com.seysa.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.seysa.infrastructure.repository.model.FaceItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FaceRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    @Autowired
    private BaseRepository<FaceItem> baseRepository;

    public FaceItem get(final String id) {
        return baseRepository.get(id, FaceItem.class);
    }

    public FaceItem create(final FaceItem face) {
        return baseRepository.create(face, FaceItem.class);
    }
}
