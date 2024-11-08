package com.scart.utilities;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class S3Uploader {
    private static final String BUCKET_NAME = "selenium-test-reports-yourname"; // Replace with your bucket name
    private static final String ACCESS_KEY = "AKIA23WHUJD7DGQD4UM6"; // Replace with your access key
    private static final String SECRET_KEY = "JMP9xYiNe/NKj5cxg+xIviDA3ZTG1dDmtbC83Z9y"; // Replace with your secret key
    private static final Region REGION = Region.AP_SOUTH_1; // Replace with your region
    
    private static final S3Client s3Client = createS3Client();
    private static final Map<String, String> uploadedFiles = new HashMap<>();

    private static S3Client createS3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        return S3Client.builder()
                .region(REGION)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public static String uploadFileToS3(String localFilePath, String fileType) {
        try {
            File file = new File(localFilePath);
            if (!file.exists()) {
                return null;
            }

            // Create a unique key for the file
            String key = fileType + "/" + System.currentTimeMillis() + "_" + file.getName();
            
            // Upload file to S3
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(key)
                    .contentType(getContentType(fileType))
                    .build(),
                    RequestBody.fromFile(file));

            // Generate HTTPS URL
            String httpsUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
                    BUCKET_NAME, REGION.toString(), key);
            
            // Store the mapping for cleanup
            uploadedFiles.put(localFilePath, key);
            
            return httpsUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getContentType(String fileType) {
        switch (fileType.toLowerCase()) {
            case "video":
                return "video/mp4";
            case "image":
                return "image/png";
            case "audio":
                return "audio/mpeg";
            case "excel":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "html":
                return "text/html";
            default:
                return "application/octet-stream";
        }
    }

    // Method to clean up old files from S3
    public static void cleanupOldFiles() {
        for (Map.Entry<String, String> entry : uploadedFiles.entrySet()) {
            try {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(entry.getValue())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        uploadedFiles.clear();
    }
}
