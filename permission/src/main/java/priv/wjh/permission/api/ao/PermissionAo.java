package priv.wjh.permission.api.ao;

import java.util.Date;

public class PermissionAo {

    private Long id;

    private Long pid;

    private String name;

    private String value;

    private String uri;

    private Byte type;

    private Date createTime;

    private Byte status;

    public void check(){
//        if(null != name && !Pattern.matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$",name)){
//            throw new com.wanshun.console.permission.exception.PermissionException(com.wanshun.console.permission.enums.PatternRspEnum.PATTERN);
//        }
//        if(null != value && !Pattern.matches("^[/*a-zA-Z0-9]+$",value)){
//            throw new com.wanshun.console.permission.exception.PermissionException(com.wanshun.console.permission.enums.PatternRspEnum.PATTERN);
//        }
//        if(null != uri && !Pattern.matches("^[/*a-zA-Z0-9]+$",uri)){
//            throw new com.wanshun.console.permission.exception.PermissionException(com.wanshun.console.permission.enums.PatternRspEnum.PATTERN);
//        }
    }

    public PermissionAo() {
    }

    public PermissionAo(Long id, Long pid, String name, String value, String uri, Byte type, Date createTime, Byte status) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.value = value;
        this.uri = uri;
        this.type = type;
        this.createTime = createTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "PermissionRequestAo{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", uri='" + uri + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}
