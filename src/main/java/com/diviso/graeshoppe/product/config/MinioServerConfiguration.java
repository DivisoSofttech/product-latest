package com.diviso.graeshoppe.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

@Configuration
public class MinioServerConfiguration {

	@Value("${minio.server.url}")
	private String url;
	
	@Value("${minio.server.accessKey}")
	private String accesskey;
	
	@Value("${minio.server.secretKey}")
	private String secretKey;
	
	@Bean
	public MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
		MinioClient minioClient = new MinioClient(url, accesskey,
				secretKey);
		return minioClient;
	}

}
