package ueh.congdunghzz.aws.service.S3Service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import ueh.congdunghzz.aws.common.exception.ApplicationException;
import ueh.congdunghzz.aws.config.AwsConfig;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final S3Client s3Client;
    private final AwsConfig awsProperties;

    public List<S3Object> getAllFiles(){
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(awsProperties.getBucket())
                .build();
        return s3Client.listObjectsV2(request).contents();
    }

    public InputStream getInputStreamByKey(String key){
        GetObjectRequest request = GetObjectRequest.builder()
                .key(key)
                .bucket(awsProperties.getBucket())
                .build();
        try {
            return s3Client.getObject(request);
        }catch (NoSuchKeyException e){
            throw new ApplicationException(HttpStatus.NOT_FOUND, "Key not found");
        }
    }

    public void upload(String key, String contentType, InputStream content) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .storageClass(StorageClass.STANDARD)
                .contentType(contentType)
                .bucket(awsProperties.getBucket())
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(content);
        s3Client.putObject(request, RequestBody.fromInputStream(bufferedInputStream, bufferedInputStream.available()));
    }

    public void delete(String key){
        ObjectIdentifier objectIdentifier = ObjectIdentifier.builder().key(key).build();
        DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                .bucket(awsProperties.getBucket())
                .delete(Delete.builder().objects(objectIdentifier).build())
                .build();
        s3Client.deleteObjects(request);
    }
}
