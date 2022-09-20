package com.bjpowernode.crm.workbench.service;

import java.util.List;

/**
 * @Date 2022/9/19 9:36
 */
public interface CustomerService {
    /**
     * 根据客户名称模糊查询全名称
     * @param name 客户名称
     * @return
     */
    List<String> queryCustomerNameByName(String name);
}
