package priv.wjh.permission.infrastructure.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * <h1>权限验证</h1>
 * @author wangjunhao
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager{
    private static final Logger log = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (log.isDebugEnabled()){
            authentication.getAuthorities().forEach(msg -> log.debug("authorities: [{}]", msg.getAuthority()));
        }
        if (log.isDebugEnabled()){
            configAttributes.forEach(msg -> log.debug("configAttributes: [{}]", msg.getAttribute()));
        }

        for (ConfigAttribute configAttribute : configAttributes){
            for (GrantedAuthority authority : authentication.getAuthorities()){
                if (!ANT_PATH_MATCHER.match(authority.getAuthority(), configAttribute.getAttribute())){
                    throw new AccessDeniedException("权限不足");
                }
            }
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
