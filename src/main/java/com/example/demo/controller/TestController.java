package com.example.demo.controller;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: com.example.demo.bean
 * @Author: BYM
 * @Date: 2019/6/19
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @RequestMapping(value = "/user/name/{id}", method = RequestMethod.GET)
    public String getUsername(@PathVariable int id) {
        return userService.getUsername(id);
    }


    @RequestMapping("/user/{id}/{name}")
    public User updateUser(@PathVariable int id, @PathVariable String name) {
        User user = new User(id, name);
        return userService.updateUser(user);
    }

    @RequestMapping("/test")
    public void test() {
        Long abc = redisTemplate.opsForSet().add("abc", userService.getUser(1));
    }
}
