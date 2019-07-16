package com.ioc.authorize.model.authority;


import com.ioc.authorize.model.common.BaseModel;

import java.io.Serializable;

public class Permission extends BaseModel implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限类型(0：MENU菜单、1：OPERATION功能模块、2：FILE文件、3：ELEMENT页面元素的可见性控制)
     */
    private Integer type;

    /**
     * 关联ID（菜单表、页面元素表、文件表、功能操作表）
     */
    private String relationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }
}