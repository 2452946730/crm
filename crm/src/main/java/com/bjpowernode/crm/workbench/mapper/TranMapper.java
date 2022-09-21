package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.model.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    int insert(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    int insertSelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    int updateByPrimaryKeySelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Sep 17 10:23:21 CST 2022
     */
    int updateByPrimaryKey(Tran record);

    /**
     * 根据条件分页查询交易
     * @param map
     * @return
     */
    List<Tran> selectTranByConditionForPage(Map map);

    /**
     * 根据条件查询总条数
     * @param map
     * @return
     */
    int selectCountByConditionForPage(Map map);
}