package priv.wjh.permission.infrastructure.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import priv.wjh.permission.infrastructure.jwt.JwtService;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;
    private final MyAccessDecisionManager myAccessDecisionManager;
    private final MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
    private final JwtService jwtService;

    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider, CorsConfigurationSource corsConfigurationSource, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler, MyAccessDecisionManager myAccessDecisionManager, MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource, JwtService jwtService) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.corsConfigurationSource = corsConfigurationSource;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.myAccessDecisionManager = myAccessDecisionManager;
        this.myFilterInvocationSecurityMetadataSource = myFilterInvocationSecurityMetadataSource;
        this.jwtService = jwtService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        // 注入权限验证
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        // 注入权限资源获取
                        object.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .addFilterAfter(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        ;

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/test", "/error",
                "/system/login", "/system/getValidateCode", "/system/logout",
                "/driver/login/**", "/org/common/**"
        );
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 用户信息获取
        auth
                .authenticationProvider(jwtAuthenticationProvider)
        ;

    }

    /**
     * 我们在 Spring Boot 中有几种其他方式配置 CORS
     * 参见 https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
     * Mvc 的配置方式见 WebMvcConfig 中的代码
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        configuration.addExposedHeader("X-Authenticate");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    /**
     * 配置认证使用的密码加密算法：BCrypt
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
