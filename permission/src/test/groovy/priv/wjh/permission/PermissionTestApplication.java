package priv.wjh.permission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import priv.wjh.permission.application.PermissionApplicationService;

/**
 *
 * @author wangjunhao
 **/

@SpringBootApplication
public class PermissionTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermissionApplicationService.class, args);
    }
}
