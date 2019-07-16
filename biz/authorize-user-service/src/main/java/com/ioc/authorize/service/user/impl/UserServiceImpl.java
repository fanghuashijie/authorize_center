package com.ioc.authorize.service.user.impl;


import com.ioc.authorize.dao.user.IUserDao;
import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.user.IUserService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.ioc.authorize.constant.BaseConstant.NO_DEL_FLAG;

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

        if (StringUtils.isBlank( userNo )){
            return null;
        }
        try {
//            User user = new User();
//            user.setDelFlag(NO_DEL_FLAG);
            User user = userDao.getUserByUserNo( userNo );

            return user;
        } catch (Exception e) {
            LogUtil.error(LOG, e, "用户查询失败");
            throw new AuthorizeUserException( "用户查询失败" );
        }
    }

    /**
     * 添加用户
     * @param user 用户
     * @throws Exception
     */
    @Override
    public int addUser(User user) throws Exception {
        int result = 0;
        user.setId( UuidUtil.getUuid() );
        user.setDelFlag(NO_DEL_FLAG);

        Date today = new Date();
        user.setCreator( user.getUserNo() );
        user.setCreateTime(today);
        user.setUpdator( user.getUserNo() );
        user.setUpdateTime(today);
        try {
            result = userDao.insert(user);
        } catch (Exception e) {
            LogUtil.error(LOG, e, "用户添加失败");
            throw new AuthorizeUserException( "用户添加失败" );
        }
        return result;
    }

    /**
     * 按条件筛选出用户列表
     * @param user 用户
     * @return
     * @throws Exception
     */
    @Override
    public List<User> getAllByUser(User user) throws Exception {
        List<User> list;

        try {
            list = userDao.getAllByUser( user );
        } catch (Exception e) {
            LogUtil.error(LOG, e, "用户查询失败");
            throw new AuthorizeUserException( "用户查询失败" );
        }
        return list;
    }
}
