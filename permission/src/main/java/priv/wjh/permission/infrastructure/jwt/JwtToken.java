package priv.wjh.permission.infrastructure.jwt;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@ConfigurationProperties(prefix = "me.jwt")
public class JwtToken {
    //token有效时长
    private long tokenTime = 1000*60*30*10000;

    //密钥
    private String tokenKey = "123456";

    //使用jwt根据用户名生成token
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenTime))
                .signWith(SignatureAlgorithm.HS512, tokenKey).compressWith(CompressionCodecs.GZIP).compact();

        return token;
    }

    //根据token字符串得到用户信息
    public String getUserInfoFromToken(String token) {
        String userinfo;
        try{
            userinfo = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e){
            log.info("token解析失败, ", e);
            return null;
        }
        return userinfo;
    }
}
