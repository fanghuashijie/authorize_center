package com.ioc.authorize.constant;

/**
 * 常量类
 */
public abstract class BaseConstant {

    // 不是叶子节点
    public final static Integer MENU_NOT_LEAF = 0;

    // 是叶子节点
    public final static Integer MENU_IS_LEAF = 1;

    // 删除标识（已删除）
    public static final Integer DEL_FLAG = 0;

    // 删除标识（未删除）
    public static final Integer NO_DEL_FLAG = 1;

    /**
     * 系统的RedisKEY格式化模板.
     * Redis的KEY统一采用如下格式生成：{系统名}-{业务模块名}-{业务ID}, 以免多系统复用Redis的时候KEY重复。
     */
    public static final String REDIS_KEY_FORMAT = "authorize:{0}:{1}";

}
