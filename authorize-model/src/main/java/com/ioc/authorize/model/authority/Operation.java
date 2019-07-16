package com.ioc.authorize.model.authority;

import com.ioc.authorize.model.common.BaseModel;

import java.io.Serializable;

public class Operation extends BaseModel implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 操作编码
     */
    private String code;

    /**
     * 拦截URL前缀
     */
    private String interceptUrlPrefix;

    /**
     * 父操作ID
     */
    private String pid;

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

    public String getInterceptUrlPrefix() {
        return interceptUrlPrefix;
    }

    public void setInterceptUrlPrefix(String interceptUrlPrefix) {
        this.interceptUrlPrefix = interceptUrlPrefix == null ? null : interceptUrlPrefix.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

}