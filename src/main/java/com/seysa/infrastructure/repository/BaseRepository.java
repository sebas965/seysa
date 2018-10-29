package com.seysa.infrastructure.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.seysa.infrastructure.repository.model.Item;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepository<T extends Item> {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public T get(final String id, final Class<T> tClass) {
        try {
            return dynamoDBMapper.load(tClass, id);
        } catch (DynamoDBMappingException e) {
            logger.error("get by Id" + e);
        } catch (AmazonServiceException ase) {
            logger.error("get by Id" + ase);
        } catch (AmazonClientException ace) {
            logger.error("get by id" + ace);
        }
        return null;
    }

    public T create(final T item, final Class<T> tClass) {
        if (StringUtils.isNotEmpty(item.getId()) && get(item.getId(), tClass) != null) {
            return null;
        }
        try {
            dynamoDBMapper.save(item);
            return item;
        } catch (DynamoDBMappingException e) {
            logger.error("create" + e);
        } catch (AmazonServiceException ase) {
            logger.error("create" + ase);
        } catch (AmazonClientException ace) {
            logger.error("create" + ace);
        }
        return null;
    }

    public T update(final T item, final Class<T> tClass) {
        Validate.notEmpty(item.getId(), "Unable to update item with empty ID.");
        try {
            dynamoDBMapper.save(item, DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config());
            return item;
        } catch (DynamoDBMappingException e) {
            logger.error("Unable to update item.", e);
        } catch (AmazonServiceException ase) {
            logger.error("Unable to update item.", ase);
        } catch (AmazonClientException ace) {
            logger.error("Unable to update item.", ace);
        }
        return null;
    }
}
