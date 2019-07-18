package com.ioc.authorize.model.authority;

import com.ioc.authorize.model.common.BaseModel;

import java.io.Serializable;


public class Menu extends BaseModel implements Serializable {
    /**
     * ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单标识
     */
    private String sn;

    /**
     * URL
     */
    private String url;

    /**
     * 父级ID（最顶级为0）
     */
    private String pid;

    /**
     * 排序（同级中的顺序，0 - N 从上到下）
     */
    private Integer seq;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 叶子（0：不是叶子，1：叶子）
     */
    private Integer isLeaf;


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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

}
