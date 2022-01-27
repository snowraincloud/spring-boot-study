package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.UserRequestAo;
import priv.wjh.permission.domain.permission.po.User;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAll(UserRequestAo userRequestAo);

    int updateStatus(UserRequestAo userRequestAo);

    User selectByUsername(String username);

    User selectById(Long id);

    int updateLoginTimeByLogin(User user);

    int insertUser(UserRequestAo userRequestAo);

    int updatePassword(User user);

    int update(UserRequestAo ao);
}