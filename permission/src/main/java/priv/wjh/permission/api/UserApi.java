package priv.wjh.permission.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import priv.wjh.permission.api.ao.UserAo;
import priv.wjh.permission.application.PermissionApplicationService;
import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserApi {

    private final PermissionApplicationService permissionApplicationService;

    @GetMapping
    public List<UserPojo> find(UserAo ao){
        return permissionApplicationService.findUser(ao);
    }

    @GetMapping("/role")
    public List<RolePojo> findRolesById(UserAo ao){
        return permissionApplicationService.findRoleByUser(ao);
    }

    @PostMapping
    public UserPojo add(@RequestBody UserAo ao){
        return permissionApplicationService.addUser(ao);
    }

    @PutMapping
    public Integer update(@RequestBody UserAo ao){
        return permissionApplicationService.updateUser(ao);
    }

}
