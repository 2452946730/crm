package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.Tran;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/9/18 16:33
 */
public interface TranService {
    List<Tran> queryTranByConditionForPage(Map map);
    int queryCountByConditionForPage(Map map);
}
