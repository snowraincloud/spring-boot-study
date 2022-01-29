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
import priv.wjh.permission.api.ao.RoleRequestAo;
import priv.wjh.permission.api.vo.PesVo;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.service.RoleService;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/selectRole")
    public Rsp<String> selectRole(@RequestBody BaseSelectAo ao) throws JsonProcessingException {
        logger.debug("requestData: {}", ao);
        BaseDataAo<RoleRequestAo> baseDataAo = objectMapper.readValue(ao.getData(), new TypeReference<BaseDataAo<RoleRequestAo>>(){});
        RoleRequestAo roleRequestAo = baseDataAo.getBody();
        roleRequestAo.check();
        PageHelper.startPage(baseDataAo.getCurrentPage(), baseDataAo.getPageSize());
        List<Role> roles = roleService.selectRole(roleRequestAo);
        PageInfo<List<Role>> pageInfo = new PageInfo(roles);
        try {
            if ("count".equals(ao.getOp())){
                return Rsp.ok(objectMapper.writeValueAsString(pageInfo.getTotal()));
            }
            return Rsp.ok("响应成功", objectMapper.writeValueAsString(roles));
        } catch (JsonProcessingException e) {
            logger.warn("查询用户列表成功,序列化失败", e);
        }
        return Rsp.error("查询用户列表成功,序列化失败");
    }

    @PostMapping("/setStatus")
    public Rsp<Integer> setStatus(@RequestBody RoleRequestAo roleRequestAo) throws JsonProcessingException {
        if(null == roleRequestAo || null == roleRequestAo.getId()){
            return Rsp.error("信息不完整");
        }
        if(0 != roleRequestAo.getStatus() && 1 != roleRequestAo.getStatus()){
            return Rsp.error("信息错误");
        }
//        if(roleRequestAo.getId().equals(1L) && roleRequestAo.getStatus() == 0){
//            return Rsp.error("超管角色无法禁用");
//        }
        Integer integer = roleService.setStatus(roleRequestAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/getPermission")
    public Rsp<List<PesVo>> getPermissions(@RequestBody RoleRequestAo roleRequestAo) throws JsonProcessingException {
        if(null == roleRequestAo){
            return Rsp.error("信息不完整");
        }
        List<PesVo> permissions = roleService.getPermissions(roleRequestAo);
        return Rsp.ok(permissions);
    }

//    @PostMapping("/setPermission")
//    public Rsp<Integer> setPermissions(@RequestBody RoleRequestAo roleRequestAo) throws JsonProcessingException {
//        if(null == roleRequestAo || null == roleRequestAo.getId()){
//            return Rsp.error("信息不完整");
//        }
//        Integer integer = roleService.setPermissions(roleRequestAo);
//        return Rsp.ok(integer);
//    }

    @PostMapping("/insertRole")
    public Rsp<Integer> insertRole(@RequestBody RoleRequestAo roleRequestAo) throws JsonProcessingException {
        if(null == roleRequestAo || !StringUtils.hasText(roleRequestAo.getName())){
            return Rsp.error("信息不完整");
        }
        if(roleRequestAo.getStatus() == null || (0 != roleRequestAo.getStatus() && 1 != roleRequestAo.getStatus())){
            return Rsp.error("请选择角色状态");
        }

        if (!StringUtils.hasText(roleRequestAo.getName())){
            return Rsp.error("角色名不能为空");
        }
        roleRequestAo.check();
        Integer integer = roleService.insertRole(roleRequestAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/updateRole")
    public Rsp<Integer> updateRole(@RequestBody RoleRequestAo roleRequestAo) throws JsonProcessingException {
        if(null == roleRequestAo){
            return Rsp.error("信息不完整");
        }
        if(0 != roleRequestAo.getStatus() && 1 != roleRequestAo.getStatus()){
            return Rsp.error("信息错误");
        }
        if (!StringUtils.hasText(roleRequestAo.getName())){
            return Rsp.error("角色名不能为空");
        }
        roleRequestAo.check();
        Integer integer = roleService.updateRole(roleRequestAo);
        return Rsp.ok(integer);
    }
}
