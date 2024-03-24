package com.onlyforchris.store.dal.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.onlyforchris.store.dal.constant.GrouponConstant;
import com.onlyforchris.store.dal.mapper.GoodsMapper;
import com.onlyforchris.store.dal.mapper.GrouponRulesMapper;
import com.onlyforchris.store.dal.model.Goods;
import com.onlyforchris.store.dal.model.GrouponRules;
import com.onlyforchris.store.dal.model.GrouponRulesExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrouponRulesService {
    @Resource
    private GrouponRulesMapper mapper;
    @Resource
    private GoodsMapper goodsMapper;
    private Goods.Column[] goodsColumns = new Goods.Column[]{Goods.Column.id, Goods.Column.name, Goods.Column.brief, Goods.Column.picUrl, Goods.Column.counterPrice, Goods.Column.retailPrice};

    public int createRules(GrouponRules rules) {
        rules.setAddTime(LocalDateTime.now());
        rules.setUpdateTime(LocalDateTime.now());
        return mapper.insertSelective(rules);
    }

    /**
     * 根据ID查找对应团购项
     *
     * @param id
     * @return
     */
    public GrouponRules findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    /**
     * 查询某个商品关联的团购规则
     *
     * @param goodsId
     * @return
     */
    public List<GrouponRules> queryByGoodsId(Integer goodsId) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    public int countByGoodsId(Integer goodsId) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.or().andGoodsIdEqualTo(goodsId).andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        return (int) mapper.countByExample(example);
    }

    public List<GrouponRules> queryByStatus(Short status) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.or().andStatusEqualTo(status).andDeletedEqualTo(false);
        return mapper.selectByExample(example);
    }

    /**
     * 获取首页团购规则列表
     *
     * @param page
     * @param limit
     * @return
     */
    public List<GrouponRules> queryList(Integer page, Integer limit) {
        return queryList(page, limit, "add_time", "desc");
    }

    public List<GrouponRules> queryList(Integer page, Integer limit, String sort, String order) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.or().andStatusEqualTo(GrouponConstant.RULE_STATUS_ON).andDeletedEqualTo(false);
        example.setOrderByClause(sort + " " + order);
        PageHelper.startPage(page, limit);
        return mapper.selectByExample(example);
    }

    /**
     * 判断某个团购规则是否已经过期
     *
     * @return
     */
    public boolean isExpired(GrouponRules rules) {
        return (rules == null || rules.getExpireTime().isBefore(LocalDateTime.now()));
    }

    /**
     * 获取团购规则列表
     *
     * @param goodsId
     * @param page
     * @param size
     * @param sort
     * @param order
     * @return
     */
    public List<GrouponRules> querySelective(String goodsId, Integer page, Integer size, String sort, String order) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.setOrderByClause(sort + " " + order);

        GrouponRulesExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(goodsId)) {
            criteria.andGoodsIdEqualTo(Integer.parseInt(goodsId));
        }
        criteria.andDeletedEqualTo(false);

        PageHelper.startPage(page, size);
        return mapper.selectByExample(example);
    }

    public void delete(Integer id) {
        mapper.logicalDeleteByPrimaryKey(id);
    }

    public int updateById(GrouponRules grouponRules) {
        grouponRules.setUpdateTime(LocalDateTime.now());
        return mapper.updateByPrimaryKeySelective(grouponRules);
    }
}