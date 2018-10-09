package com.seysa.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource("classpath:s3.properties"),
        @PropertySource("classpath:dynamo.properties"),
        @PropertySource("classpath:sns.properties"),
        @PropertySource("classpath:rekognition.properties"),
        @PropertySource("classpath:sqs.properties")
})
public class PropertyConfiguration {


}
