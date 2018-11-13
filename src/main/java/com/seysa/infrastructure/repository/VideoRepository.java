package com.seysa.infrastructure.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.seysa.infrastructure.repository.model.VideoItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VideoRepository {

    private Logger logger = LogManager.getLogger();
    @Autowired
    private BaseRepository<VideoItem> baseRepository;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public VideoItem get(final String id) {
       return baseRepository.get(id, VideoItem.class);

    }

    ;

    public VideoItem create(final VideoItem item) {
        return baseRepository.create(item, VideoItem.class);

    }

    ;

    public VideoItem update(final VideoItem item) {
        return baseRepository.update(item, VideoItem.class);

    }

    ;

    public VideoItem getByName(final String videoName) {
        final VideoItem item = new VideoItem();
        item.setName(videoName);
        final DynamoDBQueryExpression<VideoItem> queryExpressionSecondary = new DynamoDBQueryExpression<VideoItem>()
                .withHashKeyValues(item).withIndexName(VideoItem.NAME_INDEX).withConsistentRead(false);
        try {
            List<VideoItem> items = dynamoDBMapper.query(VideoItem.class, queryExpressionSecondary);
            if (items.size() > 1) {
                throw new IllegalStateException("VideoName must be unique per video.");
            }
            if (CollectionUtils.isEmpty(items)) {
                return null;
            } else {
                return items.get(0);
            }
        } catch (DynamoDBMappingException e) {
            logger.error("getByName", e);
        } catch (AmazonServiceException ase) {
            logger.error("getByName", ase);
        } catch (AmazonClientException ace) {
            logger.error("getByName", ace);
        }
        return null;
    }

    ;
}
