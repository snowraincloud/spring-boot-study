package priv.wjh.study.minio.infrastructure.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
@RequiredArgsConstructor
public class MinioUtil {
    private final MinioClient minioClient;
    private final MinioProperty minioProperty;

    public void upload(String key, MultipartFile file) {
        try {
            final InputStream in = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperty.getBucketName())
                            .object(key)
                            .stream(in, in.available(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String key) {
        return minioProperty.getUrl() + "/" + minioProperty.getBucketName() + "/" + key;
    }
}
