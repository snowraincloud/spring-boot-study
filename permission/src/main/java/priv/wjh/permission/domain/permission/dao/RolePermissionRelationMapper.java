package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.domain.permission.po.RolePermissionRelationPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
@Mapper
public interface RolePermissionRelationMapper{

    int add(List<RolePermissionRelationPojo> relations);

    int deleteByRoleId(Long id);

}