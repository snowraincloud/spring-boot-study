package priv.wjh.permission.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.wjh.permission.api.ao.BaseAo;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.MenuVo;
import priv.wjh.permission.domain.service.ISystemService;
import priv.wjh.permission.infrastructure.enums.FailRspEnum;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/system"})
public class SystemController {
    private static final Logger log = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ISystemService systemService;

    @PostMapping("/login")
    public Rsp<Object> login(@RequestBody BaseAo ao) throws JsonProcessingException {
        log.info(ao.toString());
        LoginAo loginAo = objectMapper.readValue(ao.getData(), LoginAo.class);
        return Rsp.ok(objectMapper.writeValueAsString(systemService.login(loginAo)));
//        return Rsp.ok(
//                "{\"id\":2,\"loginName\":\"wsecar\",\"userName\":\"超级管理员\",\"token\":\"MjAyMjAxMTkxNDA2Mjc4MTU6MjpAQiMzMiYqQUJhYjpiYWNrZW5kLWFjY291bnRzZXJ2aWNlY3RybGltcGwtOGRmOWRkNjg2LWNsZGQ3OjEw\",\"loginTime\":\"2022-01-19 14:06:27\",\"needUpdatePassword\":false,\"message\":null}");
    }

    @PostMapping("updatePassword")
    public Rsp<Integer> updatePassword(@RequestBody BaseAo ao) throws JsonProcessingException {
        log.info(ao.toString());
        UpdatePasswordAo updatePasswordAo = objectMapper.readValue(ao.getData(), UpdatePasswordAo.class);
        updatePasswordAo.setToken(ao.getToken());
        int res = systemService.updatePassword(updatePasswordAo);
        return Rsp.ok(res);
    }

    @PostMapping("/logout")
    public Rsp<String> logout(@RequestBody BaseAo ao) {
        log.info(ao.toString());
        systemService.logout(ao.getToken());
        return Rsp.ok("");
    }

    @PostMapping("/getValidateCode")
    public Rsp<Object> getValidateCode(@RequestBody BaseAo ao) throws JsonProcessingException {
        log.info(ao.toString());
        Optional<com.wanshun.console.permission.vo.ValidateCodeVo> vo = this.systemService.getValidateCode();
        if (vo.isPresent()) {
            return Rsp.ok(objectMapper.writeValueAsString(vo.get()));
        }
        return Rsp.fail(FailRspEnum.ZERO);
    }

    @PostMapping("/getModuleMenu")
    public Rsp<String> getModuleMenu(@RequestBody BaseAo ao) throws JsonProcessingException {
        List<MenuVo> menuVos = systemService.getModuleMenu(ao.getToken());
        return Rsp.ok(objectMapper.writeValueAsString(menuVos));
    }
}
