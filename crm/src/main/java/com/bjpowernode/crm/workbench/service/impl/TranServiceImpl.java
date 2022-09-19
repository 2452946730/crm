package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.model.Tran;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/9/18 16:34
 */
@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranMapper tranMapper;

    @Override
    public List<Tran> queryTranByConditionForPage(Map map) {
        return tranMapper.selectTranByConditionForPage(map);
    }

    @Override
    public int queryCountByConditionForPage(Map map) {
        return tranMapper.selectCountByConditionForPage(map);
    }
}
