package com.ioc.authorize.enums;

/**
 *
 * 使用Redis的业务模块列表,在这统一定义模块名，避免系统内部各模块RedisKey冲突.
 *
 */
public enum RedisEnum {
    // Ctoken模块，保存Coken和UID的映射关系
    CTOKEN,
    // SESSION模块，保存Coken和Session的映射关系
    SESSION,
    // 菜单-后台管理模块
    MENU,
}
