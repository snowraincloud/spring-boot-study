package priv.wjh.security.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <h1>验证访问资格</h1>
 * @author wangjunhao
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager{
    private static final Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);


    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (logger.isDebugEnabled()){
            authentication.getAuthorities().forEach(msg -> logger.debug("authorities: [{}]", msg.getAuthority()));
        }
        if (logger.isDebugEnabled()){
            configAttributes.forEach(msg -> logger.debug("configAttributes: [{}]", msg.getAttribute()));
        }

        throw new AccessDeniedException("11111");
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
