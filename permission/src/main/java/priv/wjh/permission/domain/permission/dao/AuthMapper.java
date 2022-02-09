package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.domain.permission.po.UserPojo;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Mapper
public interface AuthMapper {
    UserPojo findUser(LoginAo ao);
}
