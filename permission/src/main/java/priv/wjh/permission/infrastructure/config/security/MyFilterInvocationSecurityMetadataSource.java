package priv.wjh.permission.infrastructure.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * <h1>返回资源所需要的权限</h1>
 * @author wangjunh
 */
@Configuration
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger log = LoggerFactory.getLogger(MyFilterInvocationSecurityMetadataSource.class);

    private final ISourcePermissionProvider<? extends ConfigAttribute> sourcePermissionProvider;

    public MyFilterInvocationSecurityMetadataSource(ISourcePermissionProvider<? extends ConfigAttribute> sourcePermissionProvider) {
        this.sourcePermissionProvider = sourcePermissionProvider;
    }

    @Bean
    @ConditionalOnMissingBean(ISourcePermissionProvider.class)
    public static ISourcePermissionProvider<ConfigAttribute> sourcePermissionProvider(){
        return request -> List.of(() -> request.getRequestURI());
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        log.debug("Source uri: [{}]", request.getRequestURI());

        return (Collection<ConfigAttribute>) sourcePermissionProvider.getSource(request);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
