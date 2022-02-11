package com.example.web.validation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/user")     // 通过这里配置使下面的映射都在/users下
public class UserController {

    // 创建线程安全的Map，模拟users信息的存储
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    /**
     * 处理"/user/"的GET请求，用来获取用户列表
     *
     * @return
     */
    @ApiOperation("用户列表")
    @GetMapping
    public List<User> users() {
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }

    /**
     * 处理"/user/"的POST请求，用来创建User
     *
     * @param user
     * @return
     */
    @ApiOperation("添加用户")
    @PostMapping
    public String addUser(@Validated(ValidUserGroup.Add.class) @RequestBody User user) {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        users.put(user.getId(), user);
        return "success";
    }

    /**
     * 处理"/user/{id}"的GET请求，用来获取url中id值的User信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public User user(@PathVariable Long id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    /**
     * 处理"/user/{id}"的PUT请求，用来更新User信息
     *
     * @param user
     * @return
     */
    @PutMapping
    public String updateUser(@Validated(ValidUserGroup.Update.class) @RequestBody User user) {
        User u = users.get(user.getId());
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(user.getId(), u);
        return "success";
    }

    /**
     * 处理"/users/{id}"的DELETE请求，用来删除User
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }

}