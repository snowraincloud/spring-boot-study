package priv.wjh.security.domain.auth.dao;

import priv.wjh.security.domain.auth.User;

import java.util.Optional;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public interface UserDao {
    Optional<User> findByMobile(String mobile);

    Optional<User> findRolesByMobile(String mobile);
}
