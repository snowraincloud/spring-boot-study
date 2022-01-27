package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.UserRequestAo;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.UserRoleRelation;

import java.util.List;

@Mapper
public interface UserRoleRelationMapper {
    int deleteByUserId(Long id);

    int insertSelective(UserRequestAo userRequestAo);

    List<Role> selectByUserId(Long id);

    int deleteByRoleId(Long id);

    List<Permission> selectPermissionByUserId(Long id);

    int insertList(List<UserRoleRelation> list);

    List<Long> selectUserIdByRoleId(Long roleId);
}