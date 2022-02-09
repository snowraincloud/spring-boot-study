package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

@Mapper
public interface RoleMapper{

    List<RolePojo> findByUser(UserPojo user);

    List<RolePojo> find(RolePojo rolePojo);

    int add(RolePojo rolePojo);

    int update(RolePojo rolePojo);
}