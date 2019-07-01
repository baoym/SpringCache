package com.example.demo.service;

import com.example.demo.bean.User;
import org.springframework.cache.annotation.Cacheable;

/**
 * @ClassName: com.example.demo.service
 * @Author: BYM
 * @Date: 2019/6/26
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
public interface UserService {
    User getUser(int id);

    @Cacheable(value="users", key="targetClass+#p0")
    String getUsername(int id);

    User updateUser(User user);
}
