package com.onlyforchris.store.dal.service;

import com.github.pagehelper.PageHelper;
import com.onlyforchris.store.dal.mapper.AftersaleMapper;
import com.onlyforchris.store.dal.model.Aftersale;
import com.onlyforchris.store.dal.model.AftersaleExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class AftersaleService {
    @Resource
    private AftersaleMapper aftersaleMapper;

    public Aftersale findById(Integer id) {
        return aftersaleMapper.selectByPrimaryKey(id);
    }

    public Aftersale findById(Integer userId, Integer id) {
        AftersaleExample example = new AftersaleExample();
        example.or().andIdEqualTo(id).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }

    public List<Aftersale> queryList(Integer userId, Short status, Integer page, Integer limit, String sort, String order) {
        AftersaleExample example = new AftersaleExample();
        AftersaleExample.Criteria criteria = example.or();
        criteria.andUserIdEqualTo(userId);
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        } else {
            example.setOrderByClause(Aftersale.Column.addTime.desc());
        }

        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
    }

    public List<Aftersale> querySelective(Integer orderId, String aftersaleSn, Short status, Integer page, Integer limit, String sort, String order) {
        AftersaleExample example = new AftersaleExample();
        AftersaleExample.Criteria criteria = example.or();
        if (orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        if (!StringUtils.isEmpty(aftersaleSn)) {
            criteria.andAftersaleSnEqualTo(aftersaleSn);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        } else {
            example.setOrderByClause(Aftersale.Column.addTime.desc());
        }

        PageHelper.startPage(page, limit);
        return aftersaleMapper.selectByExample(example);
    }

    private String getRandomNum(Integer num) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public int countByAftersaleSn(Integer userId, String aftersaleSn) {
        AftersaleExample example = new AftersaleExample();
        example.or().andUserIdEqualTo(userId).andAftersaleSnEqualTo(aftersaleSn).andDeletedEqualTo(false);
        return (int) aftersaleMapper.countByExample(example);
    }

    // TODO 这里应该产生一个唯一的编号，但是实际上这里仍然存在两个售后编号相同的可能性
    public String generateAftersaleSn(Integer userId) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        String now = df.format(LocalDate.now());
        String aftersaleSn = now + getRandomNum(6);
        while (countByAftersaleSn(userId, aftersaleSn) != 0) {
            aftersaleSn = now + getRandomNum(6);
        }
        return aftersaleSn;
    }

    public void add(Aftersale aftersale) {
        aftersale.setAddTime(LocalDateTime.now());
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.insertSelective(aftersale);
    }

    public void deleteByIds(List<Integer> ids) {
        AftersaleExample example = new AftersaleExample();
        example.or().andIdIn(ids).andDeletedEqualTo(false);
        Aftersale aftersale = new Aftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    public void deleteById(Integer id) {
        aftersaleMapper.logicalDeleteByPrimaryKey(id);
    }

    public void deleteByOrderId(Integer userId, Integer orderId) {
        AftersaleExample example = new AftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        Aftersale aftersale = new Aftersale();
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersale.setDeleted(true);
        aftersaleMapper.updateByExampleSelective(aftersale, example);
    }

    public void updateById(Aftersale aftersale) {
        aftersale.setUpdateTime(LocalDateTime.now());
        aftersaleMapper.updateByPrimaryKeySelective(aftersale);
    }

    public Aftersale findByOrderId(Integer userId, Integer orderId) {
        AftersaleExample example = new AftersaleExample();
        example.or().andOrderIdEqualTo(orderId).andUserIdEqualTo(userId).andDeletedEqualTo(false);
        return aftersaleMapper.selectOneByExample(example);
    }
}
