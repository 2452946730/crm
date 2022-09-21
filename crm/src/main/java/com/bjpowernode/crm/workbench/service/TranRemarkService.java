package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.TranRemark;

import java.util.List;

/**
 * @Date 2022/9/21 9:52
 */
public interface TranRemarkService {
    /**
     * 根据交易的id 批量查询交易的备注
     * @param tranId 交易的id
     * @return
     */
    List<TranRemark> queryTranRemarkForDetailByTranId(String tranId);
}
