package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserPojo> find(UserPojo userPojo);

    int add(UserPojo user);

    int update(UserPojo ao);

}