package com.ioc.authorize.service.common;

import org.springframework.data.redis.connection.DataType;

public interface IRedisService {

    /**
     * 设置redis缓存（字符串类型）
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean setString(String key, T value);

    /**
     * 设置有效时间的redis缓存（字符串类型）
     * @param key
     * @param value
     * @param expireSeconds 秒
     * @param <T>
     * @return
     */
    public <T> Boolean setString(String key, T value, long expireSeconds);

    /**
     * 获取redis缓存
     * @param key
     * @return
     */
    public <T> T get(String key);

    /**
     * 获取redis缓存
     *
     * @param key
     * @param tClazz
     * @param <T>
     * @return
     */
//    public <T> T get(String key, Class<T> tClazz);

    /**
     * 获取对应的key在redis中的存取类型
     * @param key
     * @return
     */
    public DataType getDataType(String key);

    /**
     * redis清除
     * @param key
     */
    public void delete(String key);
}
