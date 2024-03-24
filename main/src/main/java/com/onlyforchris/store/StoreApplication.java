package com.onlyforchris.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: Chris
 * @create: 2024-02-17 09:48
 **/
@SpringBootApplication(scanBasePackages = {"com.onlyforchris.store"})
@MapperScan("com.onlyforchris.store.dal.mapper")
@EnableTransactionManagement
@EnableScheduling
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
