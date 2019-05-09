package com.imooc.product.service;

import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.dto.CartDTO;

import java.util.List;


public interface ProductService {

    List<ProductInfo> findUpAll();

    List<ProductInfo> findByIds(List<String> productIdList);
    //口库存
    void decreaseStock(List<CartDTO> decreaseStockInputList);
}
