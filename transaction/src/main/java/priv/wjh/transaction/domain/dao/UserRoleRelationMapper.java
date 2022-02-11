package priv.wjh.transaction.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.transaction.domain.po.UserRoleRelationPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
@Mapper
public interface UserRoleRelationMapper {

    List<UserRoleRelationPojo> find(Long id);

    int add(List<UserRoleRelationPojo> relations);

    int deleteByUserId(Long id);

}