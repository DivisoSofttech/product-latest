package com.diviso.graeshoppe.product.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.diviso.graeshoppe.product.service.impl.ProductServiceImpl;

import io.minio.MinioClient;

@Service
public class ImageService {

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private MinioClient minioClient;

	@Value("${minio.buckets.product}")
	private String productBucketName;

	@Value("${minio.buckets.category}")
	private String categoryBucketName;

	@Value("${minio.configuration.contentType}")
	private String contentType;

	@SuppressWarnings("deprecation")
	public void saveFile(String type, Long entityId, byte[] data) {
		InputStream streamData = new ByteArrayInputStream(data);
		String bucket = null;
		String imageName = null; 
		if (type.equals("product")) {
			bucket = productBucketName;
			imageName = entityId+"-product-image.png";
			log.info("Saving the product Image in bucket "+bucket+" imagename is "+imageName);
		} else if (type.equals("category")) {
			bucket = categoryBucketName;
			imageName = entityId+"-category-image.png";
			log.info("Saving the product Image in bucket "+bucket+" imagename is "+imageName);
		}
		try {
			minioClient.putObject(bucket, imageName, streamData, contentType);
			log.info("Image has been saved successfully");

		} catch (Exception e) {
			log.error("Something went wrong while saving image to miniob server "+ e.getMessage());
		}
	}

}
