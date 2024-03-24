package com.onlyforchris.store.manage.task;

import com.onlyforchris.store.core.task.Task;
import com.onlyforchris.store.core.utils.BeanUtil;
import com.onlyforchris.store.dal.constant.GrouponConstant;
import com.onlyforchris.store.dal.model.Groupon;
import com.onlyforchris.store.dal.model.GrouponRules;
import com.onlyforchris.store.dal.model.Order;
import com.onlyforchris.store.dal.service.GrouponRulesService;
import com.onlyforchris.store.dal.service.GrouponService;
import com.onlyforchris.store.dal.service.OrderService;
import com.onlyforchris.store.dal.utils.OrderUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author: Chris
 * @create: 2024-03-24 06:16
 **/
public class GrouponRuleExpiredTask extends Task {
    private final Log logger = LogFactory.getLog(GrouponRuleExpiredTask.class);
    private int grouponRuleId = -1;

    public GrouponRuleExpiredTask(Integer grouponRuleId, long delayInMilliseconds) {
        super("GrouponRuleExpiredTask-" + grouponRuleId, delayInMilliseconds);
        this.grouponRuleId = grouponRuleId;
    }

    @Override
    public void run() {
        logger.info("系统开始处理延时任务---团购规则过期---" + this.grouponRuleId);

        OrderService orderService = BeanUtil.getBean(OrderService.class);
        GrouponService grouponService = BeanUtil.getBean(GrouponService.class);
        GrouponRulesService grouponRulesService = BeanUtil.getBean(GrouponRulesService.class);

        GrouponRules grouponRules = grouponRulesService.findById(grouponRuleId);
        if (grouponRules == null) {
            return;
        }
        if (!grouponRules.getStatus().equals(GrouponConstant.RULE_STATUS_ON)) {
            return;
        }

        // 团购活动取消
        grouponRules.setStatus(GrouponConstant.RULE_STATUS_DOWN_EXPIRE);
        grouponRulesService.updateById(grouponRules);

        List<Groupon> grouponList = grouponService.queryByRuleId(grouponRuleId);
        // 用户团购处理
        for (Groupon groupon : grouponList) {
            Short status = groupon.getStatus();
            Order order = orderService.findById(groupon.getOrderId());
            if (status.equals(GrouponConstant.STATUS_NONE)) {
                groupon.setStatus(GrouponConstant.STATUS_FAIL);
                grouponService.updateById(groupon);
            } else if (status.equals(GrouponConstant.STATUS_ON)) {
                // 如果团购进行中
                // (1) 团购设置团购失败等待退款状态
                groupon.setStatus(GrouponConstant.STATUS_FAIL);
                grouponService.updateById(groupon);
                // (2) 团购订单申请退款
                if (OrderUtil.isPayStatus(order)) {
                    order.setOrderStatus(OrderUtil.STATUS_REFUND);
                    orderService.updateWithOptimisticLocker(order);
                }
            }
        }
        logger.info("系统结束处理延时任务---团购规则过期---" + this.grouponRuleId);
    }
}

