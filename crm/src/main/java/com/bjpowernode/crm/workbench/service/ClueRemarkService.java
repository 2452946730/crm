package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.model.ClueRemark;

import java.util.List;

/**
 * @Date 2022/9/2 20:02
 */
public interface ClueRemarkService {
    List<ClueRemark> queryAllRemark();
    int saveCreateClueRemark(ClueRemark remark);
    int deleteClueRemarkById(String id);
}
