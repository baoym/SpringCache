package com.example.demo.service.impl;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@CacheConfig(cacheNames = {"user"})//统一指定value的值，这时可省略value，如果你在你的方法依旧写上了value，那么依然以方法的value值为准。
public class UserServiceImpl implements UserService {

    private static final Map<Integer, User> users = new HashMap<>(3);

    static {
        users.put(1, new User(1,"bym"));
        users.put(2, new User( 2,"wx"));
        users.put(3, new User( 3,"sqp"));
    }

    //@Cacheable(key = "targetClass + methodName +#p0")
    @Cacheable(value="users", key="#p0")
    @Override
//    @Cacheable(key = "#id")
//    @Cacheable
    public User getUser(int id) {
        log.info("缓存中没有，从数据中取...");
        return users.get(id);
    }
    @Cacheable(value="users", key="methodName +#p0")
    @Override
    public String getUsername(int id) {
        log.info("缓存中没有，从数据中取...");
        return users.get(id).getName();
    }
    /**
     * 在更新数据的同时，缓存也被更新
     * @param user
     * @return
     */
    @Override
    @CachePut(cacheNames = "space", key = "#user.id")
    public User updateUser(User user) {
        log.info("更新数据...");
        users.put(user.getId(), user);
        return user;
    }

    //清除一条缓存，key为要清空的数据
    @CacheEvict(value = "space", key = "#id")
    public void delete(int id) {
        users.remove(id);
    }

    //方法调用后清空所有缓存
    @CacheEvict(value = "space", allEntries = true)
    public void deleteAll() {
        users.clear();
    }

    //方法调用前清空所有缓存
    @CacheEvict(value = "space", beforeInvocation = true)
    @Caching()
    public void deleteAllBefore() {
        users.clear();
    }

    /**
     *   @Caching是 @Cacheable、@CachePut、@CacheEvict注解的组合
     *   以下注解的含义：
     *   1.当使用指定名字查询数据库后，数据保存到缓存
     *   2.现在使用id、age就会直接查询缓存，而不是查询数据库
     */



}
