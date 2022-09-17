package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.DicValue;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.model.Activity;
import com.bjpowernode.crm.workbench.model.Clue;
import com.bjpowernode.crm.workbench.model.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Date 2022/8/30 19:59
 */
@Controller
public class ClueController {
    @Resource
    private UserService userService;
    @Resource
    private DicValueService dicValueService;
    @Resource
    private ClueService clueService;
    @Resource
    private ClueRemarkService remarkService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        List<User> userList=userService.queryAllUsers();
        List<DicValue> appellationList =dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session){
        User user= (User) session.getAttribute(Contants.SYSTEM_USER);
        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formateTime(new Date()));
        RetObject retObject=new RetObject();
        try {
            //调用service
            int ret=clueService.saveCreateClue(clue);
            if(ret>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试....");
        }
        return retObject;
    }

    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    @ResponseBody
    public Object queryClueByConditionForPage(String fullname,String company,String mphone,String phone,String source,String owner,
                                              String state,Integer pageNum,Integer pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("company",company);
        map.put("mphone",mphone);
        map.put("phone",phone);
        map.put("source",source);
        map.put("owner",owner);
        map.put("state",state);
        map.put("pageSize",pageSize);
        map.put("pageNo",(pageNum-1)*pageSize);
        //调用service
        List<Clue> clueList=clueService.queryClueByConditionForPage(map);
        int totalRows=clueService.queryTotalRows(map);
        Map<String,Object> map1=new HashMap<>();
        map1.put("clueList",clueList);
        map1.put("totalRows",totalRows);
        return map1;
    }

    @RequestMapping("/workbench/clue/deleteClueByIds.do")
    @ResponseBody
    public Object deleteClueByIds(String[] id){
        RetObject retObject=new RetObject();
        try {
            //调用service,处理业务
             clueService.deleteClueByIds(id);
             retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试....");
        }
        return retObject;
    }
    @RequestMapping("/workbench/clue/queryEditClueById.do")
    @ResponseBody
    public Object queryEditClueById(String id){
        return clueService.queryClueById(id);
    }
    @RequestMapping("/workbench/clue/saveEditClue.do")
    @ResponseBody
    public Object saveEditClue(Clue clue,HttpSession session){
        User user= (User) session.getAttribute(Contants.SYSTEM_USER);
        //封装参数
        clue.setEditBy(user.getId());
        clue.setEditTime(DateUtils.formateDateTime(new Date()));
        RetObject retObject=new RetObject();
        try {
            //调用service
            int ret=clueService.saveEditClue(clue);
            if(ret>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试.....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试.....");
        }
        return retObject;
    }

    @RequestMapping("/workbench/clue/queryClueById.do")
    public String queryClueById(String id,HttpServletRequest request){
        request.setAttribute("clue",clueService.queryClueById(id));
        request.setAttribute("remarkList",remarkService.queryRemarkByClueId(id));
        request.setAttribute("activityList",activityService.queryActivityByClueId(id));
        return "workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/queryActivityByNameClueId.do")
    @ResponseBody
    public Object queryActivityByNameClueId(String activityName,String clueId){
        Map map = new HashMap();
        map.put("activityName", activityName);
        map.put("clueId",clueId);
        return activityService.queryActivityByNameClueId(map);
    }

    @RequestMapping("/workbench/clue/saveBound.do")
    @ResponseBody
    public Object saveBound(String[] activityId,String clueId){
        //封装参数
        List list = new ArrayList<>();
        ClueActivityRelation car = null;
        for(String id:activityId){
            car = new ClueActivityRelation();
            car.setId(UUIDUtils.getUUID());
            car.setActivityId(id);
            car.setClueId(clueId);
            list.add(car);
        }
        RetObject retObject = new RetObject();
        try {
            int result = clueActivityRelationService.saveRelationByList(list);
            if(result >0){
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setDate(activityList);
            } else {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试.....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试.....");
        }
        return retObject;
    }

    @PostMapping("/workbench/clue/saveUnBound.do")
    @ResponseBody
    public Object saveUnBound(ClueActivityRelation relation){
        RetObject retObject = new RetObject();
        try {
            int result = clueActivityRelationService.deleteClueActivityByClueIdAndActivityId(relation);
            if (result > 0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试....");
        }
        return retObject;
    }

    @GetMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id, HttpServletRequest request){
        //调用service,查询结果
        Clue clue = clueService.queryClueById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //将数据保存到request中
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }

    @PostMapping("/workbench/clue/queryActivityForConvertByNameAndClueId.do")
    @ResponseBody
    public Object queryActivityForConvertByNameAndClueId(String activityName,String clueId){
        Map<String,Object> map = new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        List<Activity> activityList = activityService.queryActivityForConvertByNameAndClueId(map);
        return activityList;
    }
}
