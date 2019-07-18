package com.ioc.authorize.dao.authority;

import com.ioc.authorize.model.authority.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao {

    /**
     * 根据主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);

    /**
     * 根据ID删除菜单
     * @param ids 例如：1，2，3，4
     * @return
     */
    int deleteByIds(@Param("ids") String ids);

    /**
     * 插入角色
     * @param record
     * @return
     */
    int insert(Role record);

    /**
     * 可选的插入角色
     * @param record
     * @return
     */
    int insertSelective(Role record);

    /**
     * 根据ID查询角色
     * @param id
     * @return
     */
    Role selectByPrimaryKey(String id);

    /**
     * 根据ID查询角色
     * @param record
     * @return
     */
    List<Role> getAll(Role record);

    /**
     * 根据主键更新角色
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 根据主键选择性的更新角色
     * @param record
     * @return
     */
    int updateByPrimaryKey(Role record);
}