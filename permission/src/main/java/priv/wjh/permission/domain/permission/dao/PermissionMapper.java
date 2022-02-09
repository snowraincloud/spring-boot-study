package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;

import java.util.List;


/**
 * @author wangjunhao
 */
@Mapper
public interface PermissionMapper{

    List<PermissionPojo> findByRole(RolePojo rolePojo);
    List<PermissionPojo> find(PermissionPojo permissionPojo);
    int add(PermissionPojo permissionPojo);
    int update(PermissionPojo permissionPojo);



}