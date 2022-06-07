package priv.wjh.security.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@AllArgsConstructor
@Configuration
public class SecurityConfig {
    private final MyPermission myPermission;

    @Bean
    public SecurityFilterChain securityFilterChain() {
        List<Filter> filters = new ArrayList<>();
        MyFilter myFilter = new MyFilter(myPermission);
        filters.add(myFilter);
        return new DefaultSecurityFilterChain(new AntPathRequestMatcher("/**"), filters);
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .permitAll()
//                .and()
//                .csrf().disable();
//        return http.build();
//    }

}
