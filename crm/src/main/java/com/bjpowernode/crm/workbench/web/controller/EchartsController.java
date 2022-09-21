package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.VO.FunnelVO;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/9/21 17:01
 */
@Controller
public class EchartsController {
    @Resource
    private TranService tranService;

    @GetMapping("/workbench/echart/transaction/index.do")
    public String toIndex(){
        return "workbench/echart/transaction/index";
    }

    @PostMapping("/workbench/echart/transaction/queryCountOfByStage.do")
    @ResponseBody
    public Object queryCountOfByStage(){
        return tranService.queryCountOfByStage();
    }
}
