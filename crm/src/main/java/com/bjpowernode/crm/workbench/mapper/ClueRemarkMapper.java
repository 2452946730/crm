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
     * 根据线索id查询所有的备注
     * @return
     */
    List<ClueRemark> selectRemarkByClueId(String ClueId);

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

    /**
     * 根据id更新线索的备注
     * @param id
     * @param noteContent
     * @return
     */
    int updateClueRemarkById(ClueRemark clueRemark);

    /**
     * 根据线索的id批量删除线索备注
     * @param ids  线索的备注
     * @return
     */
    int deleteClueRemarkByClueIds(String[] ids);

    /**
     * 根据线索的id查询线索备注
     * @param clueId  线索的id
     * @return
     */
    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    /**
     * 根据线索的id删除线索的备注
     * @param clueId
     * @return
     */
    int deleteClueRemarkByClueId(String clueId);

}
