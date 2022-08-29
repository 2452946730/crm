package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.Activity;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/25 16:32
 */
public interface ActivityService {
    int saveCreateActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    int queryCountByActivityForCondition(Map<String,Object> map);
    int deleteActivityByIds(String[] ids);
    Activity queryActivityById(String id);
    int updateActivity(Activity activity);
    HSSFWorkbook queryAllActivity() throws Exception;
    HSSFWorkbook queryActivityByIds(String[] ids) throws Exception;
    int saveCreateActivityByList(List<Activity> activityList);
    Activity queryActivityForDetailById(String id);
}
