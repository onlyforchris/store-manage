package com.onlyforchris.store.wx.controller;

import com.onlyforchris.store.ResponseUtil;
import com.onlyforchris.store.dal.constant.AfterSaleConstant;
import com.onlyforchris.store.dal.model.Aftersale;
import com.onlyforchris.store.dal.model.Order;
import com.onlyforchris.store.dal.model.OrderGoods;
import com.onlyforchris.store.dal.service.AftersaleService;
import com.onlyforchris.store.dal.service.OrderGoodsService;
import com.onlyforchris.store.dal.service.OrderService;
import com.onlyforchris.store.dal.utils.OrderUtil;
import com.onlyforchris.store.validator.Sort;
import com.onlyforchris.store.wx.annotation.LoginUser;
import com.onlyforchris.store.wx.util.WxResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 售后服务
 * <p>
 * 目前只支持订单整体售后，不支持订单商品单个售后
 * <p>
 * 一个订单只能有一个售后记录
 */
@RestController
@RequestMapping("/wx/aftersale")
@Validated
public class WxAfterSaleController {
    private final Log logger = LogFactory.getLog(WxAfterSaleController.class);

    @Autowired
    private AftersaleService aftersaleService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsService orderGoodsService;

    /**
     * 售后列表
     *
     * @param userId 用户ID
     * @param status 状态类型，如果是空则是全部
     * @param page   分页页数
     * @param limit  分页大小
     * @param sort   排序字段
     * @param order  排序方式
     * @return 售后列表
     */
    @GetMapping("list")
    public Object list(@LoginUser Integer userId,
                       @RequestParam Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @com.onlyforchris.store.validator.Order @RequestParam(defaultValue = "desc") String order) {
        if (userId == null) {
            return ResponseUtil.unLogin();
        }

        List<Aftersale> aftersaleList = aftersaleService.queryList(userId, status, page, limit, sort, order);

        List<Map<String, Object>> aftersaleVoList = new ArrayList<>(aftersaleList.size());
        for (Aftersale aftersale : aftersaleList) {
            List<OrderGoods> orderGoodsList = orderGoodsService.queryByOid(aftersale.getOrderId());

            Map<String, Object> aftersaleVo = new HashMap<>();
            aftersaleVo.put("aftersale", aftersale);
            aftersaleVo.put("goodsList", orderGoodsList);

            aftersaleVoList.add(aftersaleVo);
        }

        return ResponseUtil.okList(aftersaleVoList, aftersaleList);
    }

    /**
     * 售后详情
     *
     * @param orderId 订单ID
     * @return 售后详情
     */
    @GetMapping("detail")
    public Object detail(@LoginUser Integer userId, @NotNull Integer orderId) {
        if (userId == null) {
            return ResponseUtil.unLogin();
        }

        Order order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        List<OrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
        Aftersale aftersale = aftersaleService.findByOrderId(userId, orderId);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("aftersale", aftersale);
        data.put("order", order);
        data.put("orderGoods", orderGoodsList);
        return ResponseUtil.success(data);
    }

    /**
     * 申请售后
     *
     * @param userId    用户ID
     * @param aftersale 用户售后信息
     * @return 操作结果
     */
    @PostMapping("submit")
    public Object submit(@LoginUser Integer userId, @RequestBody Aftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unLogin();
        }
        Object error = validate(aftersale);
        if (error != null) {
            return error;
        }
        // 进一步验证
        Integer orderId = aftersale.getOrderId();
        if (orderId == null) {
            return ResponseUtil.badArgument();
        }
        Order order = orderService.findById(userId, orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }

        // 订单必须完成才能进入售后流程。
        if (!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)) {
            return ResponseUtil.failed(WxResponseCode.AFTERSALE_UNALLOWED, "不能申请售后");
        }
        BigDecimal amount = order.getActualPrice().subtract(order.getFreightPrice());
        if (aftersale.getAmount().compareTo(amount) > 0) {
            return ResponseUtil.failed(WxResponseCode.AFTERSALE_INVALID_AMOUNT, "退款金额不正确");
        }
        Short afterStatus = order.getAftersaleStatus();
        if (afterStatus.equals(AfterSaleConstant.STATUS_RECEPT) || afterStatus.equals(AfterSaleConstant.STATUS_REFUND)) {
            return ResponseUtil.failed(WxResponseCode.AFTERSALE_INVALID_AMOUNT, "已申请售后");
        }

        // 如果有旧的售后记录则删除（例如用户已取消，管理员拒绝）
        aftersaleService.deleteByOrderId(userId, orderId);

        aftersale.setStatus(AfterSaleConstant.STATUS_REQUEST);
        aftersale.setAftersaleSn(aftersaleService.generateAftersaleSn(userId));
        aftersale.setUserId(userId);
        aftersaleService.add(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAfterSaleStatus(orderId, AfterSaleConstant.STATUS_REQUEST);
        return ResponseUtil.success();
    }

    /**
     * 取消售后
     * <p>
     * 如果管理员还没有审核，用户可以取消自己的售后申请
     *
     * @param userId    用户ID
     * @param aftersale 用户售后信息
     * @return 操作结果
     */
    @PostMapping("cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody Aftersale aftersale) {
        if (userId == null) {
            return ResponseUtil.unLogin();
        }
        Integer id = aftersale.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        Aftersale aftersaleOne = aftersaleService.findById(userId, id);
        if (aftersaleOne == null) {
            return ResponseUtil.badArgument();
        }

        Integer orderId = aftersaleOne.getOrderId();
        Order order = orderService.findById(userId, orderId);
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 订单必须完成才能进入售后流程。
        if (!OrderUtil.isConfirmStatus(order) && !OrderUtil.isAutoConfirmStatus(order)) {
            return ResponseUtil.failed(WxResponseCode.AFTERSALE_UNALLOWED, "不支持售后");
        }
        Short afterStatus = order.getAftersaleStatus();
        if (!afterStatus.equals(AfterSaleConstant.STATUS_REQUEST)) {
            return ResponseUtil.failed(WxResponseCode.AFTERSALE_INVALID_STATUS, "不能取消售后");
        }

        aftersale.setStatus(AfterSaleConstant.STATUS_CANCEL);
        aftersale.setUserId(userId);
        aftersaleService.updateById(aftersale);

        // 订单的aftersale_status和售后记录的status是一致的。
        orderService.updateAfterSaleStatus(orderId, AfterSaleConstant.STATUS_CANCEL);
        return ResponseUtil.success();
    }

    private Object validate(Aftersale aftersale) {
        Short type = aftersale.getType();
        if (type == null) {
            return ResponseUtil.badArgument();
        }
        BigDecimal amount = aftersale.getAmount();
        if (amount == null) {
            return ResponseUtil.badArgument();
        }
        String reason = aftersale.getReason();
        if (StringUtils.isEmpty(reason)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}