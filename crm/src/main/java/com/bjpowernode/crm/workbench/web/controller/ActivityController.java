package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2022/8/25 14:18
 */
@Controller
public class ActivityController {
    @Resource
    private UserService userService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        System.out.println(userList);
        request.setAttribute("userList",userList);
        return "workbench/activity/index";
    }
}
