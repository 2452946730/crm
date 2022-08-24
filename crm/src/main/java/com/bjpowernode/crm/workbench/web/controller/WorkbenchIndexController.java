package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Date 2022/8/24 12:16
 */
@Controller
public class WorkbenchIndexController {
    @RequestMapping("/workbench/index.do")
    public String index(){
        return "workbench/index";
    }
}
