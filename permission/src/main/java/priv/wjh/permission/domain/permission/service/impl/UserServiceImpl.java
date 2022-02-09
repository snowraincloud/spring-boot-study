package priv.wjh.permission.domain.permission.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import priv.wjh.permission.domain.permission.dao.UserMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.permission.po.UserPojo;
import priv.wjh.permission.domain.permission.po.UserRoleRelationPojo;
import priv.wjh.permission.domain.permission.service.IUserService;
import priv.wjh.permission.infrastructure.enums.rsp.FailRspEnum;
import priv.wjh.permission.infrastructure.enums.rsp.RelationRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjunhao
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final UserRoleRelationMapper userRoleRelationMapper;

    public UserServiceImpl(UserMapper userMapper, UserRoleRelationMapper userRoleRelationMapper) {
        this.userMapper = userMapper;
        this.userRoleRelationMapper = userRoleRelationMapper;
    }

    @Override
    public List<UserPojo> find(UserPojo user) {
        return userMapper.find(user);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserPojo add(UserPojo user) {
        userMapper.add(user);
        if (!CollectionUtils.isEmpty(user.getRoleIds()) && user.getId() != null) {
            addRelation(user.getRoleIds()
                        .stream()
                        .map(id -> new UserRoleRelationPojo(user.getId(), id))
                        .collect(Collectors.toList()));
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int update(UserPojo user) {
        int res = userMapper.update(user);
        if (res != 1){
            throw new PermissionException(FailRspEnum.UPDATE_FAIL);
        }
        if (!CollectionUtils.isEmpty(user.getRoleIds())) {
            updateRelation(user.getRoleIds()
                                .stream()
                                .map(id -> new UserRoleRelationPojo(user.getId(), id))
                                .collect(Collectors.toList()));
        }
        return res;
    }

    private void addRelation(List<UserRoleRelationPojo> relations) {
        int res = userRoleRelationMapper.add(relations);
        if (res != relations.size()) {
            throw new PermissionException(RelationRspEnum.ADD_RELATION_FAIL);
        }
    }

    private void updateRelation(List<UserRoleRelationPojo> relations) {
        userRoleRelationMapper.deleteByUserId(relations.get(0)
                                                      .getUserId());
        int res = userRoleRelationMapper.add(relations);
        if (res != relations.size()) {
            throw new PermissionException(RelationRspEnum.UPDATE_RELATION_FAIL);
        }
    }
}
