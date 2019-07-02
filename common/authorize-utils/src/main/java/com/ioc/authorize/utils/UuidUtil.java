package com.ioc.authorize.utils;

import java.util.UUID;

/**
* @Description: uuid生成工具
* @Author: DeYi Peng
* @CreateDate: 2019/7/2 10:55
* @Version: 1.0
*/
public class UuidUtil {

    /**
     * 获取uuid的值（默认32位）
     * @return
     */
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace( "-", "" );
        return uuidStr;
    }

    /**
     * 获取uuid的值（默认32位）
     * @param name 参数
     * @return
     */
    public static String getUuid( String name ){
        UUID uuid = UUID.fromString(name);
        String uuidStr = uuid.toString().replace( "-", "" );
        return null;
    }

}
