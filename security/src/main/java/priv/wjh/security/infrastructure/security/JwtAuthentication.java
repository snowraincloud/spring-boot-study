package priv.wjh.security.infrastructure.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    public JwtAuthentication(Object principal) {
        super(null);
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(false);
    }

    public JwtAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
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
}
