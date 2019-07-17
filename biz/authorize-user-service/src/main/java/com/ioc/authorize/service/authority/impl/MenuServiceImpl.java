package com.ioc.authorize.service.authority.impl;

import com.ioc.authorize.dao.authority.IMenuDao;
import com.ioc.authorize.enums.RedisEnum;
import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.authority.IMenuService;
import com.ioc.authorize.service.common.IRedisService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

import static com.ioc.authorize.constant.BaseConstant.DEL_FLAG;
import static com.ioc.authorize.constant.BaseConstant.NO_DEL_FLAG;


/**
 * 菜单接口实现类
 */
@Service
public class MenuServiceImpl implements IMenuService {

    // redis中的菜单标识
    private final String redis_menu = "MENU";

    private Logger LOG =  Logger.getLogger( MenuServiceImpl.class);

    @Autowired
    private IMenuDao menuDao;

    @Autowired
    private IRedisService redisService;

    /**
     * 根据用户来查询菜单
     * @param user 登录用户
     * @return
     * @throws Exception
     */
    @Override
    public List<Menu> getAllByUser(User user) throws Exception {
        List<Menu> menuList = null;
        try {

            // 登录用户为空
            if (null == user){
                LogUtil.error( LOG, "登录的用户名为空" );
                throw new ArithmeticException( "登录的用户名为空" );
            }

            // 从redis中获取菜单
            menuList = (List<Menu>)redisService.get( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu ) );

            if ( null == menuList || menuList.size() ==0 ){
                menuList = menuDao.getAllByUser( user );
                if ( null == menuList || menuList.size() ==0 ) {
                    LogUtil.info( LOG, "菜单为空" );
                } else {
                    // 菜单存取到redis中
                    redisService.setString( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu), menuList );
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "菜单查询失败");
            throw new ArithmeticException( "菜单查询失败" );
        }
        return menuList;

    }

    /**
     * 查询菜单
     * @return
     * @throws Exception
     */
    @Override
    public List<Menu> getAll(Menu menu) throws Exception {
        List<Menu> menuList = null;
        try {
            // 从redis中获取菜单列表
            menuList = (List<Menu>)redisService.get( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu ) );

            // 从数据库中获取菜单列表
            if ( CollectionUtils.isEmpty(menuList) ){
                menuList = menuDao.getAll(menu);
                if ( null == menuList || menuList.size() ==0 ) {
                    LogUtil.info( LOG, "菜单为空" );
                } else {
                    // 菜单存取到redis中
                    redisService.setString( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu), menuList );
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "菜单查询失败");
            throw new ArithmeticException( "菜单查询失败" );
        }
        return menuList;
    }

    /**
     * 添加菜单
     * @param menu
     * @throws Exception
     */
    public void add(Menu menu, User user) throws Exception{
        try {
            String userNo = user.getUserNo();
            Date date = new Date();
            menu.setCreator( userNo );
            menu.setCreateTime( date );
            menu.setUpdator( userNo );
            menu.setUpdateTime( date );

            int result = menuDao.insert( menu );

            // 菜单添加成功，更新redis
            if (result > 0){
                LogUtil.info( LOG, "菜单添加成功" );
                Boolean updateRedisMenu = this.updateRedisMenu( null );
                if ( updateRedisMenu ) {
                    LogUtil.info( LOG, "更新redis中的菜单列表成功" );
                } else {
                    LogUtil.info( LOG, "更新redis中的菜单列表失败" );
                }
            }
        }catch (Exception e) {
            LogUtil.error(LOG, e, "菜单添加失败");
            throw new ArithmeticException( "菜单添加失败" );
        }
    }

    /**
     * 删除菜单
     * @param menu
     * @throws Exception
     */
    public void del(Menu menu) throws Exception{
        try {
            // 判断是否有子菜单
            Menu subMenu = new Menu();
            subMenu.setPid( menu.getId() );
            subMenu.setDelFlag( NO_DEL_FLAG );
            List<Menu> menuList = menuDao.getAll(subMenu);
            if ( CollectionUtils.isEmpty( menuList ) ) {
                LogUtil.error(LOG, "有子菜单不能进行删除");
                throw new ArithmeticException( "有子菜单不能进行删除" );
            }

            // 更新redis中的菜单
//            int result = menuDao.delete( menu );  //物理删除
//            if (result > 0){
//                LogUtil.info( LOG, "菜单删除成功" );
//                Boolean updateRedisMenu = this.updateRedisMenu( null );
//                if ( updateRedisMenu ) {
//                    LogUtil.info( LOG, "更新redis中的菜单列表成功" );
//                } else {
//                    LogUtil.info( LOG, "更新redis中的菜单列表失败" );
//                }
//            }

            // 逻辑删除
            menu.setDelFlag( DEL_FLAG );
            menuDao.updateById( menu );

        } catch (Exception e) {
            LogUtil.error(LOG, e, "菜单删除失败");
            throw new ArithmeticException( "菜单删除失败" );
        }
    }

    /**
     * 更新redis中的菜单列表
     * @param menu 查询菜单条件
     * @return
     */
    private Boolean updateRedisMenu(Menu menu) {
        Boolean result = Boolean.FALSE;
        List<Menu> menuList = menuDao.getAll( menu );
        if ( null == menuList || menuList.size() ==0 ) {
            LogUtil.info( LOG, "菜单为空" );
        } else {
            // 菜单存取到redis中
            redisService.setString( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu), menuList );
            result = Boolean.TRUE;
        }

        return result;
    }
}
