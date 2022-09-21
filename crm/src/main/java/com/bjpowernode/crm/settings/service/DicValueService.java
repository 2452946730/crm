package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.model.DicValue;

import java.util.List;

/**
 * @Date 2022/8/30 19:57
 */
public interface DicValueService {
    /**
     * 根据code 查询其下的所有值
     * @param code
     * @return
     */
    List<DicValue> queryDicValueByTypeCode(String code);

    /**
     * 根据value查询其的orderNo
     * @param value
     * @return
     */
    String queryOrderNoByValue(String value);
}
