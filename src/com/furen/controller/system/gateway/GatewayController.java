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

import com.csii.payment.client.core.MerchantSignVerify;
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
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String plain = pd.getString("Plain");
		String signature = pd.getString("Signature");
		
		if (plain != null && signature != null) {
			if (MerchantSignVerify.merchantVerifyPayGate_ABA(signature, plain)) {
				// 获取plain中的数据
				String[] array=plain.split("\\|");
				Map<String,String> map = new HashMap<String,String>();
				for (int i = 0; i < array.length; i++) {
					if (array[i].startsWith("TranAbbr=")) {
						map.put("tranAbbr", array[i].substring(9));
					} else if (array[i].startsWith("AcqSsn=")) {
						map.put("acqSsn", array[i].substring(7));
					} else if (array[i].startsWith("MercDtTm=")) {
						map.put("mercDtTm", array[i].substring(9));
					} else if (array[i].startsWith("TermSsn=")) {
						map.put("termSsn", array[i].substring(8));
					} else if (array[i].startsWith("TermCode=")) {
						map.put("termCode", array[i].substring(9));
					} else if (array[i].startsWith("MercCode=")) {
						map.put("mercCode", array[i].substring(9));
					} else if (array[i].startsWith("TranAmt=")) {
						map.put("tranAmt", array[i].substring(8));
					} else if (array[i].startsWith("ClearDate=")) {
						map.put("clearDate", array[i].substring(10));
					} else if (array[i].startsWith("RespCode=")) {
						map.put("respCode", array[i].substring(9));
					}
				}
				String respCode = map.get("respCode");
				if ("00".equals(respCode)) {
					// 跳转交易成功页面
					mv.setViewName("/system/gateway/successPage");
				} else {
					mv.addObject("errorInfo", "交易失败！响应码：" + respCode);
					// 跳转交易失败页面
					mv.setViewName("/system/gateway/failurePage");
				}
				
				// 网关响应信息存入数据库 TODO
				
				
			} else {
				mv.addObject("errorInfo", "网关数据验签失败！");
				// 跳转交易失败页面
				mv.setViewName("/system/gateway/failurePage");
			}
		} else {
			// 获取可选择的站点
			getStations(mv);
			
//			mv.setViewName("/system/gateway/payPage");
			mv.setViewName("/system/gateway/prepay");
		}
		
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
		mv.setViewName("/system/gateway/backup");
		
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
		String merchantNum = pd.getString("merchantNum");
		
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
		String signature = MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_"+merchantNum,
				"key_password_"+merchantNum, plain);
		
		map.put("signature", signature);
		
		return AppUtil.returnObject(new PageData(), map);
		
	}
	
	/**
	 * 把订单信息保存到数据库
	 */
	@RequestMapping("saveOrderInfo")
	public void saveOrderInfo(){
		// TODO 功能未实现
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String merchantNum = pd.getString("merchantNum");
		String price = pd.getString("price");
		String number = pd.getString("number");
		String payType = pd.getString("payType");
		String payBank = pd.getString("payBank");
		String cardType = pd.getString("cardType");
		
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
