package ueh.congdunghzz.aws.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@ConfigurationProperties("aws")
@Data
public class AwsConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String region;
    private String endpoint;

    private AwsCredentialsProvider awsCredentialsProvider(){
        AwsCredentials awsCredentials = AwsBasicCredentials
                .builder()
                .accessKeyId(accessKey)
                .secretAccessKey(secretKey)
                .build();
        return StaticCredentialsProvider.create(awsCredentials);
    }
    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider())
                .endpointOverride(URI.create(endpoint))
                .forcePathStyle(true)
                .build();
    }
}
