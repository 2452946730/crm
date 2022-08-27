package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.Activity;

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
}
