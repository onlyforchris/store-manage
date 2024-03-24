package com.onlyforchris.store.dal.mapper;

import org.apache.ibatis.annotations.Param;
import com.onlyforchris.store.dal.model.GoodsAttribute;
import com.onlyforchris.store.dal.model.GoodsAttributeExample;

import java.util.List;

public interface GoodsAttributeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    long countByExample(GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int deleteByExample(GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int insert(GoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int insertSelective(GoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    GoodsAttribute selectOneByExample(GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    GoodsAttribute selectOneByExampleSelective(@Param("example") GoodsAttributeExample example, @Param("selective") GoodsAttribute.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    List<GoodsAttribute> selectByExampleSelective(@Param("example") GoodsAttributeExample example, @Param("selective") GoodsAttribute.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    List<GoodsAttribute> selectByExample(GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    GoodsAttribute selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") GoodsAttribute.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    GoodsAttribute selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    GoodsAttribute selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GoodsAttribute record, @Param("example") GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GoodsAttribute record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") GoodsAttributeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_attribute
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);
}