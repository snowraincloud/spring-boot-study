package priv.wjh.transaction.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import priv.wjh.transaction.domain.po.UserRoleRelationPojo;
import priv.wjh.transaction.domain.service.UserRoleRelationService;

import java.util.List;

/**
 *
 * @author wangjunhao
 **/
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class RelationApi {
    private final UserRoleRelationService userRoleRelationService;

    @DeleteMapping("/{id}")
    public void test(@PathVariable("id")  Long id){
        userRoleRelationService.deleteByUserId(id);
    }

    @GetMapping("/{id}")
    public List<UserRoleRelationPojo> find(@PathVariable("id")  Long id){
        return userRoleRelationService.findByUserId(id);
    }
}
