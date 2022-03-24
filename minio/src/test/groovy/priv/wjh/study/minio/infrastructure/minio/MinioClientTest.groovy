package priv.wjh.study.minio.infrastructure.minio

import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MinioClientTest extends Specification{
    @Autowired
    private MinioClient minioClient
    @Autowired
    private MinioProperty minioProperty

    def "create folder"() {
        given:
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperty.getBucketName())
                        .object("path/to/")
                        .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                        .build())
    }
}
