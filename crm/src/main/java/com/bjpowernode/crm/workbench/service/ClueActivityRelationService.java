package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.ClueActivityRelation;

import java.util.List;

/**
 * @Date 2022/9/14 19:28
 */
public interface ClueActivityRelationService {
    int saveRelationByList(List list);
    int deleteClueActivityByClueIdAndActivityId(ClueActivityRelation relation);
}
