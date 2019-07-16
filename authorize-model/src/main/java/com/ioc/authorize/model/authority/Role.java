package com.ioc.authorize.model.authority;


import com.ioc.authorize.model.common.BaseModel;

import java.io.Serializable;

public class Role extends BaseModel implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 角色标识
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否可以授权（0：不可以，1：可以）
     */
    private String authorize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize == null ? null : authorize.trim();
    }

}