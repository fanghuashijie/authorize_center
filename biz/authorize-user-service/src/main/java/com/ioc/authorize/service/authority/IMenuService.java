package com.ioc.authorize.service.authority;

import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;

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
    List<Menu> getAll(Menu menu) throws Exception;

    /**
     * 添加菜单
     * @param menu
     * @throws Exception
     */
    void add(Menu menu, User user) throws Exception;

    /**
     * 删除菜单
     * @param menu
     * @throws Exception
     */
    void del(Menu menu) throws Exception;
}
