package com.imooc.product.service.impl;

import com.immoc.product.common.DecreaseStockInput;
import com.immoc.product.common.ProductInfoOutput;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.enmus.ProductStatusEnum;
import com.imooc.product.enmus.ResultEnum;
import com.imooc.product.exception.ProductException;
import com.imooc.product.repository.ProductCategoryRepository;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.ProductService;
import com.imooc.product.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 查询上架的所有商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findByIds(List<String> productIdList) {
        //productInfo -- >ProductInfoOutput
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                }).collect(Collectors.toList());
    }

//    @Override
//    @Transactional
//    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputs) {
//
//        System.out.println("aaaaaaaaaaaaaaaa");
//        List<ProductInfo> productInfoList = new ArrayList<>();
//        for (DecreaseStockInput decreaseStockInput: decreaseStockInputs) {
//            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
//            //判断商品是否存在
//            if (!productInfoOptional.isPresent()){
//                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
//            }
//            ProductInfo productInfo = productInfoOptional.get();
//            //库存是否足够
//            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
//            if (result < 0) {
//                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
//            }
//            productInfo.setProductStock(result);
//            productInfoRepository.save(productInfo);
//            productInfoList.add(productInfo);
            //这里有个坑, 不要把扣库存的mq消息写到这里,因为购物车多个商品, 一些成功了之后库存扣除了,可是另外一些失败
//            //就意味着数据可以回滚可是消息已经发出去了,跟设计有关, 设计一起成功或者失败
//        }
//    // 应该在全部成功之后再做发送消息的操作, 通知订单这里的库存有变化
//        System.out.println(productInfoList);
//        log.warn("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        notifyStockChanged(productInfoList);
//
//    }
//
//    private void notifyStockChanged(List<ProductInfo> productInfoList) {
//
//        System.out.println("aaaaaaaaaaaaaa");
//        //List<DecreaseStockInput> --> List<ProductInfoOutput>
//        List<ProductInfoOutput>  productInfoOutputList= productInfoList.stream().map(e->{
//            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
//            BeanUtils.copyProperties(e,productInfoOutput);
//            return productInfoOutput;
//        }).collect(Collectors.toList());
//        amqpTemplate.convertAndSend("productInfoQueue", JsonUtil.toJson(productInfoOutputList));
//    }
    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = decreaseStockProcess(decreaseStockInputList);

        //发送mq消息
        List<ProductInfoOutput> productInfoOutputList = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        System.out.println(productInfoOutputList.size()+"-------------------");
        amqpTemplate.convertAndSend("productInfoQueue", JsonUtil.toJson(productInfoOutputList));

    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
        for (DecreaseStockInput decreaseStockInput: decreaseStockInputList) {
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(decreaseStockInput.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - decreaseStockInput.getProductQuantity();
            if (result < 0) {
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
