package com.seysa.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.seysa.infrastructure.repository.model.LabelItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LabelRepositoryImpl implements LabelRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    @Autowired
    private BaseRepository<LabelItem> baseRepository;

    @Override
    public LabelItem get(final String id) {
        return baseRepository.get(id, LabelItem.class);
    }

    @Override
    public LabelItem create(final LabelItem label) {
        return baseRepository.create(label, LabelItem.class);
    }
}
