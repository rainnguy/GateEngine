package com.furen.service.system.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.Page;
import com.furen.entity.system.Department;
import com.furen.entity.system.POSMachine;
import com.furen.entity.system.User;
import com.furen.util.PageData;

@Service("departmentService")
public class DepartmentService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 机构列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listDepartmentsById(Page pd) throws Exception  {
		return (List<PageData>) dao.findForList("DepartmentMapper.departmentslistPage", pd);
	}

	/**
	 * 查询加油站下所有POS机信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPosByStationId(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("POSMachineMapper.listPosByStationIdlistPage", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> findAllDepartmentById(Map<String,Object> paramMap) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.findAllDepartmentById", paramMap);
	}
	
	/**
	 * 获得顶部导航列表
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public Department queryListDepForNavigation(Map<String,Object> paramMap) throws Exception {
		return (Department) dao.findForObject("DepartmentMapper.queryListDepForNavigation", paramMap);
	}
	
	/**
	 * 判断机构是否存在
	 * @param pd
	 * @return
	 */
	public Department hasDepartment(PageData pd) throws Exception {
		return (Department) dao.findForObject("DepartmentMapper.hasDepartment", pd);
	}
	
	/**
	 * 根据id查询机构信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Department findDepartmentById(PageData pd)throws Exception{
		return (Department)dao.findForObject("DepartmentMapper.findDepartmentById", pd);
	}
	
	/**
	 * 保存机构信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int saveDepartment(PageData pd,User user) throws Exception {
		Object id = pd.get("id");
		int result = 0;
		if(null == id || 0 == Integer.parseInt(id.toString())) {
			//新增
			pd.put("createUser", user.getUSER_ID());
			result = (Integer)dao.save("DepartmentMapper.insertDepartment", pd);
		} else {
			//编辑
			pd.put("updateUser", user.getUSER_ID());
			result = (Integer)dao.update("DepartmentMapper.updateDepartment", pd);
		}
		return result;
	}
	
	/**
	 * 删除机构及其下属所有机构
	 * @param pd
	 */
	public void deleteDepartment(PageData pd) throws Exception {
		String departmentType = pd.getString("departmentType");
		String departmentId = pd.get("departmentId").toString();
		if("总公司".equals(departmentType)) {
			pd.put("parentId", pd.getString("departmentId"));
			//查询出总公司下所有分公司
			List<Department> subList = (List<Department>) dao.findForList("DepartmentMapper.queryDepartmentListByParentId", pd);
			if(null != subList && subList.size() > 0) {
				for(Department subDepartment : subList) {
					//查询分公司下所有加油站
					pd.clear();
					pd.put("parentId", subDepartment.getId());
					List<Department> stationList = (List<Department>) dao.findForList("DepartmentMapper.queryDepartmentListByParentId", pd);
					if(null != stationList && stationList.size() > 0) {
						for(Department station : stationList) {
							//删除加油站
							pd.clear();
							pd.put("id", station.getId());
							deleteStation(pd);
						}
					}
					//删除分公司
					pd.clear();
					pd.put("id", subDepartment.getId());
					deleteDepartmentByIdOrParentId(pd);
				}
			}
			//删除总公司
			pd.clear();
			pd.put("id", departmentId);
			deleteDepartmentByIdOrParentId(pd);
		} else if("分公司".equals(departmentType)) {
			//查询分公司下所有加油站
			pd.put("parentId", departmentId);
			List<Department> stationList = (List<Department>) dao.findForList("DepartmentMapper.queryDepartmentListByParentId", pd);
			if(null != stationList && stationList.size() > 0) {
				for(Department station : stationList) {
					//删除加油站
					pd.clear();
					pd.put("id", station.getId());
					deleteStation(pd);
				}
			}
			//删除分公司
			pd.clear();
			pd.put("id", departmentId);
			deleteDepartmentByIdOrParentId(pd);
		} else {
			//删除加油站
			pd.clear();
			pd.put("id", departmentId);
			deleteStation(pd);
		}
	}
	
	/**
	 * 删除机构信息，或删除机构的下级机构信息
	 */
	public void deleteDepartmentByIdOrParentId(PageData pd) throws Exception {
		this.dao.update("DepartmentMapper.deleteDepartmentByIdOrParentId", pd);
	}
	
	/**
	 * 删除加油站信息
	 * 删除加油站则删除加油站中的POS机信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteStation(PageData pd) throws Exception{
		//删除加油站
		deleteDepartmentByIdOrParentId(pd);
		//删除POS机信息
		pd.put("departmentId", pd.get("id"));
		deleteStationPos(pd);
	}
	
	/**
	 * 删除加油站下POS机信息
	 * @param pd
	 * @throws Exception
	 */
	public void deleteStationPos(PageData pd) throws Exception{
		this.dao.update("DepartmentMapper.deleteStationPos", pd);
	}
	
	/**
	 * 根据ID查询POS机信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public POSMachine getPOSMachineById(String id) throws Exception {
		return (POSMachine) dao.findForObject("POSMachineMapper.queryPOSMachineById", id);
	}
	
	/**
	 * 判断POS机是否存在
	 * @param pd
	 * @return
	 */
	public POSMachine hasPOSMachine(PageData pd) throws Exception {
		return (POSMachine) dao.findForObject("POSMachineMapper.hasPOSMachine", pd);
	}
	
	/**
	 * 保存POS机信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int savePosMachine(PageData pd,User user) throws Exception {
		Object id = pd.get("id");
		int result = 0;
		if("".equals(pd.getString("mailedDate"))) {
			pd.put("mailedDate", null);
		}
		if(null == id || 0 == Integer.parseInt(id.toString())) {
			//新增
			pd.put("createUser", user.getUSER_ID());
			result = (Integer)dao.save("POSMachineMapper.insertPOSMachine", pd);
		} else {
			//编辑
			pd.put("updateUser", user.getUSER_ID());
			result = (Integer)dao.update("POSMachineMapper.updatePOSMachine", pd);
		}
		return result;
	}
	
	/**
	 * 删除POS机
	 * @param pd
	 * @throws Exception
	 */
	public void delPosMachine(PageData pd) throws Exception {
		//将POS机的删除标识设置为2
		dao.update("POSMachineMapper.deletePOSMachine", pd);
	}
	
	/**
	 * 根据商户号取商户名称
	 * @return
	 * @throws Exception
	 */
	public String queryDepNameByMerchantNum(String merchantNum) throws Exception {
		return (String)dao.findForObject("DepartmentMapper.queryDepNameByMerchantNum", merchantNum);
	}
	
	/**
	 * 根据机构名称或商户名称及当前登录用户所属机构id，获得商户号
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String queryMerchantNumByDepNameOrMerchantName(PageData pd) throws Exception {
		String midStr = "";
		List<Department> list = (List<Department>)dao.findForList("DepartmentMapper.queryMerchantNumByDepNameOrMerchantName", pd);
		if(null != list && list.size() > 0) {
			for(Department dep : list) {
				midStr = midStr + dep.getMerchantNum() + "','";
			}
		}
		if(!midStr.isEmpty()) {
			midStr = "'"+midStr.substring(0,midStr.length()-2);
		}
		return midStr;
	}
}
