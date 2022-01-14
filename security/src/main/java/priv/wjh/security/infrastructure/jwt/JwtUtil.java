package priv.wjh.security.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Payload;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class JwtUtil  {

    public static String generateJws(Payload data, String secret) {
        return JWT.create()
                .withSubject(data.getSubject())
                .withExpiresAt(data.getExpiresAt())
                .sign(Algorithm.HMAC256(secret));
    }

    public static Payload parserJws(String jws, String secret) {
        return JWT.require(Algorithm.HMAC256(secret)).build()
                .verify(jws);
    }
}
