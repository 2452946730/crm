package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2022/8/23 19:18
 */
@Controller
public class UserController {
    @RequestMapping("/settings/qx/user/doLogin")
    public String doLogin(){
        return "settings/qx/user/login";
    }
}
