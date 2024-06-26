package com.onlyforchris.store.dal.service;

import com.github.pagehelper.PageHelper;
import com.onlyforchris.store.dal.mapper.AdminMapper;
import com.onlyforchris.store.dal.model.Admin;
import com.onlyforchris.store.dal.model.Admin.Column;
import com.onlyforchris.store.dal.model.AdminExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar, Column.roleIds};
    @Resource
    private AdminMapper adminMapper;

    public List<Admin> findAdmin(String username) {
        AdminExample example = new AdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public Admin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public List<Admin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int updateById(Admin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(Admin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    public Admin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }

    public List<Admin> all() {
        AdminExample example = new AdminExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }
}
