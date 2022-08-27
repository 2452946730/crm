package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.model.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/25 14:18
 */
@Controller
public class ActivityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        System.out.println(userList);
        request.setAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session){
        User user= (User) session.getAttribute(Contants.SYSTEM_USER);
        //接收参数,并再封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        //处理请求
        RetObject retObject=new RetObject();
        try {
            int ret=activityService.saveCreateActivity(activity);
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

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String owner,String name,String startDate,String endDate,
                                                  Integer pageNum,Integer pageSize){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageNo",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service,处理业务,查询长活动列表
        List<Activity> activityList=activityService.queryActivityByConditionForPage(map);
        //查询总条数
        int totalRows=activityService.queryCountByActivityForCondition(map);
        Map<String,Object> map1=new HashMap<>();
        map1.put("activityList",activityList);
        map1.put("totalRows",totalRows);

        return map1;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id){
        System.out.println(id);
        RetObject retObject=new RetObject();
        try {
            //调用service处理业务,返回处理结果
            int count = activityService.deleteActivityByIds(id);
            //根据处理结果生成响应数据
            if(count>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统繁忙,请稍后重试....");
            }
        } catch(Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统繁忙,请稍后重试....");
        }
        return retObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivityById(String id){
        return activityService.queryActivityById(id);
    }

    @RequestMapping("/workbench/activity/updateActivity.do")
    @ResponseBody
    public Object updateActivity(Activity activity,HttpServletRequest request){
        //封装参数
        User user = (User) request.getSession().getAttribute(Contants.SYSTEM_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        RetObject retObject=new RetObject();
        try {
            //调用service,更新数据
            int count=activityService.updateActivity(activity);
            if(count>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统繁忙,请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统繁忙,请稍后重试...");
        }
        return retObject;
    }
}
