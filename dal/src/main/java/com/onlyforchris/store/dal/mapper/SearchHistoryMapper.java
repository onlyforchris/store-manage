package com.onlyforchris.store.dal.mapper;

import com.onlyforchris.store.dal.model.SearchHistory;
import com.onlyforchris.store.dal.model.SearchHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    long countByExample(SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int deleteByExample(SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int insert(SearchHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int insertSelective(SearchHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    SearchHistory selectOneByExample(SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    SearchHistory selectOneByExampleSelective(@Param("example") SearchHistoryExample example, @Param("selective") SearchHistory.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    List<SearchHistory> selectByExampleSelective(@Param("example") SearchHistoryExample example, @Param("selective") SearchHistory.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    List<SearchHistory> selectByExample(SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    SearchHistory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") SearchHistory.Column... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    SearchHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    SearchHistory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") SearchHistory record, @Param("example") SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SearchHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SearchHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") SearchHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table search_history
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}