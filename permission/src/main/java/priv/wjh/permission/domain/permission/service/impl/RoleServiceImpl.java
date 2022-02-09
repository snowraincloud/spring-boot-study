package priv.wjh.permission.domain.permission.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import priv.wjh.permission.domain.permission.dao.RoleMapper;
import priv.wjh.permission.domain.permission.dao.RolePermissionRelationMapper;
import priv.wjh.permission.domain.permission.po.RolePermissionRelationPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.po.UserPojo;
import priv.wjh.permission.domain.permission.service.IRoleService;
import priv.wjh.permission.infrastructure.enums.rsp.FailRspEnum;
import priv.wjh.permission.infrastructure.enums.rsp.RelationRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjunhao
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleMapper roleMapper;
    private final RolePermissionRelationMapper rolePermissionRelationMapper;


    @Override
    public List<RolePojo> findByUser(UserPojo user) {
        return roleMapper.findByUser(user);
    }

    @Override
    public List<RolePojo> find(RolePojo rolePojo) {
        return roleMapper.find(rolePojo);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public RolePojo add(RolePojo role) {
        roleMapper.add(role);
        if (!CollectionUtils.isEmpty(role.getPermissionIds()) && role.getId() != null) {
            addRelation(role.getPermissionIds()
                                .stream()
                                .map(id -> new RolePermissionRelationPojo(role.getId(), id))
                                .collect(Collectors.toList()));
        }
        return role;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int update(RolePojo role) {
        int res = roleMapper.update(role);
        if (res != 1) {
            throw new PermissionException(FailRspEnum.UPDATE_FAIL);
        }
        if (!CollectionUtils.isEmpty(role.getPermissionIds())) {
            updateRelation(role.getPermissionIds()
                                .stream()
                                .map(id -> new RolePermissionRelationPojo(role.getId(), id))
                                .collect(Collectors.toList()));
        }
        return res;
    }


    private void addRelation(List<RolePermissionRelationPojo> relations) {
        int res = rolePermissionRelationMapper.add(relations);
        if (res != relations.size()) {
            throw new PermissionException(RelationRspEnum.ADD_RELATION_FAIL);
        }
    }

    private void updateRelation(List<RolePermissionRelationPojo> relations) {
        rolePermissionRelationMapper.deleteByRoleId(relations.get(0)
                                                            .getRoleId());
        int res = rolePermissionRelationMapper.add(relations);
        if (res != relations.size()) {
            throw new PermissionException(RelationRspEnum.ADD_RELATION_FAIL);
        }
    }
}
