package priv.wjh.permission.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import priv.wjh.permission.infrastructure.resolver.MyUnpackResolver;

import java.util.List;

/**
 * @author wangjunhao
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final MyUnpackResolver unpackResolver;

    public WebConfig(MyUnpackResolver unpackResolver) {
        this.unpackResolver = unpackResolver;
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(unpackResolver);
    }
}
