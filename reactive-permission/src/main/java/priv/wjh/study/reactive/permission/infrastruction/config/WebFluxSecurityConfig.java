package priv.wjh.study.reactive.permission.infrastruction.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Configuration
@RequiredArgsConstructor
public class WebFluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http){
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) -> {
                            swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                            return swe.getResponse().writeWith(
                                    Mono.just(new DefaultDataBufferFactory().wrap("请登录！".getBytes(StandardCharsets.UTF_8))));
                        })
                .accessDeniedHandler((swe, e) -> {
                    swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return swe.getResponse().writeWith(Mono.just(new DefaultDataBufferFactory().wrap("权限不足！".getBytes(
                            StandardCharsets.UTF_8))));
                }).and()
                .authorizeExchange()
                .pathMatchers("/auth/login").permitAll()
                .anyExchange().authenticated().and()
                .addFilterAt(new MyJwtFilter(), SecurityWebFiltersOrder.HTTP_BASIC);

        return http.build();
    }
}
