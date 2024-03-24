package com.onlyforchris.store.manage.job;

import com.onlyforchris.store.dal.constant.CouponConstant;
import com.onlyforchris.store.dal.constant.CouponUserConstant;
import com.onlyforchris.store.dal.model.Coupon;
import com.onlyforchris.store.dal.model.CouponUser;
import com.onlyforchris.store.dal.service.CouponService;
import com.onlyforchris.store.dal.service.CouponUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 检测优惠券过期情况
 *
 * @author: Chris
 * @create: 2024-03-24 05:28
 **/
@Component
public class CouponJob {
    private final Log logger = LogFactory.getLog(CouponJob.class);

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponUserService couponUserService;

    /**
     * 每隔一个小时检查
     * TODO
     * 注意，因为是相隔一个小时检查，因此导致优惠券真正超时时间可能比设定时间延迟1个小时
     */
    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void checkCouponExpired() {
        logger.info("系统开启任务检查优惠券是否已经过期");

        List<Coupon> couponList = couponService.queryExpired();
        for (Coupon coupon : couponList) {
            coupon.setStatus(CouponConstant.STATUS_EXPIRED);
            couponService.updateById(coupon);
        }

        List<CouponUser> couponUserList = couponUserService.queryExpired();
        for (CouponUser couponUser : couponUserList) {
            couponUser.setStatus(CouponUserConstant.STATUS_EXPIRED);
            couponUserService.update(couponUser);
        }

        logger.info("系统结束任务检查优惠券是否已经过期");
    }

}
