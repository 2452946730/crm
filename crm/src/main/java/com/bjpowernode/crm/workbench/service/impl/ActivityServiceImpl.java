package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.HSSFUtils;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.model.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/25 16:33
 */
@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountByActivityForCondition(Map<String, Object> map) {
        return activityMapper.selectCountByActivityForCondition(map);
    }

    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int updateActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    @Override
    public HSSFWorkbook queryAllActivity() throws Exception{
        List<Activity> activityList=activityMapper.selectAllActivity();
        return HSSFUtils.activityHSSF(activityList);
    }

    @Override
    public HSSFWorkbook queryActivityByIds(String[] ids) throws Exception {
        List<Activity> activityList=activityMapper.selectActivityByIds(ids);
        return HSSFUtils.activityHSSF(activityList);
    }

    @Override
    public int saveCreateActivityByList(List<Activity> activityList) {
        return activityMapper.insertActivityByList(activityList);
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        return activityMapper.selectActivityForDetailById(id);
    }

    @Override
    public List<Activity> queryActivityByClueId(String clueId) {
        return activityMapper.selectActivityByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityByNameClueId(Map map) {
        return activityMapper.selectActivityByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] activityId) {
        return activityMapper.selectActivityForDetailByIds(activityId);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameAndClueId(Map map) {
        return activityMapper.selectActivityForConvertByNameAndClueId(map);
    }

    @Override
    public List<Activity> selectActivityForTran(String activityName) {
        return activityMapper.selectActivityForTran(activityName);
    }
}
