package com.furen.controller.system.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csii.payment.client.core.MerchantSignVerifyExt;
import com.furen.controller.base.BaseController;
import com.furen.entity.system.Department;
import com.furen.service.system.department.DepartmentService;
import com.furen.util.AppUtil;
import com.furen.util.PageData;

/**
 * 网关
 * 
 * @author _wsq 2016-05-16
 * @version 2.0v
 */
@Controller
@RequestMapping("/gateway/")
public class GatewayController extends BaseController {

	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	/**
	 * 购买页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("payPage")
	public ModelAndView payPage() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		
		// 获取可选择的站点
		getStations(mv);
		
		mv.setViewName("/system/gateway/payPage");
		
		return mv;
	}
	
	/**
	 * shopping test page
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("prepay")
	public ModelAndView prepay() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		
		mv.addObject("description", "pay test");
		mv.setViewName("/system/gateway/prepay");
		
		return mv;
	}
	
	/**
	 * 获取可使用的站
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("showUseAbleStations")
	public Object showUseAbleStations() throws Exception {
		
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		String useAbleStations="";
		// 获取可使用的站
		List<Department> stationMap = new ArrayList<Department>();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String merchantNum = pd.getString("requestMethod");
		
		try{
			stationMap = departmentService.getAllUseAbleStations(merchantNum);
		} catch (Exception e) {
			errInfo = "error";
			logger.error(e.toString(), e);
		}
		
		for(Department department : stationMap){
			useAbleStations = useAbleStations + department.getDepartmentName().toString() + "\r\n";
		}
		// 商品描述
		map.put("useAbleStations", useAbleStations);
		map.put("errInfo", errInfo);
		
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 数据签名
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("signData")
	public Object signData() {
		
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String plain = pd.getString("plain");
		String merchantNum = pd.getString("merchantNum");
		// TODO 商户号目前是假的
//		MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_"+merchantNum,
//				"key_password_"+merchantNum, plain);
		String signature = MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_983708160009501",
				"key_password_983708160009501", plain);
		
		map.put("signature", signature);
		
		return AppUtil.returnObject(new PageData(), map);
		
	}
	
	/**
	 * 获取可选择的站点
	 * 
	 * @param mv
	 */
	private void getStations(ModelAndView mv){
		
		Map<String, String> orgCodeMap = new LinkedHashMap<String, String>();
		// 设置默认的空值
		orgCodeMap.put("", "选择站点");
		// 获取所有的站点
		List<Department> stationMap = new ArrayList<Department>();
		
		try{
			stationMap = departmentService.getAllMerchant();
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		
		for(Department department : stationMap){
			String orgName = department.getDepartmentName();
			String orgNum =  department.getMerchantNum();
			orgCodeMap.put(orgNum, orgName);
		}
		
		mv.addObject("orgValue", orgCodeMap);
	}
}
