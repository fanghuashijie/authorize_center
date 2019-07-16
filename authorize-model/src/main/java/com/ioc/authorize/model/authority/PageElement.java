package com.ioc.authorize.model.authority;


import com.ioc.authorize.model.common.BaseModel;

import java.io.Serializable;

public class PageElement extends BaseModel implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 元素名称
     */
    private String name;

    /**
     * 元素编码
     */
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

}