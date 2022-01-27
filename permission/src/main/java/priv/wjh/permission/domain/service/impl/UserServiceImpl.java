package priv.wjh.permission.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import priv.wjh.permission.api.ao.UserRequestAo;
import priv.wjh.permission.domain.permission.dao.RoleMapper;
import priv.wjh.permission.domain.permission.dao.UserMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;
import priv.wjh.permission.domain.permission.po.UserRoleRelation;
import priv.wjh.permission.domain.service.ILogoutUserService;
import priv.wjh.permission.domain.service.UserService;
import priv.wjh.permission.infrastructure.jwt.JwtToken;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ILogoutUserService logoutUserService;
//    @Override
//    public UserSecurity login(UserSecurity user) throws JsonProcessingException {
//        User u = userMapper.selectByUsername(user.getUsername());
//        if(u == null){
//            return null;
//        }
//        if(user.getPassword().equals(u.getPassword())){
//            u.setLoginTime(new Date());
//            int i = userMapper.updateLoginTimeByLogin(u);
//            user.setId(u.getId());
//            List<Permission> permissions = userRoleRelationMapper.selectPermissionByUserId(u.getId());
//            List<String> values = PermissionUtil.getValue(permissions);
//            servletContext.setAttribute(user.getId() + user.getUsername(),values);
//            user.setPesVos(PermissionUtil.getPesVo(permissions));
//        }else {
//            return null;
//        }
//        String token = jwtToken.createToken(user.getId() + user.getUsername());
//        user.setToken(token);
//        return user;
//    }

    @Override
    public List<User> selectUser(UserRequestAo userRequestAo) {
        return userMapper.selectAll(userRequestAo);
    }

    @Override
    public Integer setStatus(UserRequestAo userRequestAo) {
        int j = userMapper.updateStatus(userRequestAo);
        if (j != 0 && userRequestAo.getStatus() == 0){
            logoutUserService.logoutByUserId(userRequestAo.getId());
        }
        return j;
    }



    @Override
    public List<Role> getRoles(UserRequestAo userRequestAo) {
        if (userRequestAo.getId() == null){
            return roleMapper.selectAll();
        }
        List<Role> roles = userRoleRelationMapper.selectByUserId(userRequestAo.getId());
        return roles;
    }

//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public Integer setRoles(UserRequestAo userRequestAo) {
//        userRoleRelationMapper.deleteByUserId(userRequestAo.getId());
//        int j = userRoleRelationMapper.insertSelective(userRequestAo);
//        if(j != userRequestAo.getRoleId().size()){
//            throw new RuntimeException();
//        }
//        return j;
//    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Integer insertUser(UserRequestAo userRequestAo) {
//        MD5Encoder.encode()
//        userRequestAo.setPassword(passwordEncoder.encode(userRequestAo.getPassword()));
        userRequestAo.setPassword(DigestUtils.md5DigestAsHex(userRequestAo.getPassword().getBytes(StandardCharsets.UTF_8)));
        int i = userMapper.insertUser(userRequestAo);
        if(0 == i){
            throw new RuntimeException("插入用户失败");
        }
        List<UserRoleRelation> userRoleRelations = new ArrayList<>(userRequestAo.getRoleId().size());
        for(Long id : userRequestAo.getRoleId()){
            userRoleRelations.add(new UserRoleRelation(id, userRequestAo.getId()));
        }
        i = userRoleRelationMapper.insertList(userRoleRelations);
        if(i != userRequestAo.getRoleId().size()){
            throw new RuntimeException("插入用户角色失败");
        }

        return i;
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int update(UserRequestAo ao) {
        if (ao.getId().equals(1L)){
            ao.setStatus((byte) 1);
        }
        int i = userMapper.update(ao);
        if (0 == i){
            throw new RuntimeException("更新用户失败");
        }
        userRoleRelationMapper.deleteByUserId(ao.getId());
        if (ao.getRoleId() != null && ao.getRoleId().size() != 0){
            List<UserRoleRelation> userRoleRelations = new ArrayList<>(ao.getRoleId().size());
            for(Long id : ao.getRoleId()){
                userRoleRelations.add(new UserRoleRelation(id, ao.getId()));
            }
            i = userRoleRelationMapper.insertList(userRoleRelations);
            if(i != ao.getRoleId().size()){
                throw new RuntimeException("插入用户角色失败");
            }
        }
        if (ao.getStatus() == 0){
            logoutUserService.logoutByUserId(ao.getId());
        }
        return i;
    }
}
