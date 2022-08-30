package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.workbench.model.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Date 2022/8/29 18:36
 */
@Controller
public class ActivityRemarkController {
    @Resource
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    @ResponseBody
    public Object saveCreateActivityRemark(String noteContent, String activityId, HttpSession session){
        User user = (User) session.getAttribute(Contants.SYSTEM_USER);
        //封装参数
        ActivityRemark remark = new ActivityRemark();
        remark.setId(UUIDUtils.getUUID());
        remark.setNoteContent(noteContent);
        remark.setActivityId(activityId);
        remark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO);
        remark.setCreateBy(user.getId());
        remark.setCreateTime(DateUtils.formateDateTime(new Date()));
        RetObject retObject=new RetObject();
        try {
            int ret=activityRemarkService.saveCreateActivityRemark(remark);
            if(ret>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setDate(remark);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试!");
        }
        return retObject;
    }

    @RequestMapping("/workbench/activity/removeActivityRemarkById.do")
    @ResponseBody
    public Object removeActivityRemarkById(String id){
        RetObject retObject=new RetObject();
        try {
            int ret=activityRemarkService.deleteActivityRemarkById(id);
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

    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    @ResponseBody
    public Object saveEditActivityRemark(ActivityRemark remark,HttpSession session){
        User user= (User) session.getAttribute(Contants.SYSTEM_USER);
        //封装参数
        remark.setEditBy(user.getId());
        remark.setEditTime(DateUtils.formateDateTime(new Date()));
        remark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES);
        RetObject retObject=new RetObject();
        try {
            //调用service
            int ret=activityRemarkService.saveEditActivityRemark(remark);
            if(ret>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setDate(remark);
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
}
