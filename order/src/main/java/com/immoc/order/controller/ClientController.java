package com.immoc.order.controller;

//import com.immoc.order.client.CallServiceClientTest;
//import com.immoc.order.client.ProductClient;
//import com.immoc.order.dataobject.ProductInfo;
//import com.immoc.order.dto.CartDTO;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Arrays;
//import java.util.List;

/**
 * 远程服务调用的测试
 */
//@RestController
//@RequestMapping("clientRequest")
//@Slf4j
//public class ClientController {

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private CallServiceClientTest serviceClient;
//
//    @Autowired
//    private ProductClient productClient;

//    @GetMapping("getProductMsg")
//    public String getProductMsg() {

        //1.第一种方式(直接使用restTemplate, url写死)
//        RestTemplate restTemplate = new RestTemplate();
//        String response  = restTemplate.getForObject("http://localhost:8761/server/msg",String.class);

        //2. 第二种方式(利用loadBalancerClient通过应用名获取url, 然后再使用restTemplate)
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA");
//        String URL = "http://" + serviceInstance.getHost()+":" + serviceInstance.getPort() + "/server/msg";
//        String response  = restTemplate.getForObject(URL,String.class);

        //3. 第三种方式(利用@LoadBalanced, 可在restTemplate里使用应用名字)
//        @Bean
//        @LoadBalanced
//        public RestTemplate restTemplate(){
//            return new RestTemplate();
//        }
        //使用Feign来调用服务, 转移到product Module里面去了
//
//        String response = serviceClient.getServerMsg();
//
//        log.info("response={}", response);
//        return response;
//    }
//
//    @PostMapping("/getProductList")
//    public String getProductList() {
//        List<ProductInfo> productInfoList = productClient.listForOrder(Arrays.asList("164103465734242707"));
//        log.info("response={}", productInfoList);
//        return "ok";
//    }
//    @GetMapping("/productDecreaseStock")
//    public String productDecreaseStock() {
//        productClient.decreaseStock(Arrays.asList(new CartDTO("164103465734242707", 3)));
//        return "ok";
//    }
//}
