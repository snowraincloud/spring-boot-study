package priv.wjh.permission.domain.permission.po;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author wangjunhao
 */
@Data
public class PermissionPojo {
    private Long id;

    private Long pid;

    private String name;

    private String value;

    private Byte type;

    private String uri;

    private Boolean status;

    private LocalDateTime createTime;



}