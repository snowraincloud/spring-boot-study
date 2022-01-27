package priv.wjh.permission.domain.permission.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    private Integer status;

    public User() {
    }


    public User(Long id, String username, String password, Date createTime, Date loginTime, Integer status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                ", status=" + status +
                '}';
    }
}