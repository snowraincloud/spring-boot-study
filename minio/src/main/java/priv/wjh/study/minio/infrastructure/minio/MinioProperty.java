package priv.wjh.study.minio.infrastructure.minio;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Data
@Configuration
@ConfigurationProperties("me.minio")
public class MinioProperty {
    private String url;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Bean
    public static MinioClient minioClient(MinioProperty minioProperty){
        return MinioClient.builder()
                        .endpoint(minioProperty.url)
                        .credentials(minioProperty.accessKey, minioProperty.secretKey)
                        .build();
    }
}
