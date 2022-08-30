package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.model.DicValue;

import java.util.List;

/**
 * @Date 2022/8/30 19:57
 */
public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String code);
}
