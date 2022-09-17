package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.Clue;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/8/30 21:07
 */
public interface ClueService {
    int saveCreateClue(Clue clue);
    List<Clue> queryClueByConditionForPage(Map<String,Object> map);
    int queryTotalRows(Map<String,Object> map);
    void deleteClueByIds(String[] ids);
    Clue queryClueById(String id);
    int saveEditClue(Clue clue);
}
