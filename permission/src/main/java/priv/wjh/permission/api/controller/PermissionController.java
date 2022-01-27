package priv.wjh.permission.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.wjh.permission.api.ao.BaseDataAo;
import priv.wjh.permission.api.ao.BaseSelectAo;
import priv.wjh.permission.api.ao.PermissionRequestAo;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.service.PermissionService;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/selectPermission")
    public Rsp<String> selectPermission(@RequestBody BaseSelectAo ao) throws JsonProcessingException {
        log.debug("requestData: {}", ao);
        BaseDataAo<PermissionRequestAo> baseDataAo = objectMapper.readValue(ao.getData(), new TypeReference<BaseDataAo<PermissionRequestAo>>(){});
        PermissionRequestAo permissionRequestAo = baseDataAo.getBody();
        permissionRequestAo.check();
        PageHelper.startPage(baseDataAo.getCurrentPage(), baseDataAo.getPageSize());
        List<Permission> permissions = permissionService.selectPermission(permissionRequestAo);
        PageInfo<List<Role>> pageInfo = new PageInfo(permissions);
        try {
            if ("count".equals(ao.getOp())){
                return Rsp.ok(objectMapper.writeValueAsString(pageInfo.getTotal()));
            }
            return Rsp.ok(objectMapper.writeValueAsString(permissions));
        } catch (JsonProcessingException e) {
            log.warn("查询用户列表成功,序列化失败", e);
        }
        return Rsp.error("查询用户列表成功,序列化失败");
    }

    @PostMapping("/setStatus")
    public Rsp<Integer> setStatus(@RequestBody PermissionRequestAo permissionRequestAo) throws JsonProcessingException {
        if(null == permissionRequestAo || null == permissionRequestAo.getId()){
            return Rsp.error("信息不完整");
        }
        if(0 != permissionRequestAo.getStatus() && 1 != permissionRequestAo.getStatus()){
            return Rsp.error("信息错误");
        }
//        if(permissionRequestAo.getId().equals(1L) && permissionRequestAo.getStatus() == 0){
//            return Rsp.error("超管权限无法禁用");
//        }
        Integer integer = permissionService.setStatus(permissionRequestAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/insertPermission")
    public Rsp<Integer> insertPermission(@RequestBody PermissionRequestAo permissionRequestAo) throws JsonProcessingException {
        if(null == permissionRequestAo){
            return Rsp.error("信息不完整");
        }
        if(0 != permissionRequestAo.getType() && 1 != permissionRequestAo.getType()){
            return Rsp.error("信息错误");
        }
        if(permissionRequestAo.getPid() != null && permissionRequestAo.getPid() == 0 && permissionRequestAo.getType() == 1){
            return Rsp.error("信息错误");
        }

        if (!StringUtils.hasText(permissionRequestAo.getName())){
            return Rsp.error("权限名不能为空");
        }
        permissionRequestAo.check();
//        if(null == permissionRequestAo.getPid()){
//            return Rsp.error("信息错误");
//        }
//        if(1 == permissionRequestAo.getType() && 0 != permissionRequestAo.getPid()){
//            return Rsp.error("信息错误");
//        }
//        if(2 == permissionRequestAo.getType() && 0 == permissionRequestAo.getPid()){
//            return Rsp.error("信息错误");
//        }
        Integer integer = permissionService.insertPermission(permissionRequestAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/getPermission")
    public Rsp<List<Permission>> getPermissions(){
        List<Permission> permissions = permissionService.getPermissions();
        return Rsp.ok(permissions);
    }

    @PostMapping("/updatePermission")
    public Rsp<Integer> updatePermission(@RequestBody PermissionRequestAo permissionRequestAo){
        if(null == permissionRequestAo || null == permissionRequestAo.getId()){
            return Rsp.error("信息不完整");
        }
        if(permissionRequestAo.getPid() != null && permissionRequestAo.getType() != null && permissionRequestAo.getPid() == 0 && permissionRequestAo.getType() == 1){
            return Rsp.error("信息错误");
        }
        if (!StringUtils.hasText(permissionRequestAo.getName()) ){
            return Rsp.error("权限名不能为空");
        }
        if(permissionRequestAo.getPid() != null && permissionRequestAo.getPid() == permissionRequestAo.getId()){
            return Rsp.error("父级权限不能是本身");
        }
        permissionRequestAo.check();
        Integer i = permissionService.updatePermission(permissionRequestAo);
        return Rsp.ok(i);
    }

}
