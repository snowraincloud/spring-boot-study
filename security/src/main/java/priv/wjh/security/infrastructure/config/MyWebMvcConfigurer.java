package priv.wjh.security.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.wjh.security.infrastructure.interceptor.WebMDCInterceptor;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
//@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebMDCInterceptor());
    }
}
