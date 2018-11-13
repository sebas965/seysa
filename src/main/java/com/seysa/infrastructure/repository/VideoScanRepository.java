package com.seysa.infrastructure.repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.seysa.infrastructure.repository.model.VideoScanItem;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public VideoScanItem getByScanId(final String scanId){

        final VideoScanItem item = new VideoScanItem();
        item.setScanId(scanId);
        final DynamoDBQueryExpression<VideoScanItem> queryExpressionSecondary = new DynamoDBQueryExpression<VideoScanItem>()
                .withHashKeyValues(item).withIndexName(VideoScanItem.SCAN_ID_INDEX).withConsistentRead(false);
        try {
            List<VideoScanItem> items = dynamoDBMapper.query(VideoScanItem.class, queryExpressionSecondary);
            if (items.size() > 1) {
                throw new IllegalStateException("Scan ID must be unique per video scan.");
            }
            if (CollectionUtils.isEmpty(items)) {
                return null;
            } else {
                return items.get(0);
            }
        } catch (DynamoDBMappingException e) {
            logger.error("getByScanId", e);
        } catch (AmazonServiceException ase) {
            logger.error("getByScanId", ase);
        } catch (AmazonClientException ace) {
            logger.error("getByScanId", ace);
        }
        return null;

    }
}
