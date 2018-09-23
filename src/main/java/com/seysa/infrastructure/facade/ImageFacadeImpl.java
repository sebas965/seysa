package com.seysa.infrastructure.facade;

import com.seysa.domain.facade.ImageFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.CompareFacesRequest;
import software.amazon.awssdk.services.rekognition.model.CompareFacesResponse;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.Label;
import software.amazon.awssdk.services.rekognition.model.S3Object;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImageFacadeImpl implements ImageFacade {

    List<Pair<String, String>> people = new ArrayList<>();
    @Autowired
    private RekognitionClient rekognitionClient;
    @Autowired
    private S3Client s3Client;
    private String photo = "picture.jpg";
    private String bucket = "cajon-de-fotos";

    @PostConstruct
    public void setup() {
        people.add(new ImmutablePair<>("sebastian", "sebas.jpg"));
        people.add((new ImmutablePair<>("Juan Pablo", "juan.jpg")));
        //people.add(new ImmutablePair<>("Juan carlos", "quesada.jpg"));
    }

    @Override
    public List<String> getLabels() {
        DetectLabelsRequest detectLabelsRequest = null;
        //https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        //        BasicAWSCredentials awsCreds = new BasicAWSCredentials("access_key_id", "secret_key_id");
        //        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        //                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
        //                .build();
        DetectLabelsRequest request = DetectLabelsRequest.builder().
                image(Image.builder().s3Object(S3Object.builder().bucket(bucket).name(photo).build()).build())
                .maxLabels(10).minConfidence(75F).build();
        DetectLabelsResponse response = rekognitionClient.detectLabels(request);
        List<Label> labels = response.labels();
        System.out.println("Detected labels for " + photo);
        List<String> listOfLabels = new ArrayList<>();
        for (Label label : labels) {
            System.out.println(label.name() + ": " + label.confidence().toString());
            listOfLabels.add(label.name());
        }
        return listOfLabels;
    }

    @Override
    public String search(final String image1) {
        Image sourceImage = Image.builder().s3Object(S3Object.builder().bucket(bucket).name(image1).build()).build();
        ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder().bucket(bucket).build();
        ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);

//        for (S3Object s3Object: listObjectsResponse.contents()) {
//
//            s3Object.name()
//        }


        for (Pair<String, String> pair : people) {
            Image targetImage = Image.builder()
                    .s3Object(S3Object.builder().bucket(bucket).name(pair.getRight()).build()).build();
            CompareFacesRequest request = CompareFacesRequest.builder().similarityThreshold(95F)
                    .sourceImage(sourceImage).targetImage(targetImage).build();
            CompareFacesResponse response = rekognitionClient.compareFaces(request);
            if (CollectionUtils.isNotEmpty(response.faceMatches())){
                return pair.getLeft();
            }
        }
        return "No Match";
    }

}
