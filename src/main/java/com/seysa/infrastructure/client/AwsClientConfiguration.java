package com.seysa.infrastructure.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsClientConfiguration {

    private static final Region REGION = Region.US_WEST_2;
    @Autowired
    private AwsCredentialsProvider awsCredentialsProvider;
    @Autowired
    private AWSStaticCredentialsProvider staticCredentialsProvider;
    @Value("${dynamo.endpoint}")
    private String dynamoEndpoint;
    @Value("${dynamo.region}")
    private String dynamoRegion;

    @Bean
    public RekognitionClient rekognitionClient() {
        return RekognitionClient.builder().region(REGION).credentialsProvider(awsCredentialsProvider).build();
    }

    @Bean
    public AmazonRekognition amazonRekognitionClient(){
        return AmazonRekognitionClientBuilder.standard().withRegion("us-west-2").withCredentials(staticCredentialsProvider).build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder().region(REGION).credentialsProvider(awsCredentialsProvider).build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().region(REGION).credentialsProvider(awsCredentialsProvider).build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClient.builder().withCredentials(staticCredentialsProvider)
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoEndpoint, dynamoRegion))
                .build();
    }

    @Bean
    DynamoDBMapper dynamoDBMapper(
            @Autowired
            final AmazonDynamoDB dynamoDbClient) {
        return new DynamoDBMapper(dynamoDbClient);
    }
}
