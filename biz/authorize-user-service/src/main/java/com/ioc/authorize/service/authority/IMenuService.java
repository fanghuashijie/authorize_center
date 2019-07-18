package com.ioc.authorize.service.authority;

import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;
import com.ioc.authorize.vo.menu.MenuTreeVo;

import java.util.List;

/**
 * 菜单接口
 */
public interface IMenuService {

    /**
     * 根据用户来查询菜单
     * @param user 登录用户
     * @return
     * @throws Exception
     */
    List<Menu> getAllByUser(User user) throws Exception;

    /**
     * 查询菜单
     * @return
     * @throws Exception
     */
    List<MenuTreeVo> getAll(Menu menu) throws Exception;

    /**
     * 添加菜单
     * @param menu
     * @throws Exception
     */
    void addMenu(Menu menu, User user) throws Exception;

    /**
     * 删除菜单
     * @param menu
     * @throws Exception
     */
    void del(Menu menu) throws Exception;

    /**
     * 根据ids批量删除
     * @param ids 例如：1，2，3，4
     * @throws Exception
     */
    void removeMenu(String ids) throws Exception;
}
