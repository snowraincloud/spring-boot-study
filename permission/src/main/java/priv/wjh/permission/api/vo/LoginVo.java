package priv.wjh.permission.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LoginVo {
    private Date loginTime;
    private String loginName;
    private Long id;
    private String userName;
    private Object message;
    private boolean needUpdatePassword;
    private String token;
}

