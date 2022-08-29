package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.ActivityRemark;

import java.util.List;

/**
 * @Date 2022/8/29 17:03
 */
public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);
}
