package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.ClueRemarkMapper;
import com.bjpowernode.crm.workbench.model.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2022/9/2 20:03
 */
@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Resource
    private ClueRemarkMapper remarkMapper;

    @Override
    public List<ClueRemark> queryAllRemark() {
        return remarkMapper.selectAllRemark();
    }

    @Override
    public int saveCreateClueRemark(ClueRemark remark) {
        return remarkMapper.insertClueRemark(remark);
    }

    @Override
    public int deleteClueRemarkById(String id) {
        return remarkMapper.deleteClueRemarkById(id);
    }

    @Override
    public int updateClueRemarkById(ClueRemark clueRemark) {
        return remarkMapper.updateClueRemarkById(clueRemark);
    }
}
