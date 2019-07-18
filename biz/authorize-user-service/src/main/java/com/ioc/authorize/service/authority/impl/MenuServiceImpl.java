package com.ioc.authorize.service.authority.impl;

import com.ioc.authorize.dao.authority.IMenuDao;
import com.ioc.authorize.enums.RedisEnum;
import com.ioc.authorize.exceptions.AuthorizeUserException;
import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.service.authority.IMenuService;
import com.ioc.authorize.service.common.IRedisService;
import com.ioc.authorize.utils.LogUtil;
import com.ioc.authorize.utils.RedisUtil;
import com.ioc.authorize.utils.UuidUtil;
import com.ioc.authorize.vo.menu.MenuTreeVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.ioc.authorize.constant.BaseConstant.*;


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
                throw new AuthorizeUserException( "登录的用户名为空" );
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
            throw new AuthorizeUserException( "菜单查询失败" );
        }
        return menuList;

    }

    /**
     * 查询菜单
     * @return
     * @throws Exception
     */
    @Override
    public List<MenuTreeVo> getAll(Menu menu) throws Exception {
        List<MenuTreeVo> menuTreeVoList = null;
        try {
            // 从redis中获取菜单列表
            menuTreeVoList = redisService.get( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu ) );

            // 从数据库中获取菜单列表
            if ( CollectionUtils.isNotEmpty(menuTreeVoList) ){
                List<Menu> menuList = menuDao.getAll( menu );
                if ( CollectionUtils.isEmpty(menuList) ) {
                    LogUtil.info( LOG, "菜单为空" );
                } else {
                    // 将菜单列表封装成菜单树
                    // step1. 查询所有的根节点，也就是pid为空
                    List<MenuTreeVo> sortMenuTreeVoList = new LinkedList();
                    menuList.forEach( temp -> {
                        String pid = temp.getPid();
                        if (StringUtils.isBlank( pid )){
                            MenuTreeVo menuTreeVo = new MenuTreeVo();
                            menuTreeVo.setMenu( temp );
                            sortMenuTreeVoList.add( menuTreeVo );
                        }
                    } );

                    // step2. 递归查询子节点
                    sortMenuTreeVoList.forEach( menuTreeVo -> {
                        this.getSubMenuTree( menuList, menuTreeVo );
                    } );

                    // step3. 递归排序
                    this.sortMenuTree(sortMenuTreeVoList);

                    // 菜单存取到redis中
                    redisService.setString( RedisUtil.formatRedisKey( RedisEnum.MENU, redis_menu), sortMenuTreeVoList );

                    menuTreeVoList = sortMenuTreeVoList;
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOG, e, "菜单查询失败");
            throw new AuthorizeUserException( "菜单查询失败" );
        }
        return menuTreeVoList;
    }

    /**
     * 添加菜单
     * @param menu
     * @throws Exception
     */
    public void addMenu(Menu menu, User user) throws Exception{
        try {
//            String userNo = user.getUserNo();
            Date date = new Date();
//            menu.setCreator( userNo );
//            menu.setCreateTime( date );
//            menu.setUpdator( userNo );
//            menu.setUpdateTime( date );

            menu.setId( UuidUtil.getUuid() );
            menu.setCreator( "test001" );
            menu.setCreateTime( date );
            menu.setUpdator( "test001" );
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
            throw new AuthorizeUserException( "菜单添加失败" );
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
                throw new AuthorizeUserException( "有子菜单不能进行删除" );
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
            throw new AuthorizeUserException( "菜单删除失败" );
        }
    }

    /**
     * 根据ids批量删除
     * @param ids 例如：1，2，3，4
     * @throws Exception
     */
    public void removeMenu(String ids) throws Exception{
        if (StringUtils.isBlank( ids )){
            LogUtil.error(LOG, "参数为空");
            throw new AuthorizeUserException( "参数为空!" );
        }

        try {
            // 判断是否有子菜单
            List<Menu> menuByPids = menuDao.getMenuByPids( ids );

            if (CollectionUtils.isNotEmpty( menuByPids )){
                throw new AuthorizeUserException( "有子菜单！" );
            }

            menuDao.deleteByIds( ids );
        } catch (Exception e) {
            LogUtil.error(LOG, e, "数据库删除菜单失败，ids:{0}" , ids);
            throw new AuthorizeUserException( "数据库删除菜单失败!" );
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

    /**
     * 获取子菜单
     * @param menuList 菜单列表
     * @param menuTreeVo 当前菜单
     * @return
     */
    private void getSubMenuTree(List<Menu> menuList,MenuTreeVo menuTreeVo) {
        if (null != menuTreeVo){
            String id = menuTreeVo.getMenu().getId();
            List<MenuTreeVo> children = menuTreeVo.getChildren();
            if (CollectionUtils.isEmpty( children )) {
                children = new LinkedList();
            }

            List<MenuTreeVo> sChildren = children;
            menuList.forEach( temp -> {
                String pid = temp.getPid();
                if (StringUtils.isNotBlank( pid ) && pid.equals( id )) {
                    MenuTreeVo menuVo = new MenuTreeVo();
                    menuVo.setMenu( temp );
                    sChildren.add( menuVo );

                    // 非叶子节点递归
                    if (temp.getIsLeaf() == MENU_NOT_LEAF) {
                        this.getSubMenuTree( menuList, menuVo);
                    }
                }

            } );
            menuTreeVo.setChildren( sChildren );
        }
    }

    /**
     * 菜单树进行排序
     * @param sortMenuTreeVoList
     */
    public void sortMenuTree(List<MenuTreeVo> sortMenuTreeVoList){
        Collections.sort( sortMenuTreeVoList, new Comparator<MenuTreeVo>(){
            @Override
            public int compare(MenuTreeVo vo1, MenuTreeVo vo2) {
                Menu vo1Menu = vo1.getMenu();
                Menu vo2Menu = vo2.getMenu();
                Integer vo1MenuSeq = vo1Menu.getSeq();
                Integer vo2MenuSeq = vo2Menu.getSeq();
                int seq1 = null == vo1MenuSeq ? 0 : vo1MenuSeq.intValue();
                int seq2 = null == vo2MenuSeq ? 0 : vo2MenuSeq.intValue();
                return  (seq1 - seq2);
            }
        });

        sortMenuTreeVoList.forEach( temp -> {
            List<MenuTreeVo> children = temp.getChildren();
            if (CollectionUtils.isNotEmpty( children )){
                this.sortMenuTree(children);
            }
            temp.setChildren( children );
        } );
    }

}
