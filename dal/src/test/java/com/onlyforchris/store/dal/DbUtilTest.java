package com.onlyforchris.store.dal;

import com.onlyforchris.store.dal.utils.DbUtil;
import org.junit.Test;

import java.io.File;

public class DbUtilTest {
    @Test
    public void testBackup() {
        File file = new File("test.sql");
        DbUtil.backup(file, "water_store", "water_store123456", "water_store");
    }

    //    这个测试用例会重置water-store数据库，所以比较危险，请开发者注意
//    @Test
    public void testLoad() {
        File file = new File("test.sql");
        DbUtil.load(file, "water_store", "water_store123456", "water_store");
    }
}
