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
import priv.wjh.permission.api.ao.UserRequestAo;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;
import priv.wjh.permission.domain.service.UserService;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/selectUser")
    public Rsp<String> selectUser(@RequestBody BaseSelectAo ao) throws JsonProcessingException {
        log.debug("requestData: {}", ao);
        BaseDataAo<UserRequestAo> baseDataAo = objectMapper.readValue(ao.getData(), new TypeReference<BaseDataAo<UserRequestAo>>(){});
        UserRequestAo body = baseDataAo.getBody();
        body.check();
        PageHelper.startPage(baseDataAo.getCurrentPage(),baseDataAo.getPageSize());
        PageInfo<List<User>> pageInfo = new PageInfo(userService.selectUser(body));
        try {
            if ("count".equals(ao.getOp())){
                return Rsp.ok(objectMapper.writeValueAsString(pageInfo.getTotal()));
            }
            return Rsp.ok( objectMapper.writeValueAsString(pageInfo.getList()));
        } catch (JsonProcessingException e) {
            log.warn("查询用户列表成功,序列化失败", e);
        }
        return Rsp.error("查询用户列表成功,序列化失败");
    }

    @PostMapping("/setStatus")
    public Rsp<Integer> setStatus(@RequestBody UserRequestAo userRequestAo) throws JsonProcessingException {
        if(null == userRequestAo || null == userRequestAo.getId() || null == userRequestAo.getStatus()){
            return Rsp.error("信息不完整");
        }
        if(userRequestAo.getStatus() != 0 && userRequestAo.getStatus() != 1){
            return Rsp.error("信息错误");
        }
        if(userRequestAo.getId().equals(1L) && userRequestAo.getStatus() == 0){
            return Rsp.error("超管用户无法停用");
        }
        Integer integer = userService.setStatus(userRequestAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/getRole")
    public Rsp<List<Role>> getRole(@RequestBody UserRequestAo userRequestAo) throws JsonProcessingException {
        List<Role> roles = userService.getRoles(userRequestAo);
        return Rsp.ok(roles);
    }

//    @PostMapping("/setRole")
//    public Rsp<Integer> setRole(@RequestBody UserRequestAo userRequestAo) throws JsonProcessingException {
//        if(null == userRequestAo || null == userRequestAo.getId() || null == userRequestAo.getRoleId()){
//            return Rsp.error("信息不完整");
//        }
//        Integer integer = userService.setRoles(userRequestAo);
//        return Rsp.ok(integer);
//    }

    @PostMapping("/insertUser")
    public Rsp<Integer> insertUser(@RequestBody UserRequestAo ao){
        if (!StringUtils.hasText(ao.getUsername()) || !StringUtils.hasText(ao.getPassword())){
            return Rsp.error("用户名或密码不能为空");
        }
        ao.check();
        int res = userService.insertUser(ao);
        return Rsp.ok(res);
    }

    @PostMapping("/updateUser")
    public Rsp<Integer> updateUser(@RequestBody UserRequestAo ao){
        if(ao.getId().equals(1L)){
            return Rsp.error("超管用户无法修改");
        }
        if (!StringUtils.hasText(ao.getUsername())){
            return Rsp.error("用户名不能为空");
        }
        ao.check();
        int res = userService.update(ao);
        return Rsp.ok(res);
    }
}
