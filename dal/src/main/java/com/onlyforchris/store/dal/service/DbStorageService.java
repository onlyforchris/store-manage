package com.onlyforchris.store.dal.service;

import com.github.pagehelper.PageHelper;
import com.onlyforchris.store.dal.mapper.StorageMapper;
import com.onlyforchris.store.dal.model.DbStorage;
import com.onlyforchris.store.dal.model.StorageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service("dbStorageService")
public class DbStorageService {
    @Autowired
    private StorageMapper storageMapper;

    public void deleteByKey(String key) {
        StorageExample example = new StorageExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(DbStorage dbStorageInfo) {
        dbStorageInfo.setAddTime(LocalDateTime.now());
        dbStorageInfo.setUpdateTime(LocalDateTime.now());
        storageMapper.insertSelective(dbStorageInfo);
    }

    public DbStorage findByKey(String key) {
        StorageExample example = new StorageExample();
        example.or().andKeyEqualTo(key).andDeletedEqualTo(false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(DbStorage dbStorageInfo) {
        dbStorageInfo.setUpdateTime(LocalDateTime.now());
        return storageMapper.updateByPrimaryKeySelective(dbStorageInfo);
    }

    public DbStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<DbStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        StorageExample example = new StorageExample();
        StorageExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
