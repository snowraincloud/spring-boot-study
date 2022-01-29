package priv.wjh.permission.api.ao;


import java.util.Date;
import java.util.List;

public class UserAo {

    private Long id;

    private String username;


    private String password;

    private String confirmPassword;

    private Date createTime;

    private Date loginTime;

    private List<Long> roleId;

    private Byte status;


    public UserAo() {
    }

    public UserAo(Long id, String username, String password, String confirmPassword, Date createTime, Date loginTime, List<Long> roleId, Byte status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.roleId = roleId;
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserRequestAo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                ", roleId=" + roleId +
                ", status=" + status +
                '}';
    }
}
