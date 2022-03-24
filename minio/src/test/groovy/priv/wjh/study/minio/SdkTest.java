package priv.wjh.study.minio;

import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class SdkTest {


    @Test
    public void test(){
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://192.168.56.101:30010")
                            .credentials("UYECYXZ10FI1N4ELSSSE", "MTf2sqr9GLT6xGPeTyFraLIXCEdhRP4W6WWC4k+y")
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("asiatrip").build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket("asiatrip")
                            .object("cat11.jpg")
                            .filename("C:\\Users\\W\\Desktop\\file_type\\cat11.jpg")
                            .build());
            System.out.println(
                    "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
                            + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");

            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket("asiatrip")
                                    .object("cat11.jpg")
                                    .expiry(60 * 60 * 24)
                                    .build());
            System.out.println(url);
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e);
        }
    }
}
