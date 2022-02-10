package priv.wjh.permission.api.ao;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wangjunhao
 */
@Data
public class PermissionAo {

    private Long id;

    private Long pid;

    private String name;

    private String value;

    private String uri;

    private Byte type;

    private LocalDateTime createTime;

    private Boolean status;

}
