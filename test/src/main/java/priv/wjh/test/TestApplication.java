package priv.wjh.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.wjh.test.factory.bean.MyFactoryBean;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Configuration
@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public MyFactoryBean MyBean(){
        return new MyFactoryBean();
    }
}
