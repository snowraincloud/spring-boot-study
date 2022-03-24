package priv.wjh.study.minio.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import priv.wjh.study.minio.infrastructure.minio.MinioUtil;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("upload")
public class UploadApi {
    private final MinioUtil minioUtil;

    @PostMapping
    public String upload(MultipartFile file) {
        minioUtil.upload(file.getOriginalFilename(), file);
        return minioUtil.getUrl(file.getOriginalFilename());
    }
}
