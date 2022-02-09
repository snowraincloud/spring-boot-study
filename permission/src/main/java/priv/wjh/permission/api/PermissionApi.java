package priv.wjh.permission.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import priv.wjh.permission.api.ao.PermissionAo;
import priv.wjh.permission.application.PermissionApplicationService;
import priv.wjh.permission.domain.permission.po.PermissionPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
@Slf4j
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionApi {
    private final PermissionApplicationService permissionApplicationService;

    @GetMapping
    public List<PermissionPojo> find(PermissionAo ao){
        return permissionApplicationService.findPermission(ao);
    }

    @PostMapping
    public PermissionPojo add(@RequestBody PermissionAo ao){
        return permissionApplicationService.addPermission(ao);
    }

    @PutMapping
    public PermissionPojo update(@RequestBody PermissionAo ao){
        return permissionApplicationService.updatePermission(ao);
    }

}
