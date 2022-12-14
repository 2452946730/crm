package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.model.Activity;
import com.bjpowernode.crm.workbench.model.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @Date 2022/8/25 14:18
 */
@Controller
public class ActivityController {
    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @Resource
    private ActivityRemarkService activityRemarkService;

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
        //????????????,??????????????????
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formateDateTime(new Date()));
        activity.setCreateBy(user.getId());
        //????????????
        RetObject retObject=new RetObject();
        try {
            int ret=activityService.saveCreateActivity(activity);
            if(ret>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("?????????,???????????????....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("?????????,???????????????....");
        }
        return retObject;
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivityByConditionForPage(String owner,String name,String startDate,String endDate,
                                                  Integer pageNum,Integer pageSize){
        //????????????
        Map<String,Object> map=new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageNo",(pageNum-1)*pageSize);
        map.put("pageSize",pageSize);
        //??????service,????????????,?????????????????????
        List<Activity> activityList=activityService.queryActivityByConditionForPage(map);
        //???????????????
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
            //??????service????????????,??????????????????
            int count = activityService.deleteActivityByIds(id);
            //????????????????????????????????????
            if(count>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("????????????,???????????????....");
            }
        } catch(Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("????????????,???????????????....");
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
        //????????????
        User user = (User) request.getSession().getAttribute(Contants.SYSTEM_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formateDateTime(new Date()));
        RetObject retObject=new RetObject();
        try {
            //??????service,????????????
            int count=activityService.updateActivity(activity);
            if(count>0){
                retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retObject.setMessage("????????????,???????????????...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("????????????,???????????????...");
        }
        return retObject;
    }
    //??????????????????
    @RequestMapping("/workbench/activity/fileDownload.do")
    public void fileDownload(HttpServletResponse response) throws Exception{
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=studentList.xls");
        ServletOutputStream out = response.getOutputStream();
        InputStream is=new FileInputStream("F:\\test\\student.xls");
        byte[] bytes=new byte[256];
        int len=0;
        while((len=is.read(bytes))!= -1){
            out.write(bytes,0,len);
        }
        is.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/exportActivity.do")
    public void exportActivity(HttpServletResponse response) throws Exception{
        HSSFWorkbook wb=activityService.queryAllActivity();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
    }
    @RequestMapping("/workbench/activity/exportActivityByIds.do")
    public void exportActivityByIds(String[] id,HttpServletResponse response) throws Exception{
        HSSFWorkbook wb=activityService.queryActivityByIds(id);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        ServletOutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        wb.close();
    }
    @RequestMapping("/workbench/activity/importActivityByList.do")
    @ResponseBody
    public Object importActivityByList(MultipartFile activityFile,HttpSession session){
        RetObject retObject=new RetObject();
        User user=(User)session.getAttribute(Contants.SYSTEM_USER);
        try {
            //????????????,????????????
            /*File file=new File("F:\\powernode\\",activityFile.getOriginalFilename());
            activityFile.transferTo(file);*/
            //????????????
            //HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream("F:\\powernode\\"+activityFile.getOriginalFilename()));
            HSSFWorkbook wb=new HSSFWorkbook(activityFile.getInputStream());
            HSSFSheet sheet=wb.getSheetAt(0);
            HSSFRow row=null;
            HSSFCell cell=null;
            List<Activity> activityList=new ArrayList<>();
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Activity activity=new Activity();
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formateDateTime(new Date()));
                activity.setCreateBy(user.getId());
                row=sheet.getRow(i);
                for(int j=0;j<row.getLastCellNum();j++){
                    cell=row.getCell(j);
                    String cellValue=null;
                    if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
                       cellValue=cell.getStringCellValue();
                    }
                    if(j==0) {
                        activity.setName(cellValue);
                    }else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if (j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if (j==4){
                        activity.setDescription(cellValue);
                    }
                }
                activityList.add(activity);
            }
            //??????service,
            int ret=activityService.saveCreateActivityByList(activityList);
            retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            retObject.setDate(ret);
        } catch (Exception e) {
            e.printStackTrace();
            retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retObject.setMessage("?????????,???????????????....");
        }
        return retObject;
    }

    @RequestMapping("/workbench/activity/queryActivityForDetailById.do")
    public String queryActivityForDetailById(String id,HttpServletRequest request){
        //??????service????????????
        Activity activity=activityService.queryActivityForDetailById(id);
        List<ActivityRemark> activityRemarkList=activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("activityRemarkList",activityRemarkList);
        return "workbench/activity/detail";
    }
}
