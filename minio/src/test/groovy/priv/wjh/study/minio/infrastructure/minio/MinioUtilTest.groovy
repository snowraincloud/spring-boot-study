package priv.wjh.study.minio.infrastructure.minio


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import spock.lang.Specification

@SpringBootTest
class MinioUtilTest extends Specification {
    @Autowired
    private MinioUtil minioUtil


    def "Upload"() {
        given:
        def file = new MockMultipartFile("test", "test.txt","text/plain", "test".getBytes())
        minioUtil.upload("test", file)
    }

    def "GetUrl"() {
        given:
        String url = minioUtil.getUrl("key")
        println(url)
    }



}
