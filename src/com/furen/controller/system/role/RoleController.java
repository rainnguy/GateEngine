package com.furen.controller.system.role;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.furen.controller.base.BaseController;
import com.furen.entity.Page;
import com.furen.entity.system.Menu;
import com.furen.entity.system.Role;
import com.furen.service.system.log.LogService;
import com.furen.service.system.menu.MenuService;
import com.furen.service.system.role.RoleService;
import com.furen.util.AppUtil;
import com.furen.util.Const;
import com.furen.util.Jurisdiction;
import com.furen.util.PageData;
/** 
 * 类名称：RoleController
 * 创建人：xiajj
 * 创建时间：2015年11月10日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role.do"; //菜单地址(权限用)
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="logService")
	private LogService logService;
	
	/**
	 * 列表
	 */
	@RequestMapping
	public ModelAndView list(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String name = pd.getString("name");
		pd.put("name", name);
		page.setPd(pd);
		List<PageData> roleList = roleService.listAllRoles(page);				//列出所有角色
		//获得页面按钮
		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.setViewName("system/role/role_list");
//		//插入日志
//		logService.insertOneLog(menuUrl,
//								Const.LOG_SYSTEM_TYPE_DATA,
//								Const.LOG_OPERATOR_TYPE_SELECT,
//								"查看所有角色");
		return mv;
	}
	
	/**
	 * 请求角色菜单授权页面
	 */
	@RequestMapping(value="/toRoleInfo")
	public String auth(@RequestParam String id,Model model)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//角色信息
			Role role = new Role();
			Map<String,Object> paramMap = new HashMap<String,Object>();
			//所有菜单
			List<Menu> menuList = menuService.findAllMenuForRole(paramMap);
			if(!id.isEmpty() && !"0".equals(id) && !"undefined".equals(id)) {
				//角色菜单
				paramMap.put("role_id", id);
				List<Menu> roleMenuList = menuService.findAllMenuForRole(paramMap);
				if(null != roleMenuList && roleMenuList.size() > 0) {
					for(Menu parentMenu : menuList) {
						for(Menu roleMenu : roleMenuList) {
							if(parentMenu.getMenu_id().equals(roleMenu.getMenu_id())) {
								parentMenu.setHasMenu(true);
								break;
							}
						}
					}
				}
				//查询角色信息
				role = roleService.getRoleById(id);
			}
			JSONArray json_arr = new JSONArray();
			for(Menu menu : menuList) {
				JSONObject json_obj = new JSONObject();
				json_obj.put("id", menu.getMenu_id());
				json_obj.put("pId",menu.getParent_id());
				json_obj.put("name",menu.getMenu_name());
				json_obj.put("resLevel", menu.getMenu_type());
				if(menu.getMenu_type().equals("1")) {
					json_obj.put("open", "true");
				} else {
					json_obj.put("open", "false");
				}
				json_obj.put("checked", menu.isHasMenu());
				json_arr.add(json_obj);
			}
			String json = json_arr.toString();
			model.addAttribute("role",role);
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", id);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return "authorization";
	}
	/**
	 * 保存角色菜单权限
	 */
	@RequestMapping(value="/auth/save")
	public ModelAndView saveAuth(PrintWriter out)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String logRemark = "";
		//日志操作类型  默认新增
		int logType = Const.LOG_OPERATOR_TYPE_INSERT;
		try{
			//session中取得该用户所属机构
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			if(null == pd.get("id") || pd.get("id").toString().isEmpty()
				|| "undefined".equals(pd.get("id").toString())) {
				//新增
				//保存角色信息
				Role role = new Role();
				role.setLevel(Integer.parseInt(pd.getString("level")));
				role.setName(pd.getString("name"));
				role.setRemark(pd.getString("remark"));
				this.roleService.add(role);
				pd.put("id", role.getId());
				logRemark = "新增角色："+role.getName();
			} else {
				//编辑
				this.roleService.edit(pd);
				logRemark = "编辑角色："+pd.getString("name");
				logType = Const.LOG_OPERATOR_TYPE_UPDATE;
			}
			//保存角色菜单信息
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", session.getAttribute(Const.SESSION_USERNAME).toString());
			paramMap.put("role_id", pd.get("id"));
			paramMap.put("menuIds", pd.get("menuIds"));
			this.roleService.saveRoleMenu(paramMap);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("save_result");
		//插入日志
		logService.insertOneLog(menuUrl,
								Const.LOG_SYSTEM_TYPE_DATA,
								logType,
								logRemark);
		return mv;
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			roleService.deleteRole(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		//插入日志
		logService.insertOneLog(menuUrl,
								Const.LOG_SYSTEM_TYPE_DATA,
								Const.LOG_OPERATOR_TYPE_DELETE,
								"删除角色："+pd.get("name").toString());
	}
	
	/**
	 * 判断角色名称是否存在
	 * @return
	 */
	@RequestMapping(value="/hasRoleName")
	@ResponseBody
	public Object hasRoleName() {
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(roleService.hasRoleName(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
