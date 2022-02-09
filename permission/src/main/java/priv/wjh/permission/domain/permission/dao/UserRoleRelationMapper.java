package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.domain.permission.po.UserRoleRelationPojo;

import java.util.List;

@Mapper
public interface UserRoleRelationMapper {

    int add(List<UserRoleRelationPojo> relations);

    int deleteByUserId(Long id);

}