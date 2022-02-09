package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

/**
 * <h1>用户服务接口定义</h1>
 * @author wangjunhao
 */
public interface IUserService {


    List<UserPojo> find(UserPojo user);

    UserPojo add(UserPojo user);

    int update(UserPojo ao);

}
