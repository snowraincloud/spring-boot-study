package priv.wjh.permission.domain.service;

import priv.wjh.permission.domain.permission.po.Permission;

import java.util.List;

public interface IAuthService {


    boolean checkCache(String token);

    Long getId(String token);

    List<Permission> getPermission(Long id);
    List<Permission> getPermission(String token);
}
