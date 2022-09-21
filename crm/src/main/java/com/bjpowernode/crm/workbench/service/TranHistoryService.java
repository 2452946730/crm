package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.TranHistory;

import java.util.List;

/**
 * @Date 2022/9/21 10:01
 */
public interface TranHistoryService {
    /**
     * 根据交易的id 批量查询交易的历史记录
     * @param tranId  交易的id
     * @return
     */
    List<TranHistory> queryTranHistoryForDetailByTranId(String tranId);
}
