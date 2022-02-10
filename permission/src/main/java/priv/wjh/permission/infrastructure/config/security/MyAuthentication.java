package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <h1>token</h1>
 *
 * @author wangjunhao
 **/
public class MyAuthentication extends AbstractAuthenticationToken {

    private final Long principal;

    private final Object credentials;

    public MyAuthentication(Long principal, String credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(false);
    }



    public MyAuthentication(Long principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Long getId(){
        return principal;
    }
}
