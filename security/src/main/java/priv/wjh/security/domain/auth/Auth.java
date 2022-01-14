package priv.wjh.security.domain.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Auth implements Serializable {
    private String accessToken;
    private String refreshToken;
}
