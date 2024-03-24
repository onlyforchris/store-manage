package com.onlyforchris.store;

import org.springframework.core.env.Environment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author: Chris
 * @create: 2024-02-19 11:48
 **/
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ConfigTest {
    @Autowired
    private Environment environment;

    @Test
    public void test() {
        // 测试获取application-core.yml配置信息
        System.out.println(environment.getProperty("onlyforchris.express.appId"));
        // 测试获取application-dal.yml配置信息
        System.out.println(environment.getProperty("spring.datasource.druid.url"));
        // 测试获取application-wx.yml配置信息
        System.out.println(environment.getProperty("onlyforchris.wx.app-id"));
        // 测试获取application-manage.yml配置信息
//        System.out.println(environment.getProperty(""));
        // 测试获取application.yml配置信息
        System.out.println(environment.getProperty("logging.level.com.onlyforchris.store.wx"));
        System.out.println(environment.getProperty("logging.level.com.onlyforchris.store.manage"));
        System.out.println(environment.getProperty("logging.level.com.onlyforchris.store"));
    }

}
