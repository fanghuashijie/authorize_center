package com.ioc.authorize.service.common;

import org.springframework.data.redis.connection.DataType;

public interface IRedisService {

    /**
     * 设置redis缓存
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean set(String key, T value);

    /**
     * 获取redis缓存
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 获取redis缓存
     *
     * @param key
     * @param tClazz
     * @param <T>
     * @return
     */
    <T> T get(String key, Class<T> tClazz);

    /**
     * 获取对应的key在redis中的存取类型
     * @param key
     * @return
     */
    DataType getDataType(String key);
}
