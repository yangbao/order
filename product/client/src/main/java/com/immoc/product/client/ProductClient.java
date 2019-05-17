package com.immoc.product.client;


import com.immoc.product.common.DecreaseStockInput;
import com.immoc.product.common.ProductInfoOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 把对外提供接口的服务转过来，从productController转移过来
 */
@FeignClient(name="product")
public interface ProductClient {
    //供给order调用的服务
    //ProductInfo -- >ProductInfoOutput (ID date 就不要了)
    @PostMapping("/product/listForOrder")
    public List<ProductInfoOutput> listForOrder(@RequestBody List<String> productIdList);
    //减库存
    //   CarDTO order product 里面都有-->DecreaseStockInput
    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> decreaseStockInputs) ;

}

