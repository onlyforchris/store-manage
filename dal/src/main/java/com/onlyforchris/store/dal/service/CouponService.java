package com.onlyforchris.store.dal.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.onlyforchris.store.dal.constant.CouponConstant;
import com.onlyforchris.store.dal.mapper.CouponMapper;
import com.onlyforchris.store.dal.mapper.CouponUserMapper;
import com.onlyforchris.store.dal.model.Coupon;
import com.onlyforchris.store.dal.model.Coupon.Column;
import com.onlyforchris.store.dal.model.CouponExample;
import com.onlyforchris.store.dal.model.CouponUser;
import com.onlyforchris.store.dal.model.CouponUserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CouponService {
    @Resource
    private CouponMapper couponMapper;
    @Resource
    private CouponUserMapper couponUserMapper;

    private final Column[] result = new Column[]{Column.id, Column.name, Column.desc, Column.tag,
            Column.days, Column.startTime, Column.endTime,
            Column.discount, Column.min};

    /**
     * 查询，空参数
     *
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<Coupon> queryList(int offset, int limit, String sort, String order) {
        return queryList(CouponExample.newAndCreateCriteria(), offset, limit, sort, order);
    }

    /**
     * 查询
     *
     * @param criteria 可扩展的条件
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    public List<Coupon> queryList(CouponExample.Criteria criteria, int offset, int limit, String sort, String order) {
        criteria.andTypeEqualTo(CouponConstant.TYPE_COMMON).andStatusEqualTo(CouponConstant.STATUS_NORMAL).andDeletedEqualTo(false);
        criteria.example().setOrderByClause(sort + " " + order);
        PageHelper.startPage(offset, limit);
        return couponMapper.selectByExampleSelective(criteria.example(), result);
    }

    public List<Coupon> queryAvailableList(Integer userId, int offset, int limit) {
        assert userId != null;
        // 过滤掉登录账号已经领取过的coupon
        CouponExample.Criteria c = CouponExample.newAndCreateCriteria();
        List<CouponUser> used = couponUserMapper.selectByExample(
                CouponUserExample.newAndCreateCriteria().andUserIdEqualTo(userId).example()
        );
        if (used != null && !used.isEmpty()) {
            c.andIdNotIn(used.stream().map(CouponUser::getCouponId).collect(Collectors.toList()));
        }
        return queryList(c, offset, limit, "add_time", "desc");
    }

    public List<Coupon> queryList(int offset, int limit) {
        return queryList(offset, limit, "add_time", "desc");
    }

    public Coupon findById(Integer id) {
        return couponMapper.selectByPrimaryKey(id);
    }


    public Coupon findByCode(String code) {
        CouponExample example = new CouponExample();
        example.or().andCodeEqualTo(code).andTypeEqualTo(CouponConstant.TYPE_CODE).andStatusEqualTo(CouponConstant.STATUS_NORMAL).andDeletedEqualTo(false);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        if (couponList.size() > 1) {
            throw new RuntimeException("");
        } else if (couponList.size() == 0) {
            return null;
        } else {
            return couponList.get(0);
        }
    }

    /**
     * 查询新用户注册优惠券
     *
     * @return
     */
    public List<Coupon> queryRegister() {
        CouponExample example = new CouponExample();
        example.or().andTypeEqualTo(CouponConstant.TYPE_REGISTER).andStatusEqualTo(CouponConstant.STATUS_NORMAL).andDeletedEqualTo(false);
        return couponMapper.selectByExample(example);
    }

    public List<Coupon> querySelective(String name, Short type, Short status, Integer page, Integer limit, String sort, String order) {
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return couponMapper.selectByExample(example);
    }

    public void add(Coupon coupon) {
        coupon.setAddTime(LocalDateTime.now());
        coupon.setUpdateTime(LocalDateTime.now());
        couponMapper.insertSelective(coupon);
    }

    public int updateById(Coupon coupon) {
        coupon.setUpdateTime(LocalDateTime.now());
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

    public void deleteById(Integer id) {
        couponMapper.logicalDeleteByPrimaryKey(id);
    }

    private String getRandomNum(Integer num) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        base += "0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成优惠码
     *
     * @return 可使用优惠码
     */
    public String generateCode() {
        String code = getRandomNum(8);
        while (findByCode(code) != null) {
            code = getRandomNum(8);
        }
        return code;
    }

    /**
     * 查询过期的优惠券:
     * 注意：如果timeType=0, 即基于领取时间有效期的优惠券，则优惠券不会过期
     *
     * @return
     */
    public List<Coupon> queryExpired() {
        CouponExample example = new CouponExample();
        example.or().andStatusEqualTo(CouponConstant.STATUS_NORMAL).andTimeTypeEqualTo(CouponConstant.TIME_TYPE_TIME).andEndTimeLessThan(LocalDateTime.now()).andDeletedEqualTo(false);
        return couponMapper.selectByExample(example);
    }
}
