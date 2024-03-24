package com.onlyforchris.store.core.system;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Chris
 * @create: 2024-03-24 02:16
 **/
public class SystemConfig {

    // 小程序相关配置
    public final static String STORE_WX_INDEX_NEW = "store_wx_index_new";
    public final static String STORE_WX_INDEX_HOT = "store_wx_index_hot";
    public final static String STORE_WX_INDEX_BRAND = "store_wx_index_brand";
    public final static String STORE_WX_INDEX_TOPIC = "store_wx_index_topic";
    public final static String STORE_WX_INDEX_CATLOG_LIST = "store_wx_catlog_list";
    public final static String STORE_WX_INDEX_CATLOG_GOODS = "store_wx_catlog_goods";
    public final static String STORE_WX_SHARE = "store_wx_share";

    // 运费相关配置
    public final static String STORE_EXPRESS_FREIGHT_VALUE = "store_express_freight_value";
    public final static String STORE_EXPRESS_FREIGHT_MIN = "store_express_freight_min";

    // 订单相关配置
    public final static String STORE_ORDER_UNPAID = "store_order_unpaid";
    public final static String STORE_ORDER_UNCONFIRM = "store_order_unconfirm";
    public final static String STORE_ORDER_COMMENT = "store_order_comment";

    // 商场相关配置
    public final static String STORE_MALL_NAME = "store_mall_name";
    public final static String STORE_MALL_ADDRESS = "store_mall_address";
    public final static String STORE_MALL_PHONE = "store_mall_phone";
    public final static String STORE_MALL_QQ = "store_mall_qq";
    public final static String STORE_MALL_LONGITUDE = "store_mall_longitude";
    public final static String STORE_MALL_Latitude = "store_mall_latitude";


    //所有的配置均保存在该 HashMap 中
    private static Map<String, String> SYSTEM_CONFIGS = new HashMap<>();

    private static String getConfig(String keyName) {
        return SYSTEM_CONFIGS.get(keyName);
    }

    private static Integer getConfigInt(String keyName) {
        return Integer.parseInt(SYSTEM_CONFIGS.get(keyName));
    }

    private static Boolean getConfigBoolean(String keyName) {
        return Boolean.valueOf(SYSTEM_CONFIGS.get(keyName));
    }

    private static BigDecimal getConfigBigDec(String keyName) {
        return new BigDecimal(SYSTEM_CONFIGS.get(keyName));
    }

    public static Integer getNewLimit() {
        return getConfigInt(STORE_WX_INDEX_NEW);
    }

    public static Integer getHotLimit() {
        return getConfigInt(STORE_WX_INDEX_HOT);
    }

    public static Integer getBrandLimit() {
        return getConfigInt(STORE_WX_INDEX_BRAND);
    }

    public static Integer getTopicLimit() {
        return getConfigInt(STORE_WX_INDEX_TOPIC);
    }

    public static Integer getCatlogListLimit() {
        return getConfigInt(STORE_WX_INDEX_CATLOG_LIST);
    }

    public static Integer getCatlogMoreLimit() {
        return getConfigInt(STORE_WX_INDEX_CATLOG_GOODS);
    }

    public static boolean isAutoCreateShareImage() {
        return getConfigBoolean(STORE_WX_SHARE);
    }

    public static BigDecimal getFreight() {
        return getConfigBigDec(STORE_EXPRESS_FREIGHT_VALUE);
    }

    public static BigDecimal getFreightLimit() {
        return getConfigBigDec(STORE_EXPRESS_FREIGHT_MIN);
    }

    public static Integer getOrderUnpaid() {
        return getConfigInt(STORE_ORDER_UNPAID);
    }

    public static Integer getOrderUnconfirm() {
        return getConfigInt(STORE_ORDER_UNCONFIRM);
    }

    public static Integer getOrderComment() {
        return getConfigInt(STORE_ORDER_COMMENT);
    }

    public static String getMallName() {
        return getConfig(STORE_MALL_NAME);
    }

    public static String getMallAddress() {
        return getConfig(STORE_MALL_ADDRESS);
    }

    public static String getMallPhone() {
        return getConfig(STORE_MALL_PHONE);
    }

    public static String getMallQQ() {
        return getConfig(STORE_MALL_QQ);
    }

    public static String getMallLongitude() {
        return getConfig(STORE_MALL_LONGITUDE);
    }

    public static String getMallLatitude() {
        return getConfig(STORE_MALL_Latitude);
    }

    public static void setConfigs(Map<String, String> configs) {
        SYSTEM_CONFIGS = configs;
    }

    public static void updateConfigs(Map<String, String> data) {
        SYSTEM_CONFIGS.putAll(data);
    }
}
