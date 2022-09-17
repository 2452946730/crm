package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.mapper.ClueRemarkMapper;
import com.bjpowernode.crm.workbench.model.Clue;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/30 21:07
 */
@Service
public class ClueServiceImpl implements ClueService {
    private ClueMapper clueMapper;
    private ClueRemarkMapper clueRemarkMapper;
    private ClueActivityRelationMapper clueActivityRelationMapper;

    public ClueServiceImpl(ClueMapper clueMapper, ClueRemarkMapper clueRemarkMapper, ClueActivityRelationMapper clueActivityRelationMapper) {
        this.clueMapper = clueMapper;
        this.clueRemarkMapper = clueRemarkMapper;
        this.clueActivityRelationMapper = clueActivityRelationMapper;
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String,Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryTotalRows(Map<String,Object> map) {
        return clueMapper.selectTotalRows(map);
    }

    @Override
    public void deleteClueByIds(String[] ids) {
        clueMapper.deleteClueByIds(ids);
        clueRemarkMapper.deleteClueRemarkByClueIds(ids);
        clueActivityRelationMapper.deleteClueActivityRelationByClueIds(ids);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int saveEditClue(Clue clue) {
        return clueMapper.updateClue(clue);
    }
}
