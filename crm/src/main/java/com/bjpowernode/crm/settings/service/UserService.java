package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.model.User;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/24 11:20
 */
public interface UserService {
    //根据用户名和密码查询用户
    User queryUserByLoginActAndPwd(Map map);
    //查询所有用户
    List<User> queryAllUsers();
}
