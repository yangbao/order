package com.immoc.order.client;

import com.immoc.order.dataobject.ProductInfo;
import com.immoc.order.dto.CartDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
//@FeignClient(name = "PRODUCT")
//public interface ProductClient {
////@RequestBody 在request的body里，较为复杂类型
//    @PostMapping("/product/listForOrder")
//使用Feign来调用服务, 转移到product Module里面去了
//    List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);
//
//    @PostMapping("/product/decreaseStock")
//    void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
//}
