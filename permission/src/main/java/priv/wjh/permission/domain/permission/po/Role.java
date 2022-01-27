package priv.wjh.permission.domain.permission.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Role {
    private Long id;

    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Byte status;
//
//    private Integer have;

    public Role() {
    }

    public Role(Long id, String name, String description, Date createTime, Byte status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createTime = createTime;
        this.status = status;
//        this.have = have;
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

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

//    public Integer getHave() {
//        return have;
//    }
//
//    public void setHave(Integer have) {
//        this.have = have;
//    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                '}';
    }
}