package com.onlyforchris.store.wx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.onlyforchris.store.dal", "com.onlyforchris.store.core", "com.onlyforchris.store.wx"})
@MapperScan("com.onlyforchris.store.dal.mapper")
@EnableTransactionManagement
@EnableScheduling
public class WxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxApplication.class, args);
    }

}