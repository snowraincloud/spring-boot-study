package priv.wjh.security.infrastructure.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
public class JwtService {
    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


    public Optional<String> extractJwt(HttpServletRequest request){
        String authHeader = request.getHeader(jwtProperties.getHeader());
        if (authHeader != null && authHeader.startsWith(jwtProperties.getPrefix())){
            String jwt = authHeader.substring(jwtProperties.getPrefix().length());
            return Optional.of(jwt);
        }
        return Optional.empty();
    }


    public String generateAccessToken(String username){
        return generateToken(username, jwtProperties.getSecret(), jwtProperties.getExpireTime());
    }

    public String generateRefreshToken(String username){
        return generateToken(username, jwtProperties.getRefreshSecret(), jwtProperties.getRefreshTokenExpireTime());
    }

    private String generateToken(String username, String secret, long expire) {
        Payload payload = new MyPayload(username, new Date(expire + System.currentTimeMillis()));
        return JwtUtil.generateJws(payload, secret);
    }

    public Optional<String> parserAccessToken(String token){
        return parserToken(token, jwtProperties.getSecret());
    }

    public Optional<String> parserRefreshToken(String token){
        return parserToken(token, jwtProperties.getRefreshSecret());
    }

    private Optional<String> parserToken(String token, String secret) {

        Payload payload = null;
        try {
            payload = JwtUtil.parserJws(token, secret);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
        if (payload.getExpiresAt().getTime() <= System.currentTimeMillis()){
            return Optional.empty();
        }
        return Optional.ofNullable(payload.getSubject());
    }

    @RequiredArgsConstructor
    private static class MyPayload implements Payload{

        private final String subject;
        private final Date expiresAt;

        @Override
        public String getIssuer() {
            return null;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public List<String> getAudience() {
            return null;
        }

        @Override
        public Date getExpiresAt() {
            return expiresAt;
        }

        @Override
        public Date getNotBefore() {
            return null;
        }

        @Override
        public Date getIssuedAt() {
            return null;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public Claim getClaim(String name) {
            return null;
        }

        @Override
        public Map<String, Claim> getClaims() {
            return null;
        }

        @Override
        public String toString() {
            return "MyPayload{" +
                    "subject='" + subject + '\'' +
                    ", expiresAt=" + expiresAt +
                    '}';
        }
    }
}
