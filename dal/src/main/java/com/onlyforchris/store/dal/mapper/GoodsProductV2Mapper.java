package com.onlyforchris.store.dal.mapper;

import org.apache.ibatis.annotations.Param;

public interface GoodsProductV2Mapper {
    int addStock(@Param("id") Integer id, @Param("num") Short num);
    int reduceStock(@Param("id") Integer id, @Param("num") Short num);
}