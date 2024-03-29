package priv.wjh.permission.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author wangjunhao
 */
@Data
@NoArgsConstructor
public class LoginVo {
    private LocalDateTime loginTime;
    private String loginName;
    private Long id;
    private String userName;
    private Object message;
    private boolean needUpdatePassword;
    private String token;
}

