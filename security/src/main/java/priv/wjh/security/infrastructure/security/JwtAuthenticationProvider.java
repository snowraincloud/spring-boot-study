package priv.wjh.security.infrastructure.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>jwt 验证以及获取权限列表</h1>
 *
 * @author wangjunhao
 **/
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            authorities.add((GrantedAuthority) () -> "authority" + finalI);
        }
        
        return new JwtAuthentication("test", "", authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthentication.class);
    }
}
