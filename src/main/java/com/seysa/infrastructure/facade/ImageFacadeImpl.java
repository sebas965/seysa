package com.seysa.infrastructure.facade;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;
import com.amazonaws.services.rekognition.model.DescribeCollectionRequest;
import com.amazonaws.services.rekognition.model.DescribeCollectionResult;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.seysa.domain.facade.ImageFacade;
import com.seysa.infrastructure.factory.ImageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Qualifier("ImageFacadeImpl")
@Service
public class ImageFacadeImpl implements ImageFacade {

    @Value("${s3.image.bucket}")
    private String imageBucket;
    @Autowired
    private AmazonRekognition amazonRekognitionClient;
    @Autowired
    private ImageFactory imageFactory;

    @Value("${rekognition.collection.id}")
    private String rekognitionCollectionId;


    @Override
    public List<String> getLabels() {
        throw new UnsupportedOperationException("Missing implementation");
    }

    @Override
    public String search(String image) {
        throw new UnsupportedOperationException("Missing implementation");
    }

    @Override
    public String createCollection(final String collectionId) {
        CreateCollectionRequest createCollectionRequest = new CreateCollectionRequest().withCollectionId(collectionId);
        CreateCollectionResult createCollectionResult = amazonRekognitionClient
                .createCollection(createCollectionRequest);
        return createCollectionResult.getCollectionArn();
    }

    @Override
    public String describeCollection(final String collectionId) {
        DescribeCollectionRequest describeCollectionRequest = new DescribeCollectionRequest()
                .withCollectionId(collectionId);
        DescribeCollectionResult describeCollectionResult = amazonRekognitionClient
                .describeCollection(describeCollectionRequest);
        return describeCollectionResult.getCollectionARN();
        //        describeCollectionResult.getSdkHttpMetadata().getHttpStatusCode();
    }

    @Override
    public void addImageToCollection(final String imageFileName) {
        Image image = imageFactory.createImage(imageFileName, imageBucket);
        UUID imageId = UUID.randomUUID();
        IndexFacesRequest indexFacesRequest = new IndexFacesRequest().withImage(image)
                //.withQualityFilter(QualityFilter.AUTO)
                //.withMaxFaces(1)
                .withCollectionId(rekognitionCollectionId).withExternalImageId(imageId.toString())
                .withDetectionAttributes("DEFAULT");
        IndexFacesResult indexFacesResult = amazonRekognitionClient.indexFaces(indexFacesRequest);
        System.out.println("Results for " + imageFileName);
        System.out.println("Faces indexed:");
        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
        for (FaceRecord faceRecord : faceRecords) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());
        }
        //        List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
        //        System.out.println("Faces not indexed:");
        //        for (UnindexedFace unindexedFace : unindexedFaces) {
        //            System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
        //            System.out.println("  Reasons:");
        //            for (String reason : unindexedFace.getReasons()) {
        //                System.out.println("   " + reason);
        //            }
        //        }
    }
}
