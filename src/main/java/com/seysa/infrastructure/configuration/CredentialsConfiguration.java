package com.seysa.infrastructure.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
public class CredentialsConfiguration {

    @Value("${aws.accessKeyId}")
    private String amazonAWSAccessKey;
    @Value("${aws.secretAccessKey}")
    private String amazonAWSSecretKey;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey));
        //        System.setProperty("aws.accessKeyId", "AKIAINLVOXOB27DHUEZQ");
        //        System.setProperty("aws.secretAccessKey", "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");
        //        return SystemPropertyCredentialsProvider.create();
    }

    @Bean
    public AWSStaticCredentialsProvider awsStaticCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }
}
