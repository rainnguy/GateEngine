package com.foofu.entity.system;

import java.util.List;
/**
 * 
* 类名称：Menu.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class Menu {
	/** 菜单ID */
	private String menu_id;
	/** 菜单名称 */
	private String menu_name;
	/** 菜单URL链接 */
	private String menu_url;
	/** 父级菜单ID */
	private String parent_id;
	/** 拍序号 */
	private String menu_order;
	/** 图标 */
	private String menu_icon;
	/** 菜单类型 */
	private String menu_type;
	/** 父级菜单信息 */
	private Menu parentMenu;
	/** 下级菜单信息 */
	private List<Menu> subMenu;
	/** 按钮菜单信息 */
	private List<Menu> butMenu;
	/** 菜单所属系统 */
	private String menu_sys;
	
	private boolean hasMenu = false;
	
	/**
	 * 菜单ID
	 * @return
	 */
	public String getMenu_id() {
		return menu_id;
	}
	
	/**
	 * 菜单ID
	 * @param menu_id
	 */
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	
	/**
	 * 菜单名称
	 * @return
	 */
	public String getMenu_name() {
		return menu_name;
	}
	
	/**
	 * 菜单名称
	 * @param menu_name
	 */
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	
	/**
	 * 菜单URL链接
	 * @return
	 */
	public String getMenu_url() {
		return menu_url;
	}
	
	/**
	 * 菜单URL链接
	 * @param menu_url
	 */
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	
	/**
	 * 父级菜单ID
	 * @return
	 */
	public String getParent_id() {
		return parent_id;
	}
	
	/**
	 * 父级菜单ID
	 * @param parent_id
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	/**
	 * 拍序号
	 * @return
	 */
	public String getMenu_order() {
		return menu_order;
	}
	
	/**
	 * 拍序号
	 * @param menu_order
	 */
	public void setMenu_order(String menu_order) {
		this.menu_order = menu_order;
	}
	
	/**
	 * 图标
	 * 只适用于一级菜单
	 * @return
	 */
	public String getMenu_icon() {
		return menu_icon;
	}
	
	/**
	 * 图标
	 * 只适用于一级菜单
	 * @param menu_icon
	 */
	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}
	
	/**
	 * 菜单类型
	 * @return
	 */
	public String getMenu_type() {
		return menu_type;
	}
	
	/**
	 * 菜单类型
	 * @param menu_type
	 */
	public void setMenu_type(String menu_type) {
		this.menu_type = menu_type;
	}
	
	/**
	 * 父级菜单信息
	 * @return
	 */
	public Menu getParentMenu() {
		return parentMenu;
	}
	
	/**
	 * 父级菜单信息
	 * @param parentMenu
	 */
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	
	/**
	 * 下级菜单信息
	 * @return
	 */
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	
	/**
	 * 下级菜单信息
	 * @param subMenu
	 */
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	
	/**
	 * 按钮菜单信息
	 * @return
	 */
	public List<Menu> getButMenu() {
		return butMenu;
	}
	
	/**
	 * 按钮菜单信息
	 * @param butMenu
	 */
	public void setButMenu(List<Menu> butMenu) {
		this.butMenu = butMenu;
	}

	/**
	 * 菜单所属系统
	 * @return
	 */
	public String getMenu_sys() {
		return menu_sys;
	}

	/**
	 * 菜单所属系统
	 * @param menu_sys
	 */
	public void setMenu_sys(String menu_sys) {
		this.menu_sys = menu_sys;
	}

	public boolean isHasMenu() {
		return hasMenu;
	}

	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	
}
