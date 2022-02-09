package priv.wjh.permission.api.ao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjunhao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAo {
    private String keyCode;
    private String password;
    private String authCode;
    private String systemName;
    private String loginName;

}
