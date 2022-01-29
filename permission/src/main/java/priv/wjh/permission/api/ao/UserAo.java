package priv.wjh.permission.api.ao;


import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * @author wangjunhao
 */
@Data
public class UserAo {
    @Positive(message = "用户ID必须大于零")
    private Long id;

    private String username;

    private String password;

    @Past(message = "创建时间不能大于当前时间")
    private Date createTime;
    @Past(message = "登陆时间不能大于当前时间")
    private Date loginTime;
    private Boolean status;
}
