package com.onlyforchris.store.manage.controller;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.onlyforchris.store.ResponseUtil;
import com.onlyforchris.store.core.notify.NotifyService;
import com.onlyforchris.store.core.notify.NotifyType;
import com.onlyforchris.store.core.utils.JacksonUtil;
import com.onlyforchris.store.dal.constant.AfterSaleConstant;
import com.onlyforchris.store.dal.model.Aftersale;
import com.onlyforchris.store.dal.model.OrderGoods;
import com.onlyforchris.store.dal.service.AftersaleService;
import com.onlyforchris.store.dal.service.GoodsProductService;
import com.onlyforchris.store.dal.service.OrderGoodsService;
import com.onlyforchris.store.dal.service.OrderService;
import com.onlyforchris.store.manage.annotation.RequiresPermissionsDesc;
import com.onlyforchris.store.manage.common.AdminResponseCode;
import com.onlyforchris.store.manage.service.LogHelper;
import com.onlyforchris.store.validator.Order;
import com.onlyforchris.store.validator.Sort;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.onlyforchris.store.manage.common.AdminResponseCode.ORDER_REFUND_FAILED;


@RestController
@RequestMapping("/admin/aftersale")
@Validated
public class AdminAftersaleController {
    private final Log logger = LogFactory.getLog(AdminAftersaleController.class);

    @Autowired
    private AftersaleService aftersaleService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderGoodsService orderGoodsService;
    @Autowired
    private GoodsProductService goodsProductService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private NotifyService notifyService;

    @RequiresPermissions("admin:aftersale:list")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer orderId, String aftersaleSn, Short status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<Aftersale> aftersaleList = aftersaleService.querySelective(orderId, aftersaleSn, status, page, limit, sort, order);
        return ResponseUtil.okList(aftersaleList);
    }

    @RequiresPermissions("admin:aftersale:recept")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "审核通过")
    @PostMapping("/recept")
    public Object recept(@RequestBody Aftersale aftersale) {
        Integer id = aftersale.getId();
        Aftersale aftersaleOne = aftersaleService.findById(id);
        if (aftersaleOne == null) {
            return ResponseUtil.failed(AdminResponseCode.AFTERSALE_NOT_ALLOWED, "售后不存在");
        }
        Short status = aftersaleOne.getStatus();
        if (!status.equals(AfterSaleConstant.STATUS_REQUEST)) {
            return ResponseUtil.failed(AdminResponseCode.AFTERSALE_NOT_ALLOWED, "售后不能进行审核通过操作");
        }
        aftersaleOne.setStatus(AfterSaleConstant.STATUS_RECEPT);
        aftersaleOne.setHandleTime(LocalDateTime.now());
        aftersaleService.updateById(aftersaleOne);

        // 订单也要更新售后状态
        orderService.updateAfterSaleStatus(aftersaleOne.getOrderId(), AfterSaleConstant.STATUS_RECEPT);
        return ResponseUtil.success();
    }

    @RequiresPermissions("admin:aftersale:batch-recept")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "批量通过")
    @PostMapping("/batch-recept")
    public Object batchRecept(@RequestBody String body) {
        List<Integer> ids = JacksonUtil.parseIntegerList(body, "ids");
        // NOTE
        // 批量操作中，如果一部分数据项失败，应该如何处理
        // 这里采用忽略失败，继续处理其他项。
        // 当然开发者可以采取其他处理方式，具体情况具体分析，例如利用事务回滚所有操作然后返回用户失败信息
        for (Integer id : ids) {
            Aftersale aftersale = aftersaleService.findById(id);
            if (aftersale == null) {
                continue;
            }
            Short status = aftersale.getStatus();
            if (!status.equals(AfterSaleConstant.STATUS_REQUEST)) {
                continue;
            }
            aftersale.setStatus(AfterSaleConstant.STATUS_RECEPT);
            aftersale.setHandleTime(LocalDateTime.now());
            aftersaleService.updateById(aftersale);

            // 订单也要更新售后状态
            orderService.updateAfterSaleStatus(aftersale.getOrderId(), AfterSaleConstant.STATUS_RECEPT);
        }
        return ResponseUtil.success();
    }

    @RequiresPermissions("admin:aftersale:reject")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "审核拒绝")
    @PostMapping("/reject")
    public Object reject(@RequestBody Aftersale aftersale) {
        Integer id = aftersale.getId();
        Aftersale aftersaleOne = aftersaleService.findById(id);
        if (aftersaleOne == null) {
            return ResponseUtil.badArgumentValue();
        }
        Short status = aftersaleOne.getStatus();
        if (!status.equals(AfterSaleConstant.STATUS_REQUEST)) {
            return ResponseUtil.failed(AdminResponseCode.AFTERSALE_NOT_ALLOWED, "售后不能进行审核拒绝操作");
        }
        aftersaleOne.setStatus(AfterSaleConstant.STATUS_REJECT);
        aftersaleOne.setHandleTime(LocalDateTime.now());
        aftersaleService.updateById(aftersaleOne);

        // 订单也要更新售后状态
        orderService.updateAfterSaleStatus(aftersaleOne.getOrderId(), AfterSaleConstant.STATUS_REJECT);
        return ResponseUtil.success();
    }

    @RequiresPermissions("admin:aftersale:batch-reject")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "批量拒绝")
    @PostMapping("/batch-reject")
    public Object batchReject(@RequestBody String body) {
        List<Integer> ids = JacksonUtil.parseIntegerList(body, "ids");
        for (Integer id : ids) {
            Aftersale aftersale = aftersaleService.findById(id);
            if (aftersale == null) {
                continue;
            }
            Short status = aftersale.getStatus();
            if (!status.equals(AfterSaleConstant.STATUS_REQUEST)) {
                continue;
            }
            aftersale.setStatus(AfterSaleConstant.STATUS_REJECT);
            aftersale.setHandleTime(LocalDateTime.now());
            aftersaleService.updateById(aftersale);

            // 订单也要更新售后状态
            orderService.updateAfterSaleStatus(aftersale.getOrderId(), AfterSaleConstant.STATUS_REJECT);
        }
        return ResponseUtil.success();
    }

    @RequiresPermissions("admin:aftersale:refund")
    @RequiresPermissionsDesc(menu = {"商城管理", "售后管理"}, button = "退款")
    @PostMapping("/refund")
    public Object refund(@RequestBody Aftersale aftersale) {
        Integer id = aftersale.getId();
        Aftersale aftersaleOne = aftersaleService.findById(id);
        if (aftersaleOne == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!aftersaleOne.getStatus().equals(AfterSaleConstant.STATUS_RECEPT)) {
            return ResponseUtil.failed(AdminResponseCode.AFTERSALE_NOT_ALLOWED, "售后不能进行退款操作");
        }
        Integer orderId = aftersaleOne.getOrderId();
        com.onlyforchris.store.dal.model.Order order = orderService.findById(orderId);

        // 微信退款
        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setOutTradeNo(order.getOrderSn());
        wxPayRefundRequest.setOutRefundNo("refund_" + order.getOrderSn());
        // 元转成分
        Integer totalFee = aftersaleOne.getAmount().multiply(new BigDecimal(100)).intValue();
        wxPayRefundRequest.setTotalFee(order.getActualPrice().multiply(new BigDecimal(100)).intValue());
        wxPayRefundRequest.setRefundFee(totalFee);

        WxPayRefundResult wxPayRefundResult;
        try {
            wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
        } catch (WxPayException e) {
            logger.error(e.getMessage(), e);
            return ResponseUtil.failed(ORDER_REFUND_FAILED, "订单退款失败");
        }
        if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.failed(ORDER_REFUND_FAILED, "订单退款失败");
        }
        if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
            logger.warn("refund fail: " + wxPayRefundResult.getReturnMsg());
            return ResponseUtil.failed(ORDER_REFUND_FAILED, "订单退款失败");
        }

        aftersaleOne.setStatus(AfterSaleConstant.STATUS_REFUND);
        aftersaleOne.setHandleTime(LocalDateTime.now());
        aftersaleService.updateById(aftersaleOne);

        orderService.updateAfterSaleStatus(orderId, AfterSaleConstant.STATUS_REFUND);

        // NOTE
        // 如果是“退货退款”类型的售后，这里退款说明用户的货已经退回，则需要商品货品数量增加
        // 开发者也可以删除一下代码，在其他地方增加商品货品入库操作
        if (aftersale.getType().equals(AfterSaleConstant.TYPE_GOODS_REQUIRED)) {
            List<OrderGoods> orderGoodsList = orderGoodsService.queryByOid(orderId);
            for (OrderGoods orderGoods : orderGoodsList) {
                Integer productId = orderGoods.getProductId();
                Short number = orderGoods.getNumber();
                goodsProductService.addStock(productId, number);
            }
        }

        // 发送短信通知，这里采用异步发送
        // 退款成功通知用户, 例如“您申请的订单退款 [ 单号:{1} ] 已成功，请耐心等待到账。”
        // TODO 注意订单号只发后6位
        notifyService.notifySmsTemplate(order.getMobile(), NotifyType.REFUND,
                new String[]{order.getOrderSn().substring(8, 14)});

        logHelper.logOrderSucceed("退款", "订单编号 " + order.getOrderSn() + " 售后编号 " + aftersale.getAftersaleSn());
        return ResponseUtil.success();
    }
}
