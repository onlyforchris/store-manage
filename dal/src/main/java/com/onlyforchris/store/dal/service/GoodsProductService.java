package com.onlyforchris.store.dal.service;

import com.onlyforchris.store.dal.mapper.GoodsProductV2Mapper;
import com.onlyforchris.store.dal.mapper.GoodsProductMapper;
import com.onlyforchris.store.dal.model.GoodsProduct;
import com.onlyforchris.store.dal.model.GoodsProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsProductService {
    @Resource
    private GoodsProductMapper goodsProductMapper;
    @Resource
    private GoodsProductV2Mapper goodsProductV2Mapper;

    public List<GoodsProduct> queryByGid(Integer gid) {
        GoodsProductExample example = new GoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsProductMapper.selectByExample(example);
    }

    public GoodsProduct findById(Integer id) {
        return goodsProductMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        goodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(GoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        goodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        GoodsProductExample example = new GoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) goodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        GoodsProductExample example = new GoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num) {
        return goodsProductV2Mapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num) {
        return goodsProductV2Mapper.reduceStock(id, num);
    }

    public void updateById(GoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        goodsProductMapper.updateByPrimaryKeySelective(product);
    }
}