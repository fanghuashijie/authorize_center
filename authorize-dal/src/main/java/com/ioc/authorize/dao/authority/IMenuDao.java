package com.ioc.authorize.dao.authority;


import com.ioc.authorize.model.authority.Menu;
import com.ioc.authorize.model.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuDao {

    /**
     * 根据ID删除菜单
     */
    int deleteById(String id);

    /**
     * 删除菜单
     */
    int delete(Menu menu);

    /**
     * 添加菜单
     */
    int insert(Menu menu);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_menu
     *
     * @mbg.generated
     */
    int insertSelective(Menu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_menu
     *
     * @mbg.generated
     */
    Menu selectByPrimaryKey(String id);

    /**
     * 根据ID更新菜单
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
     * @return
     */
    List<Menu> getAll(Menu menu);
}