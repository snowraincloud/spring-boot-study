package priv.wjh.transaction.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import priv.wjh.transaction.domain.dao.UserRoleRelationMapper;
import priv.wjh.transaction.domain.po.UserRoleRelationPojo;

import java.util.List;

/**
 *
 * @author wangjunhao
 **/
@Service
@RequiredArgsConstructor
public class UserRoleRelationService {
    private final UserRoleRelationMapper userRoleRelationMapper;

//    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserId(Long id){
        userRoleRelationMapper.deleteByUserId(id);
        throw new RuntimeException();
    }


    public List<UserRoleRelationPojo> findByUserId(Long id){
        return userRoleRelationMapper.find(id);
    }
}
