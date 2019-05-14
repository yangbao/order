package com.immoc.order.service.impl;

import com.immoc.order.dto.CartDTO;
import com.immoc.product.client.ProductClient;
import com.immoc.order.dataobject.OrderDetail;
import com.immoc.order.dataobject.OrderMaster;
//import com.immoc.order.dataobject.ProductInfo;
//import com.immoc.order.dto.CartDTO;
import com.immoc.order.dto.OrderDTO;
import com.immoc.order.enums.OrderStatusEnum;
import com.immoc.order.enums.PayStatusEnum;
import com.immoc.order.repository.OrderDetailRepository;
import com.immoc.order.repository.OrderMasterRepository;
import com.immoc.order.service.OrderService;
import com.immoc.order.util.KeyUtil;
import com.immoc.product.common.DecreaseStockInput;
import com.immoc.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private ProductClient productClient;


    // TODO 1. 查询商品信息(调用商品服务)
    // TODO 2. 计算总价
    // TODO 3. 扣库存(调用商品服务)
    // TODO 4. 订单入库
    // 订单入库 - 生成订单

    // 参数有些传入是空的
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {

        String orderId = KeyUtil.genUniqueKey();

        //1. 查询商品信息(调用商品服务)
        List<String> productIds =  orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfos = productClient.listForOrder(productIds);

        //2. 计算总价 商品单价*商品数量 - 多种商品要累加
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for (ProductInfoOutput productInfo : productInfos) {
//            productInfo.getProductPrice(); // -- 单价
            for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    orderAmout = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(orderAmout);//多个产品要累加的
                    //计算完后要把实体类的一些信息补充上
                    BeanUtils.copyProperties(productInfo, orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //3. 扣库存(调用商品服务)
        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(decreaseStockInputList);

        //4. 订单入库
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        //直接copy不需要这个了 -  orderMaster.setOrderId(KeyUtil.genUniqueKey());
        BeanUtils.copyProperties(orderDTO,orderMaster);

        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
//        orderMaster.setOrderId(KeyUtil.genUniqueKey());应该放到copy之前

        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }
}
