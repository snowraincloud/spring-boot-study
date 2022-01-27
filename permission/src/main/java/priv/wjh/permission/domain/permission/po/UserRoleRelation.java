package priv.wjh.permission.domain.permission.po;


public class UserRoleRelation {
    private Long id;

    private Long userId;

    private Long roleId;

    public UserRoleRelation() {
    }


    public UserRoleRelation(Long roleId, Long userId) {
        this.roleId = roleId;
        this.userId =  userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }



    public UserRoleRelation(Long id, Long userId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }


    @Override
    public String toString() {
        return "UserRoleRelation{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}