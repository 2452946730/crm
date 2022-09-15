package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.model.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2022/9/14 19:29
 */
@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Override
    public int saveRelationByList(List list) {
        return clueActivityRelationMapper.insertRelationByList(list);
    }

    @Override
    public int deleteClueActivityByClueIdAndActivityId(ClueActivityRelation relation) {
        return clueActivityRelationMapper.deleteClueActivityByClueIdAndActivityId(relation);
    }
}
