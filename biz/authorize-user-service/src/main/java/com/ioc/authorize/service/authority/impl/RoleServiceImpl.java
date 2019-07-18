package com.ioc.authorize.service.authority.impl;

import com.ioc.authorize.dao.authority.IRoleDao;
import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.authority.Role;
import com.ioc.authorize.service.authority.IRoleService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.UuidUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.ioc.authorize.constant.BaseConstant.NO_DEL_FLAG;

/**
* @Description: 角色接口实现类
* @Author: DeYi Peng
* @CreateDate: 2019/7/18 9:36
* @Version: 1.0
*/
@Service
public class RoleServiceImpl implements IRoleService {

    private Logger LOG = Logger.getLogger( getClass() );

    @Autowired
    private IRoleDao roleDao;

    /**
     * 获取所有角色
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> getAll(Role role) throws Exception {
        role.setDelFlag( NO_DEL_FLAG );
        List<Role> roleList = null;
        try {
            roleList = roleDao.getAll( role );
        } catch (Exception e) {
            LogUtil.error(LOG, e, "角色查询失败");
            throw new AuthorizeUserException( "角色查询失败" );
        }
        return roleList;
    }

    /**
     * 角色添加
     * @param role
     * @throws Exception
     */
    @Override
    public void addRole(Role role) throws Exception {
        Date date = new Date();
//            role.setCreator( userNo );
//            role.setCreateTime( date );
//            role.setUpdator( userNo );
//            role.setUpdateTime( date );

        role.setId( UuidUtil.getUuid() );
        role.setCreator( "test001" );
        role.setCreateTime( date );
        role.setUpdator( "test001" );
        role.setUpdateTime( date );
        role.setDelFlag( NO_DEL_FLAG );

        try {
            roleDao.insertSelective( role );
        } catch (Exception e) {
            LogUtil.error(LOG, e, "角色添加失败");
            throw new AuthorizeUserException( "角色添加失败" );
        }
    }

    /**
     * 根据ids批量删除
     * @param ids 例如：1，2，3，4
     * @throws Exception
     */
    @Override
    public void removeRole(String ids) throws Exception {
        if (StringUtils.isBlank( ids )){
            LogUtil.error(LOG, "参数为空");
            throw new AuthorizeUserException( "参数为空!" );
        }

        try {

            roleDao.deleteByIds(ids);
        } catch (Exception e) {
            LogUtil.error(LOG, e, "数据库删除菜单失败，ids:{0}" , ids);
            throw new AuthorizeUserException( "数据库删除菜单失败!" );
        }


    }
}
