package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.workbench.model.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Date 2022/9/2 20:03
 */
@Controller
public class ClueRemarkController {
    @Resource
    private ClueRemarkService remarkService;

    @RequestMapping("/workbench/clue/saveCreateClueRemark.do")
    @ResponseBody
    public Object saveCreateClueRemark(ClueRemark remark, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SYSTEM_USER);
        //封装参数
        remark.setCreateBy(user.getId());
        remark.setCreateTime(DateUtils.formateTime(new Date()));
        remark.setId(UUIDUtils.getUUID());
        remark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO);
        RetObject retObject = new RetObject();
        try {
            //调用service
            int ret = remarkService.saveCreateClueRemark(remark);
            if (ret > 0) {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setDate(remark);
            } else {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试...");
        }
        return retObject;
    }

    @RequestMapping("/workbench/clue/deleteClueRemarkById.do")
    @ResponseBody
    public Object deleteClueRemarkById(String id) {
        RetObject retObject = new RetObject();
        try {
            //调用service
            int ret = remarkService.deleteClueRemarkById(id);
            if (ret > 0) {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("系统忙,请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("系统忙,请稍后重试...");
        }
        return retObject;
    }

    @PostMapping("/workbench/clue/updateClueRemarkById.do")
    @ResponseBody
    public Object updateClueRemarkById(ClueRemark clueRemark, HttpSession session) {
        RetObject retObject = new RetObject();
        //封装参数
        User user = (User) session.getAttribute(Contants.SYSTEM_USER);
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES);
        clueRemark.setEditTime(DateUtils.formateDateTime(new Date()));
        try {
            //调用service
            int result = remarkService.updateClueRemarkById(clueRemark);
            //处理结果
            if (result > 0) {
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                retObject.setDate(clueRemark);
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
}
