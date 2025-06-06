package at.fhtw.swkom.paperless.services;

import at.fhtw.swkom.paperless.config.MinIOConfig;
import io.minio.*;
import io.minio.errors.*;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class FileStorageImpl implements FileStorage {
    private final MinIOConfig minIOConfig;
    private final MinioClient minioClient;

    @Autowired
    public FileStorageImpl(MinIOConfig minIOConfig, MinioClient minioClient) {
        this.minIOConfig = minIOConfig;
        this.minioClient = minioClient;
    }

    @Override
    public void upload(String objectName, byte[] file) {
        try {
            boolean hasBucketWithName =
                    minioClient.bucketExists(
                            BucketExistsArgs
                                    .builder()
                                    .bucket(minIOConfig.getBucketName())
                                    .build()
                    );
            if (!hasBucketWithName) {
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(minIOConfig.getBucketName())
                                .build()
                );
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .stream(new ByteArrayInputStream(file), file.length, -1)
                            .build()
            );

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] download(String objectName) {
        try {
            return IOUtils.toByteArray(minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minIOConfig.getBucketName())
                            .object(objectName)
                            .build()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from MinIO: " + objectName, e);
        }
    }
}


