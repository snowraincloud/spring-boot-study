package priv.wjh.permission.infrastructure.config.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <h1>jwt 验证以及获取权限列表</h1>
 *
 * @author wangjunhao
 **/
@Configuration
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final IPermissionProvider<? extends GrantedAuthority> permissionProvider;

    public JwtAuthenticationProvider(IPermissionProvider<? extends GrantedAuthority> permissionProvider) {
        this.permissionProvider = permissionProvider;
    }


    @Bean
    @ConditionalOnMissingBean(IPermissionProvider.class)
    public static IPermissionProvider<GrantedAuthority> permissionProvider(){
        return info -> List.of(() -> "test permission provider");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new JwtAuthentication(authentication.getPrincipal(),
                authentication.getCredentials(), permissionProvider.getPermission((String) authentication.getPrincipal()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthentication.class);
    }
}
