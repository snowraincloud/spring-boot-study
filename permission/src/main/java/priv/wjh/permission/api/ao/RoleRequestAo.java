package priv.wjh.permission.api.ao;

import java.util.Date;
import java.util.List;

public class RoleRequestAo {

    private Long id;

    private String name;

    private String description;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Byte status;

    private List<Long> permissionId;

    public void check(){
//        if(null != name && !Pattern.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$",name)){
//            throw new com.wanshun.console.permission.exception.PermissionException(com.wanshun.console.permission.enums.PatternRspEnum.PATTERN);
//        }
    }

    public RoleRequestAo() {
    }

    public RoleRequestAo(Long id, String name, String description, Date createTime, Byte status, List<Long> permissionId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createTime = createTime;
        this.status = status;
        this.permissionId = permissionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public List<Long> getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(List<Long> permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "RoleRequestAo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", permissionId=" + permissionId +
                '}';
    }
}
