package com.imooc.apigateway.fliter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterFilter extends ZuulFilter {

    //guava 有实现
    public static final RateLimiter rateLimiter = RateLimiter.create(100);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    //前置最高的优先级
    @Override
    public int filterOrder() {
        return -100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //取令牌
        if(!rateLimiter.tryAcquire()){
            //返回提示或者抛出异常
        }
        return null;
    }
}
