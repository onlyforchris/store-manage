package com.onlyforchris.store.dal;

import com.onlyforchris.store.dal.mapper.OrderMapper;
import com.onlyforchris.store.dal.mapper.SystemMapper;
import com.onlyforchris.store.dal.model.Order;
import com.onlyforchris.store.dal.model.OrderExample;
import com.onlyforchris.store.dal.model.SystemExample;
import com.onlyforchris.store.dal.service.SystemConfigService;
import com.onlyforchris.store.dal.utils.OrderUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.onlyforchris.store.dal.model.System;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperReturnTest {

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SystemConfigService systemConfigService;

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void selectByExample(){
        OrderExample example = new OrderExample();
        example.or().andOrderStatusEqualTo(OrderUtil.STATUS_CREATE).andDeletedEqualTo(false);
        List<Order> orders = orderMapper.selectByExample(example);
        Assert.assertNotNull(orders);
    }


    @Test
    public void testQuery(){
        Map<String, String> configs = systemConfigService.queryAll();
        Assert.assertNotNull(configs);
    }

    @Test
    public void test() {
        SystemExample example = new SystemExample();
        example.or().andDeletedEqualTo(false);

        List<System> systemList = systemMapper.selectByExample(example);
        Assert.assertNotNull(systemList);

        System system = new System();
        system.setKeyName("test-system-key");
        system.setKeyValue("test-system-value");
        int updates = systemMapper.insertSelective(system);
        Assert.assertEquals(updates, 1);

        updates = systemMapper.deleteByPrimaryKey(system.getId());
        Assert.assertEquals(updates, 1);

        updates = systemMapper.updateByPrimaryKey(system);
        Assert.assertEquals(updates, 0);
    }

}

