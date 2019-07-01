package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: com.example.demo.controller
 * @Author: BYM
 * @Date: 2019/6/26
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
@Data
@AllArgsConstructor
public class User implements Serializable{
    private Integer id;
    private String name;

    public User(Integer id) {
        this.id = id;
    }

    }
