package com.ioc.authorize.service.user;


import com.ioc.authorize.model.user.User;

import java.util.List;

/**
 * 用户接口
 */
public interface IUserService {

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    public User getUserById(String id) throws Exception;

    /**
     * 根据userNo查询用户
     * @param userNo 用户编码
     * @return
     * @throws Exception
     */
    public User getUserByUserNo(String userNo) throws Exception;

    /**
     * 添加用户
     * @param user 用户
     * @throws Exception
     */
    public int setUser(User user) throws Exception;

    /**
     * 按条件筛选出用户列表
     * @param user 用户
     * @return
     * @throws Exception
     */
    public List<User> getAllByUser(User user) throws Exception;
}
