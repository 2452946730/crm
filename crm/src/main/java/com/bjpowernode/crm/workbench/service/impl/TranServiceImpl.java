package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.model.Customer;
import com.bjpowernode.crm.workbench.model.Tran;
import com.bjpowernode.crm.workbench.model.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/9/18 16:34
 */
@Service
public class TranServiceImpl implements TranService {
    @Resource
    private TranMapper tranMapper;
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public List<Tran> queryTranByConditionForPage(Map map) {
        return tranMapper.selectTranByConditionForPage(map);
    }

    @Override
    public int queryCountByConditionForPage(Map map) {
        return tranMapper.selectCountByConditionForPage(map);
    }

    @Override
    public int saveCreateTran(Tran tran, User user) {
        int result = 0;
        //根据客户的id查询客户是否存在
        Customer customer = customerMapper.selectCustomerByName(tran.getCustomerId());
        if (customer == null) {
            customer = new Customer();
            //新建客户
            customer.setOwner(user.getId());
            customer.setCreateBy(user.getId());
            customer.setCreateTime(DateUtils.formateDateTime(new Date()));
            customer.setId(UUIDUtils.getUUID());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setName(tran.getCustomerId());
            result = customerMapper.insert(customer);
            if (result <= 0) {
                throw new RuntimeException("添加客户失败");
            }
        }
        result = 0;
        //新建交易
        tran.setId(UUIDUtils.getUUID());
        tran.setOwner(user.getId());
        tran.setCreateTime(DateUtils.formateDateTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setCustomerId(customer.getId());
        result = tranMapper.insert(tran);
        if (result <= 0) {
            throw new RuntimeException("添加交易失败");
        }
        //新建交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formateDateTime(new Date()));
        tranHistory.setTranId(tran.getId());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        result = tranHistoryMapper.insert(tranHistory);
        if (result <= 0) {
            throw new RuntimeException("添加交易历史失败!");
        }
        return result;
    }
}
