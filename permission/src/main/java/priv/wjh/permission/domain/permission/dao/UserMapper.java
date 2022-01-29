package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.UserAo;
import priv.wjh.permission.domain.permission.po.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAll(UserAo userAo);

    int updateStatus(UserAo userAo);

    User selectByUsername(String username);

    User selectById(Long id);

    int updateLoginTimeByLogin(User user);

    int insertUser(UserAo userAo);

    int updatePassword(User user);

    int update(UserAo ao);
}