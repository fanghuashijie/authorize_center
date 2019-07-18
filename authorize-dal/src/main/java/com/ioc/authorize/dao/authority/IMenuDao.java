package com.ioc.authorize.dao.authority;


import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuDao {

    /**
     * 根据ID删除菜单
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据ID删除菜单
     * @param ids 例如：1，2，3，4
     * @return
     */
    int deleteByIds(@Param("ids") String ids);


    /**
     * 删除菜单
     * @param menu
     * @return
     */
    int delete(Menu menu);


    /**
     * 添加菜单
     * @param menu
     * @return
     */
    int insert(Menu menu);

    /**
     * 选择性添加菜单列
     * @param record
     * @return
     */
    int insertSelective(Menu record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Menu selectByPrimaryKey(String id);

    /**
     * 根据ID更新菜单
     * @param menu
     * @return
     */
    int updateById(Menu menu);

    /**
     * 根据当前用户查询菜单
     * @param user
     * @return
     */
    List<Menu> getAllByUser(User user);

    /**
     * 查询所有菜单
     * @param menu
     * @return
     */
    List<Menu> getAll(Menu menu);

    /**
     * 根据pids查询菜单
     * @param pids 例如：1，2，3
     * @return
     */
    List<Menu> getMenuByPids(@Param( "pids" ) String pids);
}