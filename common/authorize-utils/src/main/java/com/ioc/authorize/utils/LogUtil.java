package com.ioc.authorize.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.UUID;

/**
 *
 * 日志工具
 * 该模块主要用于提供模板日志动态参数拼接，和日志等级过滤功能。
 *
 *
 * @author Eric
 * @version $Id: LogUtil.java, v 0.1 2017年06月30日 下午4:27 Eric Exp $
 */
public class LogUtil {

    private static final ThreadLocal<String> traceId = new ThreadLocal<String>();

    private static final String TRACE_LOG_FORMAT = " [{0}] {1}";

    /**
     * INFO
     * @param log  日志记录器
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void info(Logger log, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isInfoEnabled()) {
            log.info(format);
        }

    }

    /**
     * WARN
     * @param log  日志记录器
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void warn(Logger log, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isWarnEnabled()) {
            log.warn(format);
        }

    }

    /**
     * WARN
     * @param log  日志记录器
     * @param ex 异常信息.
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void warn(Logger log, Exception ex, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isWarnEnabled()) {
            log.warn(format, ex);
        }

    }

    /**
     * ERROR
     * @param log  日志记录器
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void error(Logger log, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isErrorEnabled()) {
            log.error(format);
        }

    }

    /**
     * ERROR
     * @param log  日志记录器
     * @param ex 异常信息.
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void error(Logger log, Exception ex, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isErrorEnabled()) {
            log.error(format, ex);
        }

    }

    /**
     * DEBUG
     * @param log  日志记录器
     * @param format 日志格式，采用Messageformat格式{0},{1}，{n}占位.
     * @param arg 日志参数.
     */
    public static void debug(Logger log, String format, Object... arg) {
        if (arg != null && arg.length >= BigInteger.ONE.intValue()) {
            format = MessageFormat.format(format, arg);
        }
        if (StringUtils.isNotBlank(traceId.get())) {
            format = MessageFormat.format(TRACE_LOG_FORMAT, traceId.get(), format);
        }
        if (log.isDebugEnabled()) {
            log.debug(format);
        }

    }

    /**
     * 清除TraceId。
     *
     * 注意：当前的TraceId的管理方式，存在任意模块错误调用本方法导致TraceID被重置的风险。
     * 暂时没有时间将TraceId做进一步封装，以确保业务代码只能读取而不能更新。所以各业务模块请勿调用该方法。
     */
    public static void clearTraceId() {
        traceId.remove();
    }

    /**
     * 重设TraceId。
     *
     * 注意：当前的TraceId的管理方式，存在任意模块错误调用本方法导致TraceID被重置的风险。
     * 暂时没有时间将TraceId做进一步封装，以确保业务代码只能读取而不能更新。所以各业务模块请勿调用该方法。
     */
    public static void resetTraceId() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.toUpperCase().replace("-", "");
        traceId.set(uuid);
    }

}