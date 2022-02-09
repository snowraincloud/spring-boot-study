package priv.wjh.permission.domain.permission.po;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserPojo {

    private Long id;

    private String username;

    private String password;

//    private String icon;
//
//    private String email;
//
//    private String nickName;
//
//    private String note;

    private LocalDateTime createTime;
    private LocalDateTime loginTime;

    private Boolean status;

    private List<Long> roleIds;

}