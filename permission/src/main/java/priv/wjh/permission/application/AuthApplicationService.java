package priv.wjh.permission.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.MenuVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.service.IAuthService;

import java.util.*;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
@RequiredArgsConstructor
public class AuthApplicationService {

    private final IAuthService authService;


    public LoginVo login(LoginAo ao) {
        return authService.login(ao);
    }

    public void logout() {
        authService.logout();
    }

    public Integer updatePassword(UpdatePasswordAo ao) {
        return authService.updatePassword(ao);
    }

    public ValidateCodeVo getValidateCode() {
        return authService.getValidateCode();
    }

    public List<MenuVo> getMenu() {
        // 根据pid进行排序
        List<PermissionPojo> permissions = authService.getTreePermission();
        permissions.sort(Comparator.comparingLong(PermissionPojo::getPid));
        // 转换为树形结构
        List<MenuVo> res = new ArrayList<>();
        Map<Long, MenuVo> map = new HashMap<>(permissions.size() / 4);

        permissions.forEach(permission -> {
            if (permission.getPid().equals(0L)){
                MenuVo menu = MenuVo.fromPermission(permission);
                res.add(menu);
                map.put(permission.getId(), menu);
            }else {
                map.get(permission.getPid()).getChildren().add(MenuVo.fromPermission(permission));
            }
        });

        return res;
    }
}
