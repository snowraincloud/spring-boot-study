package priv.wjh.permission.domain.service;

public interface ILogoutUserService {
    void logoutUserByPermissionId(Long id);

    void logoutByRoleId(Long id);

    void logoutByUserId(Long id);
}
