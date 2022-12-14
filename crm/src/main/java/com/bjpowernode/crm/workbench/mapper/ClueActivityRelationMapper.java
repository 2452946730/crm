package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.model.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    int insert(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    int insertSelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    ClueActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    int updateByPrimaryKeySelective(ClueActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_clue_activity_relation
     *
     * @mbggenerated Wed Sep 14 18:31:50 CST 2022
     */
    int updateByPrimaryKey(ClueActivityRelation record);

    /**
     * 根据市场活动的id和线索的id插入
     * @param list
     * @return
     */
    int insertRelationByList(List list);

    /**
     * 根据市场活动的id和线索的id删除市场活动和线索的关联关系
     * @param relation
     * @return
     */
    int deleteClueActivityByClueIdAndActivityId(ClueActivityRelation relation);

    /**
     * 根据线索的id批量删除线索和市场活动的关联关系
     * @param ids  线索的id
     * @return
     */
    int deleteClueActivityRelationByClueIds(String[] ids);

    /**
     * 根据线索的id 查询线索与市场活动的关联关系
     * @param clueId 线索的id
     * @return
     */
    List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId);

    /**
     * 根据线索的id删除线索和市场活动的关联关系
     * @param ClueId
     * @return
     */
    int deleteClueActivityRelationByClueId(String ClueId);
}
