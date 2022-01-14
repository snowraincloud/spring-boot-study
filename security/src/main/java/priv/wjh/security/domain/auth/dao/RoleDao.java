package priv.wjh.security.domain.auth.dao;


import priv.wjh.security.domain.auth.Role;

import java.util.List;

/**
* //TODO
*
* @author wangjunhao
**/
public interface RoleDao {
    List<Role> findAll();
}