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
import priv.wjh.permission.api.ao.UserAo;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;
import priv.wjh.permission.domain.permission.service.UserService;
import priv.wjh.permission.infrastructure.annotation.MyUnpack;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjunhao
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public List<User> findUser(@MyUnpack @Valid BaseDataAo<UserAo> userAo){

        return null;
    }


    @PostMapping("/selectUser")
    public Rsp<String> selectUser(@RequestBody BaseSelectAo ao) throws JsonProcessingException {
        logger.debug("requestData: {}", ao);
        BaseDataAo<UserAo> baseDataAo = objectMapper.readValue(ao.getData(), new TypeReference<>() {
        });
        UserAo body = baseDataAo.getBody();
        PageHelper.startPage(baseDataAo.getCurrentPage(),baseDataAo.getPageSize());
        PageInfo<List<User>> pageInfo = new PageInfo(userService.selectUser(body));
        try {
            if ("count".equals(ao.getOp())){
                return Rsp.ok(objectMapper.writeValueAsString(pageInfo.getTotal()));
            }
            return Rsp.ok( objectMapper.writeValueAsString(pageInfo.getList()));
        } catch (JsonProcessingException e) {
            logger.warn("查询用户列表成功,序列化失败", e);
        }
        return Rsp.error("查询用户列表成功,序列化失败");
    }

    @PostMapping("/setStatus")
    public Rsp<Integer> setStatus(@RequestBody UserAo userAo) throws JsonProcessingException {
        if(null == userAo || null == userAo.getId() || null == userAo.getStatus()){
            return Rsp.error("信息不完整");
        }
        if(userAo.getStatus() != 0 && userAo.getStatus() != 1){
            return Rsp.error("信息错误");
        }
        if(userAo.getId().equals(1L) && userAo.getStatus() == 0){
            return Rsp.error("超管用户无法停用");
        }
        Integer integer = userService.setStatus(userAo);
        return Rsp.ok(integer);
    }

    @PostMapping("/getRole")
    public Rsp<List<Role>> getRole(@RequestBody UserAo userAo) throws JsonProcessingException {
        List<Role> roles = userService.getRoles(userAo);
        return Rsp.ok(roles);
    }

    @PostMapping("/insertUser")
    public Rsp<Integer> insertUser(@RequestBody UserAo ao){
        if (!StringUtils.hasText(ao.getUsername()) || !StringUtils.hasText(ao.getPassword())){
            return Rsp.error("用户名或密码不能为空");
        }
        int res = userService.insertUser(ao);
        return Rsp.ok(res);
    }

    @PostMapping("/updateUser")
    public Rsp<Integer> updateUser(@RequestBody UserAo ao){
        if(ao.getId().equals(1L)){
            return Rsp.error("超管用户无法修改");
        }
        if (!StringUtils.hasText(ao.getUsername())){
            return Rsp.error("用户名不能为空");
        }
        int res = userService.update(ao);
        return Rsp.ok(res);
    }
}
