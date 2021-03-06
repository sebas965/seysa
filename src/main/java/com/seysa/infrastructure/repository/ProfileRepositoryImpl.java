package com.seysa.infrastructure.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.seysa.infrastructure.repository.model.ProfileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    @Autowired
    private BaseRepository<ProfileItem> baseRepository;

    @Override
    public ProfileItem get(final String id) {
        return baseRepository.get(id, ProfileItem.class);
    }

    @Override
    public ProfileItem create(final ProfileItem item) {
        return baseRepository.create(item, ProfileItem.class);
    }
}
