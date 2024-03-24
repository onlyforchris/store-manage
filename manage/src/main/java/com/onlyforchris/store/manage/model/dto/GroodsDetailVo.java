package com.onlyforchris.store.manage.model.dto;

import com.onlyforchris.store.dal.model.Goods;
import com.onlyforchris.store.dal.model.GoodsAttribute;
import com.onlyforchris.store.dal.model.GoodsProduct;
import com.onlyforchris.store.dal.model.GoodsSpecification;

/**
 * @author: Chris
 * @create: 2024-03-24 05:26
 **/
public class GroodsDetailVo {
    Goods goods;
    GoodsSpecification[] specifications;
    GoodsAttribute[] attributes;
    GoodsProduct[] products;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(GoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public GoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(GoodsAttribute[] attributes) {
        this.attributes = attributes;
    }

    public GoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(GoodsProduct[] products) {
        this.products = products;
    }
}
