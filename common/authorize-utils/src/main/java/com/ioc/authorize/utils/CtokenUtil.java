package com.ioc.authorize.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Ctoken工具类.
 *
 * 本类主要用于实现一个简易的可自检的Ctoken工具类，Ctoken主要实现如下两个需求：
 *  1.创建的Ctoken需要绑定客户端IP，避免传统的HTTP会话管理，因为Cookie复制，导致的用户信息被他人越权访问。
 *  2.确保Ctoken能够自检，在不需要操作数据库或服务端SESSION数据的情况下，能够快速识别那些恶意伪造的Ctoken值。
 *
 * Ctoken的生成生成策略如下：
 *    Ctoken格式：由字母或数字组成的36位字符串。(32位UUID + 4位校验位)
 *    4位校验位生成规则:
 *              第1位校验位：  (HashCode(SubStr(UUID, 0,8))+ int(clientIp))%16 的16进制结果
 *              第2位校验位：  (HashCode(SubStr(UUID, 8,16))+ int(clientIp))%16 的16进制结果
 *              第2位校验位：  (HashCode(SubStr(UUID,16,24))+ int(clientIp))%16 的16进制结果
 *              第2位校验位：  (HashCode(SubStr(UUID,24,32))+ int(clientIp))%16 的16进制结果
 *
 */
public class CtokenUtil {

    /**CTOKEN的Cookie名称*/
    public static final String CTOKEN_COOKIE_NAME = "COM.IOC.CTOKEN";

    public static final String CTOKEN_HEADER_NAME = "COM_IOC_CTOKEN";

    /**CTOKEN在Session中的key*/
    public static final String CTOKEN_SESSION_KEY = "CTOKEN";

    private static final Logger LOG = Logger.getLogger( CtokenUtil.class);

    /**
     * 创建一个Ctoken .
     * @param request
     * @param response
     * @return
     */
    public static String sendCtoken(HttpServletRequest request, HttpServletResponse response) {
        /**
         * Step1:创建Ctoken.
         */
        String uuid = UuidUtil.getUuid().toUpperCase();
        int ipHashCode = HttpRequestUtil.getRemoteIp(request).hashCode();
        StringBuffer str = new StringBuffer(uuid);
        String validateChar = null;

        for (int i = 0; i < 4; i++) {
            validateChar = Integer.toHexString((Math.abs(uuid.substring(i * 8, (i + 1) * 8 - 1).hashCode() + ipHashCode)) % 16);
            str = str.append(validateChar);
        }

        String ctoken = str.toString().toUpperCase();

        /**
         * Step2: 写入客户端
         */
        sendCtoken(response,ctoken);
        return ctoken;
    }


    /**
     * 写入客户端
     * @param response
     * @param ctoken
     * @return
     */
    public static void sendCtoken(HttpServletResponse response, String ctoken) {
        // 将Ctoken写入客户端Cookie.
        Cookie cookie = new Cookie(CTOKEN_COOKIE_NAME, ctoken);
        cookie.setPath("/");
//        cookie.setMaxAge(3600);  // 单位秒
        response.addCookie(cookie);

        response.setHeader(CTOKEN_HEADER_NAME, ctoken);
    }

    /**
     * 验证Ctoken是否合法
     *
     * @param request 待验证的HTTP请求.
     * @return
     *  1. Ctoken存在并且无法通过格式自检则返回False，标示Ctoken验证失败.
     *  2. 如果Ctoken不存在，或 Ctoken存在并且能够通过格式验证，则返回True，标示Ctoken合法。
     */
    public static boolean validateCtoken(HttpServletRequest request) {
        String ctoken = HttpRequestUtil.getCookie(request, CTOKEN_COOKIE_NAME);
        if (ctoken == null) {  //Ctoken不存在.
            return true;
        } else {
            //Ctoken存在.
            if (ctoken.length() == 36) {
                //循环检查每一个校验位.
                String validateChar = null;
                int ipHashCode = HttpRequestUtil.getRemoteIp(request).hashCode();
                for (int i = 0; i < 4; i++) {
                    validateChar = Integer.toHexString((Math.abs(ctoken.substring(i * 8, (i + 1) * 8 - 1).hashCode() + ipHashCode)) % 16);
                    if (!ctoken.substring(32 + i, 33 + i).equalsIgnoreCase(validateChar)) {
                        //如果任意一个校验位不匹配，则直接返回False .
                        return false;
                    }
                }
                //所有校验位均验证通过之后，返回True，标示验证通过
                return true;
            }
            //默认返回验证失败 false.
            return false;
        }
    }

    /**
     * 从HTTP请求中获取Ctoken.
     *
     * @param request 待提取Ctoken的HTTP请求
     * @return 从HTTP请求中获取Ctoken.
     *
     */
    public static String getCtoken(HttpServletRequest request) {
        String ctoken = HttpRequestUtil.getCookie(request, CTOKEN_COOKIE_NAME);
        if (ctoken != null && validateCtoken(request)) {
            //Ctoken存在，并且格式正确，则返回该Ctoken.
            return ctoken;
        }

        ctoken = request.getHeader(CTOKEN_HEADER_NAME);

        if (StringUtils.isNotBlank(ctoken)) {
            return ctoken;
        }

        ctoken = request.getParameter(CTOKEN_COOKIE_NAME);
        if(StringUtils.isNotBlank( ctoken ))
        {
            //注从HTTP请求参数中获取Ctoken，主要是为了方便模拟压测，正常业务逻辑部分进入该流程.
            LogUtil.info(LOG,"从HTTP请求参数中获取Ctoken:{0}",ctoken);
            return ctoken;
        }
        return null;
    }
}
