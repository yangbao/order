package com.imooc.product.service;

import com.immoc.product.common.DecreaseStockInput;
import com.immoc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;

import java.util.List;


public interface ProductService {

    List<ProductInfo> findUpAll();

    List<ProductInfoOutput> findByIds(List<String> productIdList);
    //口库存
    void decreaseStock(List<DecreaseStockInput> decreaseStockInputs);
}
