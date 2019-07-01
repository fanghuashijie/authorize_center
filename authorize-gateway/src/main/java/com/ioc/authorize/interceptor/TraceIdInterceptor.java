package com.ioc.authorize.interceptor;

import com.ioc.authorize.utils.LogUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *  TraceId拦截器
 *  负责在请求开始时设置TradeID，请求结束时清空TraceId，以便后续日志跟踪。
 *
 */
public class TraceIdInterceptor extends HandlerInterceptorAdapter {

    /**网关配置模块*/
//    private GatewayConf conf = null;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
         * 重置TraceId
         */
        LogUtil.resetTraceId();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*
         * 清空TraceId
         */
        LogUtil.clearTraceId();
    }
}