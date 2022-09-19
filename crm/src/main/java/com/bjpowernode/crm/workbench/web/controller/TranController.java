package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.model.DicValue;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Date 2022/9/18 16:33
 */
@Controller
public class TranController {
    @Resource
    private UserService userService;
    @Resource
    private DicValueService dicValueService;
    @Resource
    private TranService tranService;

    @GetMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        return "workbench/transaction/index";
    }

    @PostMapping("/workbench/transaction/queryTranByConditionForPage.do")
    @ResponseBody
    public Object queryTranByConditionForPage(String owner,String name,String customerName,String stage,String type,
                                              String source,String contactsName,Integer pageNo,Integer pageSize){
        Map map = new HashMap();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerName",customerName);
        map.put("stage",stage);
        map.put("type",type);
        map.put("source",source);
        map.put("contactsName",contactsName);
        map.put("offset",(pageNo-1)*pageSize);
        map.put("rows",pageSize);
        List tranList = tranService.queryTranByConditionForPage(map);
        int totalRows = tranService.queryCountByConditionForPage(map);
        map = new HashMap();
        map.put("tranList",tranList);
        map.put("totalRows",totalRows);
        return map;
    }

    @GetMapping("/workbench/transaction/save.do")
    public String save(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("userList",userList);
        return "workbench/transaction/save";
    }
    @PostMapping("/workbench/transaction/possibility.do")
    @ResponseBody
    public String possibility(String stage){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("possibility");
        return resourceBundle.getString(stage);
    }
}
