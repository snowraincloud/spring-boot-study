package priv.wjh.permission.domain.service;

import priv.wjh.permission.api.ao.UserRequestAo;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;

import java.util.List;

public interface UserService {

//    UserSecurity login(UserSecurity user) throws JsonProcessingException;

    List<User> selectUser(UserRequestAo userRequestAo) ;

    Integer setStatus(UserRequestAo userRequestAo);

    List<Role> getRoles(UserRequestAo userRequestAo);

//    Integer setRoles(UserRequestAo userRequestAo);

    Integer insertUser(UserRequestAo userRequestAo);

    int update(UserRequestAo ao);
}
