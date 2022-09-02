package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.model.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    int insert(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    int insertSelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    ClueRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    int updateByPrimaryKeySelective(ClueRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_remark
     *
     * @mbggenerated Fri Sep 02 19:57:47 CST 2022
     */
    int updateByPrimaryKey(ClueRemark record);

    /**
     * 查询所有的备注
     * @return
     */
    List<ClueRemark> selectAllRemark();

    /**
     * 添加线索备注
     * @param remark
     * @return
     */
    int insertClueRemark(ClueRemark remark);

    /**
     * 根据id删除备注
     * @param id
     * @return
     */
    int deleteClueRemarkById(String id);
}