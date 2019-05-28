package com.imooc.apigateway.fliter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
@Component
public class TokenFilter extends ZuulFilter {
    //filter 类型
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
//        return PRE_TYPE;
    }
//优先级, 官方推荐的, 中间优先级是5 PRE_DECORATION_FILTER_ORDER
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }
//起不起作用
    @Override
    public boolean shouldFilter() {
        return true;
    }
//实现的逻辑
    @Override
    public Object run() throws ZuulException {
        //一次请求相关的所有资源以及相关的接口都会整合RequestContext中，
        // RequestContext用于Servlet到我们自定义的Controller层传递数据。
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        //token也可以从cookie或者header获取
        if(StringUtils.isEmpty(request.getParameter("token"))){
            //标识不通过
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);//401
        }
        return null;
    }
}
