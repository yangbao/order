package com.immoc.order.service.impl;

import com.immoc.order.dataobject.OrderMaster;
import com.immoc.order.dto.OrderDTO;
import com.immoc.order.enums.OrderStatusEnum;
import com.immoc.order.enums.PayStatusEnum;
import com.immoc.order.repository.OrderDetailRepository;
import com.immoc.order.repository.OrderMasterRepository;
import com.immoc.order.service.OrderService;
import com.immoc.order.util.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    // TODO 2. 查询商品信息(调用商品服务)
    // TODO 3. 计算总价
    // TODO 4. 扣库存(调用商品服务)
    // 订单入库 - 生成订单

     // 参数有些传入是空的
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //订单入库
        orderDTO.setOrderId(KeyUtil.genUniqueKey());
        //直接copy不需要这个了 -  orderMaster.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderMaster);

        orderMaster.setOrderAmount(new BigDecimal(5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
//        orderMaster.setOrderId(KeyUtil.genUniqueKey());

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
