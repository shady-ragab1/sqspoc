package com.shady.sqspoc.sqspoc;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSns;
import org.springframework.cloud.aws.messaging.core.NotificationMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class SqspocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqspocApplication.class, args);
	}

	// Value is populated by the region code.
	@Value("${cloud.aws.region.static}")
	private String region;

	// Value is populated with the aws access key.
	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;

	// Value is populated with the aws secret key
	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Bean
	@Primary
	public AmazonSQSAsync amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder
				.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				.build();
	}


	@Bean
	public NotificationMessagingTemplate notificationMessagingTemplate() {
		return new NotificationMessagingTemplate(amazonSNSClient());
	}

	@Bean
	@Primary
	public AmazonSNSClient amazonSNSClient() {
		return (AmazonSNSClient) AmazonSNSClientBuilder
				.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				.build();
	}



}
