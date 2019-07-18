package com.ioc.authorize.vo.menu;

import com.ioc.authorize.model.authority.Menu;

import java.io.Serializable;
import java.util.List;

/**
* @Description: 菜单树
* @Author: DeYi Peng
* @CreateDate: 2019/7/17 13:15
* @Version: 1.0
*/
public class MenuTreeVo implements Serializable {

    /**
     * 菜单
     */
    private Menu menu;

    /**
     * 子菜单
     */
    private List<MenuTreeVo> children;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<MenuTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeVo> children) {
        this.children = children;
    }
}
