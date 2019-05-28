package com.imooc.apigateway.fliter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * add something to the response header
 */
@Component
public class PostFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
//add something in the header
    @Override
    public Object run() throws ZuulException {

        HttpServletResponse response = RequestContext.getCurrentContext().getResponse();
        response.setHeader("DX-FOO", UUID.randomUUID().toString());
        return null;
    }
}
