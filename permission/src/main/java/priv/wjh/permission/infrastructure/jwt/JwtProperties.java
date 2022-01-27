package priv.wjh.permission.infrastructure.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Setter
@Getter
@ToString
@Validated
@Configuration
@ConfigurationProperties(prefix = "me.jwt")
@ConditionalOnProperty(value = {"secret", "refresh-secret"}, prefix = "me.jwt")
public class JwtProperties {
    /**
     * HTTP 报头的认证字段的 key
     */
    private String header = "Authorization";
    /**
     * HTTP 报头的认证字段的值的前缀
     */
    private String prefix = "Bearer ";
    /**
     * Access Token 签名密钥
     */
    private String secret;
    /**
     * Access Token 过期时间
     */
    @Min(60000L)
    private long expireTime = 10 * 60 * 1000L;
    /**
     * Refresh Token 签名密钥
     */
    private String refreshSecret;
    /**
     * Refresh Token 过期时间
     */
    @Min(3600000L)
    private long refreshTokenExpireTime = 30 * 24 * 60 * 60 * 1000L;

}
