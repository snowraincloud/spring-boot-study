package priv.wjh.permission.domain.permission.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Permission {
    private Long id;

    private Long pid;

    private String name;

    private String value;

    private Byte type;

    private String uri;

    private Byte status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<Permission> list = new ArrayList<>();

    public void add(Permission permission){
        list.add(permission);
    }

    public Permission() {
    }

    public Permission(Long id, Long pid, String name, String value, Byte type, String uri, Byte status, Date createTime, List<Permission> list) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.value = value;
        this.type = type;
        this.uri = uri;
        this.status = status;
        this.createTime = createTime;
        this.list = list;
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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Date createTime) {
        this.createTime = createTime;
    }

    public List<Permission> getList() {
        return list;
    }

    public void setList(List<Permission> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", type=" + type +
                ", uri='" + uri + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", list=" + list +
                '}';
    }
}