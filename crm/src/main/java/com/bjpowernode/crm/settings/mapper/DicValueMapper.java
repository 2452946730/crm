package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.model.DicValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DicValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    int insert(DicValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    int insertSelective(DicValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    DicValue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    int updateByPrimaryKeySelective(DicValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbggenerated Tue Aug 30 19:51:48 CST 2022
     */
    int updateByPrimaryKey(DicValue record);

    /**
     * 根据typeCode 查询对应的值
     * @param code
     * @return
     */
    List<DicValue> selectDicValueByTypeCode(String code);

    /**
     * 根据value 查询 orderNo
     * @param value
     * @return
     */
    String selectOrderNoByValue(@Param("value") String value);
}
