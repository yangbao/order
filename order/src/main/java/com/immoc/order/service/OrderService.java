package com.immoc.order.service;

import com.immoc.order.dto.OrderDTO;

public interface OrderService {

    // 需要在封装一层数据，dto-数据传输对象， 因为方法 返回值 和参数不好写
    //public void createOrder(OrderDetail orderDetail,OrderMaster orderMaster);
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * 完结订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
