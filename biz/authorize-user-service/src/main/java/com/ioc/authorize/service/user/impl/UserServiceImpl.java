package com.ioc.authorize.service.user.impl;


import com.ioc.authorize.dao.user.IUserDao;
import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.user.IUserService;
import com.ioc.authorize.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    private Logger LOG =  Logger.getLogger( UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    @Override
    public User getUserById(String id) throws Exception {
        User user = null;
        try {
            if ( StringUtils.isNotBlank( id ) ){
                user = userDao.getUserById( id );
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "用户查询失败");
            throw new AuthorizeUserException( "用户查询失败" );
        }
        return user;
    }


    /**
     * 根据userNo查询用户
     * @param userNo 用户编码
     * @return
     * @throws Exception
     */
    @Override
    public User getUserByUserNo(String userNo) throws Exception {
        User user = null;
        try {
            if ( StringUtils.isNotBlank( userNo ) ){
                user = userDao.getUserByUserNo( userNo );
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "用户查询失败");
            throw new AuthorizeUserException( "用户查询失败" );
        }
        return user;
    }

    /**
     * 添加用户
     * @param user 用户
     * @throws Exception
     */
    @Override
    public int setUser(User user) throws Exception {
        Date today = new Date();
        user.setCreateTime(today);
        user.setUpdateTime(today);
       return null == user ? 0 : userDao.insert(user);
    }

    /**
     * 按条件筛选出用户列表
     * @param user 用户
     * @return
     * @throws Exception
     */
    @Override
    public List<User> getAllByUser(User user) throws Exception {
        return null == user ? null : userDao.getAllByUser(user);
    }
}
