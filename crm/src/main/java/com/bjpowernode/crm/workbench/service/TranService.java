package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.workbench.model.Tran;

import java.util.List;
import java.util.Map;

/**
 * @Date 2022/9/18 16:33
 */
public interface TranService {
    /**
     * 线索首页的分页查询
     * @param map
     * @return
     */
    List<Tran> queryTranByConditionForPage(Map map);

    /**
     * 查询所有的记录条数
     * @param map
     * @return
     */
    int queryCountByConditionForPage(Map map);

    /**
     * 保存线索
     * @param tran
     * @return
     */
    int saveCreateTran(Tran tran, User user);

    /**
     * 根据线索的id 查询线索的明细
     * @param id 线索的id
     * @return
     */
    Tran queryTranForDetailById(String id);
}
