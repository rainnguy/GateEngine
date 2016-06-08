package com.foofu.controller.system.department;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.Page;
import com.foofu.entity.system.Department;
import com.foofu.entity.system.Dictionary;
import com.foofu.entity.system.POSMachine;
import com.foofu.entity.system.Region;
import com.foofu.entity.system.User;
import com.foofu.service.system.department.DepartmentService;
import com.foofu.service.system.dictionaries.DictionaryService;
import com.foofu.service.system.log.LogService;
import com.foofu.service.system.region.RegionService;
import com.foofu.util.AppUtil;
import com.foofu.util.Const;
import com.foofu.util.Jurisdiction;
import com.foofu.util.PageData;

/** 
 * 类名称：DepartmentController
 * 创建人：xiajj 
 * 创建时间：2015年11月10日
 * @version
 */
@Controller
@RequestMapping(value="/department")
public class DepartmentController extends BaseController {
	String menuUrl = "department/listDepartment.do"; //机构地址(权限用)
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	
	@Resource(name="logService")
	private LogService logService;
	
	@Resource(name="regionService")
	private RegionService regionService;
	
	/** 机构列表顶部导航 */
	public List<Department> listDepForNavigation;
	
	/**
	 * 机构列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listDepartment")
	public ModelAndView listDepartment(Page page) throws Exception {
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//session中取得该用户所属机构
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		//获得查询条件
		String keyValue = pd.getString("keyValue");
		String parentId = pd.getString("parentId");
		String id = pd.getString("id");
		//查询父级机构
		Department parentDepartment = new Department();
		if(null == parentId) {
			pd.put("showAddForPage", false);
		} else {
			pd.put("showAddForPage", true);
			pd.put("id", parentId);
			//parentId 不为空，查询父级机构信息
			parentDepartment = this.departmentService.findDepartmentById(pd);
		}
		if(null == id) {
			id = String.valueOf(user.getDepartmentId());
		}
		if(parentDepartment.getDepartmentType() == 3) {
			//父级机构类型为3，表示加油站，则下一列表为pos机信息
			//查询pos机列表
			pd.put("departmentId", parentId);
			page.setPd(pd);
			List<PageData>	posList = departmentService.listPosByStationId(page);
			//获得页面按钮
			Jurisdiction.buttonJurisdictionForPage("department/listPOSMachine.do",pd);
			mv.setViewName("system/department/pos/pos_list");
			mv.addObject("posList", posList);
			mv.addObject("pd", pd);
		} else {
			pd.put("id", id);
			pd.put("parentId", parentId);
			pd.put("keyValue", keyValue);
			page.setPd(pd);
			//获得机构列表
			List<PageData>	departmentList = departmentService.listDepartmentsById(page);
			//获得页面按钮
			Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
			mv.setViewName("system/department/department_list");
			mv.addObject("departmentList", departmentList);
			mv.addObject("pd", pd);
		}
		if(null == parentId) {
			pd.put("parentId", user.getDepartmentId());
		}
		if(null != pd.get("parentId")) {
			id = String.valueOf(pd.get("parentId"));
		}
		//获得顶部导航列表
		listDepForNavigation = new ArrayList<Department>();
		queryListDepForNavigation(Integer.parseInt(id),user.getDepartmentId());
		Collections.reverse(listDepForNavigation);
		mv.addObject("listDepForNavigation",listDepForNavigation);
		return mv;
	}
	
	/**
	 * 获得顶部导航列表
	 * @param parentId
	 */
	public void queryListDepForNavigation(int id,int userDepartmentId) {
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			Department department = departmentService.queryListDepForNavigation(paramMap);
			if(department != null){
				listDepForNavigation.add(department);
				if(department.getId() != userDepartmentId) {
					int parentId = department.getParentId();
					this.queryListDepForNavigation(parentId,userDepartmentId);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	
	/**
	 * 去机构信息页面
	 */
	@RequestMapping(value="/goDepartmentInfo")
	public ModelAndView goDepartmentInfo()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//省份list
		List<Region> provinceList = new ArrayList<Region>();
		//查询省份
		pd.put("regionParentId", "1");
		provinceList = this.regionService.queryRegionByParentId(pd);
		
		//城市list
		List<Region> cityList = new ArrayList<Region>();
		//区list
		List<Region> areaList = new ArrayList<Region>();
		Department department = new Department();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(null != pd.get("id")) {
			//编辑，先查询出机构信息
			department = this.departmentService.findDepartmentById(pd);
			pd.put("regionParentId", department.getProvince());
			cityList = this.regionService.queryRegionByParentId(pd);
			pd.put("regionParentId", department.getCity());
			areaList = this.regionService.queryRegionByParentId(pd);
			paramMap.put("operateType", "edit");
		} else {
			paramMap.put("operateType", "add");
		}
		
		if(null != pd.get("parentId") && !"1".equals(pd.getString("parentId"))) {
			pd.put("id", pd.get("parentId"));
			Department parentDepartment = this.departmentService.findDepartmentById(pd);
			paramMap.put("value", parentDepartment.getDepartmentType());
		} else {
			//session中取得该用户所属机构
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			paramMap.put("value", String.valueOf(user.getDepartmentType()));
		}
		paramMap.put("type", "departmentType");
		List<Dictionary> departmentTypeList = this.dictionaryService.listDictionary(paramMap);
		mv.addObject("department",department);
		mv.setViewName("system/department/dep_edit");
		mv.addObject("departmentTypeList", departmentTypeList);
		mv.addObject("msg", "saveDepartment");
		mv.addObject("pd", pd);
		mv.addObject("provinceList",provinceList);
		mv.addObject("cityList",cityList);
		mv.addObject("areaList",areaList);
//		//插入日志
//		logService.insertOneLog(menuUrl,
//								Const.LOG_SYSTEM_TYPE_DATA,
//								Const.LOG_OPERATOR_TYPE_SELECT,
//								"查看所有机构");
		return mv;
	}
	
	/**
	 * 机构树
	 */
	@RequestMapping(value="/showDepartmentTree")
	public  ModelAndView showDepartmentTree() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String departmentId = pd.getString("departmentId");
		String selectedId = pd.getString("selectedId");
		
		List<Department> departmentList = null;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		if(!departmentId.isEmpty()){
			paramMap.put("departmentId", departmentId);
		}
		try{
			departmentList = departmentService.findAllDepartmentById(paramMap);
		}catch(Exception e){
			logger.error(e.toString(),e);
		}
		
		JSONArray json_arr = new JSONArray();
		for(Department department : departmentList) {
			JSONObject json_obj = new JSONObject();
			json_obj.put("id", department.getId());
			json_obj.put("pId",department.getParentId());
			json_obj.put("name",department.getDepartmentName());
//			json_obj.put("resLevel", department.getDepartmentType());
			json_obj.put("open", "true");
			if(!selectedId.isEmpty() && department.getId() == Integer.parseInt(selectedId) ){
				json_obj.put("checked", true);
			}
			json_arr.add(json_obj);
		}
		String json = json_arr.toString();
		System.out.println(json);
		mv.setViewName("system/department/dep_tree");
		mv.addObject("zTreeNodes", json);
		mv.addObject("msg", "saveDeptForUser");
		return mv;
	}
	
	/**
	 * 保存机构
	 */
	@RequestMapping(value="/saveDepartment")
	public ModelAndView saveDepartment() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		int result = this.departmentService.saveDepartment(pd,user);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		//插入日志
		String logRemark = "";
		int logType = Const.LOG_OPERATOR_TYPE_INSERT;
		if(null == pd.get("id") || 0 == Integer.parseInt(pd.get("id").toString())) {
			logRemark = "新增机构："+pd.getString("departmentName");
		} else {
			logRemark = "编辑机构："+pd.getString("departmentName");
			logType = Const.LOG_OPERATOR_TYPE_UPDATE;
		}
		logService.insertOneLog(menuUrl,
								Const.LOG_SYSTEM_TYPE_DATA,
								logType,
								logRemark);
		return mv;
	}
	/**
	 * 判断机构是否存在
	 * @return
	 */
	@RequestMapping(value="/hasDepartment")
	@ResponseBody
	public Object hasDepartment() {
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(departmentService.hasDepartment(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 删除机构及其下属所有机构
	 */
	@RequestMapping(value="/deleteDepartment")
	@ResponseBody
	public Object deleteDepartment() {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String departmentType = pd.getString("departmentType");
			String departmentName = pd.getString("departmentName");
			List<PageData> pdList = new ArrayList<PageData>();
			departmentService.deleteDepartment(pd);
			pdList.add(pd);
			map.put("list", pdList);
			
			String logRemark = "";
			if("总公司".equals(departmentType)) {
				logRemark = "删除总公司："+departmentName+"及其下属所有分公司、加油站信息";
			} else if("分公司".equals(departmentType)) {
				logRemark = "删除分公司："+departmentName+"及其下属所有加油站信息";
			} else {
				logRemark = "删除加油站："+departmentName;
			}
			logService.insertOneLog(menuUrl,
					Const.LOG_SYSTEM_TYPE_DATA,
					Const.LOG_OPERATOR_TYPE_DELETE,
					logRemark);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 去POS机信息页面
	 */
	@RequestMapping(value="/goPOSMachineInfo")
	public ModelAndView goPOSMachineInfo()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//POS机信息
		POSMachine posMachine = new POSMachine();
		try{
			posMachine.setDepartmentId(Integer.parseInt(pd.get("departmentId").toString()));
			Object id = pd.get("id");
			if(null != id && !id.toString().isEmpty() && !"0".equals(id) && !"undefined".equals(id)) {
				//查询POS机信息
				posMachine = this.departmentService.getPOSMachineById(id.toString());
			} 
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.addObject("posMachine",posMachine);
		mv.setViewName("system/department/pos/pos_edit");
		return mv;
	}
	
	/**
	 * 判断POS机是否存在
	 * @return
	 */
	@RequestMapping(value="/hasPOSMachine")
	@ResponseBody
	public Object hasPOSMachine() {
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			if(departmentService.hasPOSMachine(pd) != null){
				errInfo = "error";
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	
	/**
	 * 保存POS机信息
	 */
	@RequestMapping(value="/savePosMachine")
	public ModelAndView savePosMachine() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		int result = this.departmentService.savePosMachine(pd,user);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		//插入日志
		String logRemark = "";
		int logType = Const.LOG_OPERATOR_TYPE_INSERT;
		if(null == pd.get("id") || 0 == Integer.parseInt(pd.get("id").toString())) {
			logRemark = "新增POS机："+pd.getString("posNumber");
		} else {
			logRemark = "编辑POS机："+pd.getString("posNumber");
			logType = Const.LOG_OPERATOR_TYPE_UPDATE;
		}
		logService.insertOneLog(menuUrl,
								Const.LOG_SYSTEM_TYPE_DATA,
								logType,
								logRemark);
		return mv;
	}
	
	/**
	 * 删除POS机信息
	 */
	@RequestMapping(value="/delPosMachine")
	public void delPosMachine(PrintWriter out) {
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			this.departmentService.delPosMachine(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		//插入日志
		logService.insertOneLog(menuUrl,
								Const.LOG_SYSTEM_TYPE_DATA,
								Const.LOG_OPERATOR_TYPE_DELETE,
								"删除POS机："+pd.get("posNumber").toString());
	}
	
	/**
	 * 机构列表顶部导航
	 * @return
	 */
	public List<Department> getListDepForNavigation() {
		return listDepForNavigation;
	}

	/**
	 * 机构列表顶部导航
	 * @param listDepForNavigation
	 */
	public void setListDepForNavigation(List<Department> listDepForNavigation) {
		this.listDepForNavigation = listDepForNavigation;
	}
	
	/* ===============================获得按钮================================== */
	public Map<String, String> getBtnMenu(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
