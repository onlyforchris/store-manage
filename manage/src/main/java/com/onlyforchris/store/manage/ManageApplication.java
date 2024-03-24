package com.onlyforchris.store.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.onlyforchris.store.dal", "com.onlyforchris.store.core",
        "com.onlyforchris.store.manage"})
@MapperScan("com.onlyforchris.store.dal.mapper")
@EnableTransactionManagement
@EnableScheduling
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

}