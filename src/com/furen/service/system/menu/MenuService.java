package com.furen.service.system.menu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.system.Menu;
import com.furen.util.Const;
import com.furen.util.PageData;

@Service("menuService")
public class MenuService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void deleteMenuById(String menu_id) throws Exception {
		dao.save("MenuMapper.deleteMenuById", menu_id);
		
	}

	public PageData getMenuById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.getMenuById", pd);
		
	}

	//取最大id
	public PageData findMaxId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("MenuMapper.findMaxId", pd);
		
	}
	
	public List<Menu> listAllParentMenu() throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAllParentMenu", null);
		
	}

	public void saveMenu(Menu menu) throws Exception {
		if(menu.getMenu_id()!=null && menu.getMenu_id() != ""){
			//dao.update("MenuMapper.updateMenu", menu);
			dao.save("MenuMapper.insertMenu", menu);
		}else{
			dao.save("MenuMapper.insertMenu", menu);
		}
	}

	/**
	 * 角色管理 -> 加载菜单
	 * @return
	 */
	public List<Menu> findAllMenuForRole(Map<String,Object> map) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.findAllMenuForRole", map);
	}
	
	public List<Menu> listSubMenuByParentId(String parentId) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listSubMenuByParentId", parentId);
		
	}
		
	/**
	 * 查询所有菜单
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllMenu() throws Exception {
		//查询一级菜单
		List<Menu> rl = this.listAllParentMenu();
		for(Menu menu : rl){
			//查询二级菜单
			List<Menu> subList = this.listSubMenuByParentId(menu.getMenu_id());
			menu.setSubMenu(subList);
			if(null != subList && subList.size() > 0) {
				for(Menu subMenu : subList) {
					//查询按钮菜单
					List<Menu> btnList = this.listSubMenuByParentId(subMenu.getMenu_id());
					subMenu.setButMenu(btnList);
				}
			}
		}
		return rl;
	}

	public List<Menu> listAllSubMenu() throws Exception{
		return (List<Menu>) dao.findForList("MenuMapper.listAllSubMenu", null);
		
	}
	
	/**
	 * 编辑
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.updateMenu", pd);
	}
	/**
	 * 保存菜单图标 (顶部菜单)
	 */
	public PageData editicon(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editicon", pd);
	}
	
	/**
	 * 更新子菜单类型菜单
	 */
	public PageData editType(PageData pd) throws Exception {
		return (PageData)dao.findForObject("MenuMapper.editType", pd);
	}
	
	/**
	 * 根据用户角色获得权限菜单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Menu> listAllParentMenuByUserRole(PageData pd) throws Exception {
		//一级菜单
		List<Menu> parentMenuList = (List<Menu>) dao.findForList("MenuMapper.listAllParentMenuByUserRole", pd);
		if(null != parentMenuList && parentMenuList.size() > 0) {
			for(Menu menu : parentMenuList) {
				pd.put("parent_id", menu.getMenu_id());
				List<Menu> subMenuList = (List<Menu>) dao.findForList("MenuMapper.listAllParentMenuByUserRole", pd);
				menu.setSubMenu(subMenuList);
			}
		}
		return parentMenuList;
	}
	
	/**
	 * 根据二级菜单ID、角色ID 获得角色对应菜单的按钮权限
	 * @return
	 */
	public List<Menu> listBtnMenuByParentIdAndRoleId(PageData pd) throws Exception {
		return (List<Menu>) dao.findForList("MenuMapper.listAllParentMenuByUserRole", pd);
	}
}
