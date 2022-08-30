package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.model.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date 2022/8/30 19:58
 */
@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;
    @Override
    public List<DicValue> queryDicValueByTypeCode(String code) {
        return dicValueMapper.selectDicValueByTypeCode(code);
    }
}
