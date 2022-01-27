package priv.wjh.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@MapperScan(basePackages = "priv.wjh.permission.domain.auth.dao")
@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);

    }
}
