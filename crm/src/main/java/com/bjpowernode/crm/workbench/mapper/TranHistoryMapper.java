package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.model.TranHistory;

public interface TranHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    int insert(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    int insertSelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    TranHistory selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    int updateByPrimaryKeySelective(TranHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_history
     *
     * @mbggenerated Wed Sep 21 09:25:29 CST 2022
     */
    int updateByPrimaryKey(TranHistory record);
}