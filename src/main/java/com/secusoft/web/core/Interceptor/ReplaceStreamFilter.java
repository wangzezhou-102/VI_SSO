package com.secusoft.web.core.Interceptor;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器
 */
@Configuration
public class ReplaceStreamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = null;
        ResponseWrapper responseWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        if(response instanceof HttpServletResponse) {
            responseWrapper = new ResponseWrapper((HttpServletResponse)response);
        }

        //获取请求中的流，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
        // 在chain.doFiler方法中传递新的request对象
        if(requestWrapper == null|| responseWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper,  response);
        }

    }

    @Override
    public void destroy() {

    }
}
