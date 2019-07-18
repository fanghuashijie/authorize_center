package com.ioc.authorize.service.authority;

import com.ioc.authorize.model.authority.Role;

import java.util.List;

/**
* @Description: 角色接口类
* @Author: DeYi Peng
* @CreateDate: 2019/7/18 9:35
* @Version: 1.0
*/
public interface IRoleService {

    /**
     * 获取所有角色
     * @param role
     * @return
     * @throws Exception
     */
    List<Role> getAll(Role role) throws Exception;

    /**
     * 角色添加
     * @param role
     * @throws Exception
     */
    void addRole(Role role) throws Exception;

    /**
     * 根据ids批量删除
     * @param ids 例如：1，2，3，4
     * @throws Exception
     */
    void removeRole(String ids) throws Exception;

}
