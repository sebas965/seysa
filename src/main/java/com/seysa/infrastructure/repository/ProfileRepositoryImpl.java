package com.seysa.infrastructure.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.seysa.infrastructure.model.ProfileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public ProfileItem getByUuid(final String uuid) {
        try {
            return dynamoDBMapper.load(ProfileItem.class, uuid);
        } catch (DynamoDBMappingException e) {
            logger.error("getByUuid" + e);
        } catch (AmazonServiceException ase) {
            logger.error("getByUuid" + ase);
        } catch (AmazonClientException ace) {
            logger.error("getByUuid" + ace);
        }
        return null;
    }

    @Override
    public ProfileItem create(final ProfileItem item) {
        if (StringUtils.isNotEmpty(item.getUuid()) && getByUuid(item.getUuid()) != null) {
            return null;
        }
        try {
            dynamoDBMapper.save(item);
            return item;
        } catch (DynamoDBMappingException e) {
            logger.error("getByUuid" + e);
        } catch (AmazonServiceException ase) {
            logger.error("getByUuid" + ase);
        } catch (AmazonClientException ace) {
            logger.error("getByUuid" + ace);
        }
        return null;
    }
}
