package com.insert.ioj.domain.file.service;

import com.insert.ioj.domain.user.facade.UserFacade;
import com.insert.ioj.global.config.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {
    private static final long DURATION_OF_PRESIGNED_URL_MINUTE = 30L;

    private final S3Properties s3Properties;
    private final AwsCredentialsProvider awsCredentialsProvider;
    private final UserFacade userFacade;

    public String generatePreSignedUrlToUpload(String fileName) {
        Long userId = userFacade.getCurrentUserId();
        String encodedFileName = createUUID() + "_" + fileName;
        String objectName = userId + "/" + encodedFileName;

        try (S3Presigner presigner = S3Presigner.builder()
            .credentialsProvider(awsCredentialsProvider)
            .region(Region.AP_NORTHEAST_2)
            .build()) {

            PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(objectName)
                .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(DURATION_OF_PRESIGNED_URL_MINUTE))
                .putObjectRequest(putRequest)
                .build();

            return presigner.presignPutObject(presignRequest)
                .url()
                .toExternalForm();
        }
    }

    public String generatePreSignedUrlToDownload(String objectName) {
        try (S3Presigner presigner = S3Presigner.builder()
            .credentialsProvider(awsCredentialsProvider)
            .region(Region.AP_NORTHEAST_2)
            .build()) {

            GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(objectName)
                .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(DURATION_OF_PRESIGNED_URL_MINUTE))
                .getObjectRequest(getRequest)
                .build();

            return presigner.presignGetObject(presignRequest)
                .url()
                .toExternalForm();
        }
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }
}

