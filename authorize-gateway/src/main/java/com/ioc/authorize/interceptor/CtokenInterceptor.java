package com.ioc.authorize.interceptor;


import com.ioc.authorize.utils.CtokenUtil;
import com.ioc.authorize.utils.HttpRequestUtil;
import com.ioc.authorize.utils.LogUtil;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *  Ctoken验证拦截器.
 *  仅允许包含授权令牌的的请求访问服务器，避免Ajax请求被滥用。
 *
 */
public class CtokenInterceptor extends HandlerInterceptorAdapter {

    private Logger LOG = Logger.getLogger( CtokenInterceptor.class);

    /**网关配置模块*/
//    private GatewayConf conf = null;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
         * Step1：配置开关验证.
         */
//        if (conf == null) {
//            conf = Application.getInstance().loadBeanById(GatewayConf.GATEWAY_CONF_BEAN_NAME, GatewayConf.class);
//        }
//        if (!Boolean.TRUE.toString().equalsIgnoreCase(conf.getCheckCtoken())) {
//            /*不检查Ctoken则直接跳过当前拦截器.*/
//            return true;
//        }

        /*
         * Step2：Ctoken验证.
         */
        LogUtil.info(LOG,"本次请求IP:{0}", HttpRequestUtil.getRemoteIp(request));
        if (CtokenUtil.validateCtoken(request)) {
            //Ctoken验证通过
            return true;
        }

        //输出提示，Ctoken验证失败.
//        Response resp = new Response(Response.CODE_FORBIDDEN, "Forbidden, Illegal Ctoken");
        response.getWriter().print("Forbidden, Illegal Ctoken");

        return false;
    }
}