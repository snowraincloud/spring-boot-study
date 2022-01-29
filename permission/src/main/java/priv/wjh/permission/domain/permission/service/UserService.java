package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.UserAo;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;

import java.util.List;

public interface UserService {

//    UserSecurity login(UserSecurity user) throws JsonProcessingException;

    List<User> selectUser(UserAo userAo) ;

    Integer setStatus(UserAo userAo);

    List<Role> getRoles(UserAo userAo);

//    Integer setRoles(UserRequestAo userRequestAo);

    Integer insertUser(UserAo userAo);

    int update(UserAo ao);
}
