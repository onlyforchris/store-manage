package com.onlyforchris.store.dal.mapper;

import org.apache.ibatis.annotations.Param;
import com.onlyforchris.store.dal.model.Issue;
import com.onlyforchris.store.dal.model.IssueExample;

import java.util.List;

public interface IssueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    long countByExample(IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int deleteByExample(IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int insert(Issue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int insertSelective(Issue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    Issue selectOneByExample(IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    Issue selectOneByExampleSelective(@Param("example") IssueExample example, @Param("selective") Issue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    List<Issue> selectByExampleSelective(@Param("example") IssueExample example, @Param("selective") Issue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    List<Issue> selectByExample(IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    Issue selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Issue.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    Issue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    Issue selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Issue record, @Param("example") IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Issue record, @Param("example") IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Issue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Issue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") IssueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table issue
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}