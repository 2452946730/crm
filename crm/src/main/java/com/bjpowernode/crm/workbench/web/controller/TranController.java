package com.bjpowernode.crm.workbench.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.settings.model.DicValue;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.VO.TranVO;
import com.bjpowernode.crm.workbench.model.Tran;
import com.bjpowernode.crm.workbench.model.TranHistory;
import com.bjpowernode.crm.workbench.model.TranRemark;
import com.bjpowernode.crm.workbench.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Resource
    private CustomerService customerService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ContactsService contactsService;
    @Resource
    private TranRemarkService tranRemarkService;
    @Resource
    private TranHistoryService tranHistoryService;

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

    @PostMapping("/workbench/transaction/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String name){
        //https://way.jd.com/YunKun/fuzzyQuery?Keyword=????????????&PageIndex=1&appkey=
        Map map = new HashMap();
        map.put("Keyword",name);
        map.put("PageIndex",1);
        map.put("appkey","3680fa919b771148da626bbcbd459475");
        //String s = HttpUtil.get("https://way.jd.com/YunKun/fuzzyQuery", map);
        String s = "{\n" +
                "    \"code\": \"10000\",\n" +
                "    \"charge\": false,\n" +
                "    \"remain\": 1305,\n" +
                "    \"msg\": \"????????????\",\n" +
                "    \"result\": {\n" +
                "        \"status\": \"200\",\n" +
                "        \"message\": \"????????????\",\n" +
                "        \"data\": {\n" +
                "            \"list\": [\n" +
                "                {\n" +
                "                    \"name\": \"??????????????????????????????\",\n" +
                "                    \"legal_person_name\": \"??????\",\n" +
                "                    \"reg_capital\": \"185000???????????????\",\n" +
                "                    \"reg_date\": \"2010-03-03\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"??????\",\n" +
                "                    \"reg_capital\": \"100000???????????????\",\n" +
                "                    \"reg_date\": \"2015-09-21\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-06-21\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-04-23\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-03-07\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2014-04-29\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-06-06\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-03-05\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-03-08\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-05-23\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-04-06\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-07-25\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-02-09\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-03-07\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"?????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-07-19\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"?????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2013-11-26\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2014-04-28\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"??????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2012-02-16\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"???\",\n" +
                "                    \"reg_date\": \"2014-11-07\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"????????????\",\n" +
                "                    \"legal_person_name\": \"?????????\",\n" +
                "                    \"reg_capital\": \"100???????????????\",\n" +
                "                    \"reg_date\": \"2014-04-16\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"total\": 64,\n" +
                "            \"num\": 20\n" +
                "        }\n" +
                "    }\n" +
                "}";
        JSONObject result = JSONUtil.parseObj(s);
        JSONArray list = null;
        if ("10000".equals(result.getStr("code"))) {
            if ("200".equals(result.getJSONObject("result").getStr("status"))){
                 list =  result.getJSONObject("result").getJSONObject("data").getJSONArray("list");
            }
        }
        List nameList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JSON json = (JSON) list.get(i);
            nameList.add(json.getByPath("name"));
        }
        return nameList;
        //return customerService.queryCustomerNameByName(name);
    }

    @PostMapping("/workbench/transaction/queryAllActivity.do")
    @ResponseBody
    public Object queryAllActivity(String activityName){
        return activityService.selectActivityForTran(activityName);
    }
    @PostMapping("/workbench/transaction/queryAllContacts.do")
    @ResponseBody
    public Object queryAllContacts(String contactsName){
        return contactsService.queryAllContacts(contactsName);
    }

    @PostMapping("/workbench/transaction/saveCreateTran.do")
    @ResponseBody
    public Object saveCreateTran(Tran tran, HttpSession session){
        RetObject retObject = new RetObject();
        User user = (User) session.getAttribute(Contants.SYSTEM_USER);
        try{
            tranService.saveCreateTran(tran,user);
            retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e){
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("?????????,???????????????...");
        }
        return retObject;
    }

    @GetMapping("/workbench/transaction/toTranDetail.do")
    public String toTranDetail(String id, HttpServletRequest request){
        //?????????????????????
        Tran tran = tranService.queryTranForDetailById(id);
        //????????????????????????
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        //???????????????????????????
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //???????????????????????????orderNo
        String tranNo = dicValueService.queryOrderNoByValue(tran.getStage());
        //???????????????????????????
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        //???????????????orderNo
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //????????????
        TranVO tranVO = BeanUtil.copyProperties(tran, TranVO.class);
        tranVO.setPossibility(possibility);
        tranVO.setTranNO(tranNo);
        //???????????????request??????
        request.setAttribute("tran", tranVO);
        request.setAttribute("remarkList", remarkList);
        request.setAttribute("historyList", historyList);
        request.setAttribute("stageList", stageList);
        //????????????
        return "workbench/transaction/detail";
    }
}
