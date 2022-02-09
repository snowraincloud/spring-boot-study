package priv.wjh.permission.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import priv.wjh.permission.api.ao.RoleAo;
import priv.wjh.permission.application.PermissionApplicationService;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;

import java.util.List;

/**
 * @author wangjunhao
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleApi {

    private final PermissionApplicationService permissionApplicationService;


    @GetMapping
    public List<RolePojo> find(RoleAo ao){
        return permissionApplicationService.findRole(ao);
    }

    @GetMapping("/permission")
    public List<PermissionPojo> findPermissionById(RoleAo ao){
        return permissionApplicationService.findPermissionByRole(ao);
    }

    @PostMapping
    public RolePojo add(@RequestBody RoleAo ao){
        return permissionApplicationService.addRole(ao);
    }

    @PutMapping
    public int update(@RequestBody RoleAo ao){
        return permissionApplicationService.updateRole(ao);
    }
}
