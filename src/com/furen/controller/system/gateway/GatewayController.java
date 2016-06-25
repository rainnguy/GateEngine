package com.furen.controller.system.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csii.payment.client.core.MerchantSignVerify;
import com.csii.payment.client.core.MerchantSignVerifyExt;
import com.furen.controller.base.BaseController;
import com.furen.entity.system.Department;
import com.furen.entity.system.Gateway;
import com.furen.entity.system.Order;
import com.furen.service.system.department.DepartmentService;
import com.furen.service.system.gateway.GatewayService;
import com.furen.service.system.order.OrderService;
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
	
	@Resource(name="gatewayService")
	private GatewayService gatewayService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
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
				Integer count = 0;
				if ("00".equals(respCode)) {
					// 跳转交易成功页面
					mv.setViewName("/system/gateway/successPage");
					
					// 更新订单状态
					map.put("status", "2");
					count = orderService.updateOrderStatus(map);
					if(count != 1){
						// TODO 更新订单失败的处理
					}
					
					// 生成加油券
					createCardTicket(map.get("termSsn"));
				} else {
					
					Map<String, String> codeMap = new HashMap<String, String>();
					codeMap.put("code", respCode);
					codeMap.put("termSsn", map.get("termSsn"));
					codeMap.put("mercCode", map.get("mercCode"));
					
					// 获取请求交易时的交易缩写
					Gateway gatewayRequest = gatewayService.getRequTranAbbr(codeMap);
					
					String tranAbbr = "";
					if (gatewayRequest != null) {
						tranAbbr = gatewayRequest.getTranAbbr();
					}
					codeMap.put("tranAbbr", tranAbbr);
					
					// 获取响应码的含义
					Gateway gatewayResp = gatewayService.getContOfRespCode(codeMap);
					mv.addObject("errorCode", "响应码：" + respCode);

					String content = "";
					if (gatewayResp != null) {
						content = gatewayResp.getRespCodeContent();
					}
					// 响应码含义
					mv.addObject("errorInfo", content);
					// 跳转交易失败页面
					mv.setViewName("/system/gateway/failurePage");
				}
				
				// 更新网关响应信息
				count = orderService.updateOrderInfo(map);
				if(count != 1){
					// TODO 更新网关响应信息失败的处理
				}
				
			} else {
				mv.addObject("errorCode", "");
				mv.addObject("errorInfo", "网关数据验签失败！");
				// 跳转交易失败页面
				mv.setViewName("/system/gateway/failurePage");
			}
		} else {
			// 获取可选择的站点
			getStations(mv);
			
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
	public ModelAndView prepay() {
		
		ModelAndView mv = this.getModelAndView();
		
		mv.addObject("description", "pay test");
		mv.setViewName("/system/gateway/backup");
		
		return mv;
	}
	
	/**
	 * shopping main page
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("defaultPage")
	public ModelAndView defaultPage() {
		
		ModelAndView mv = this.getModelAndView();
		
		mv.setViewName("/system/admin/default");
		
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
			useAbleStations = useAbleStations + department.getDepartmentName() + "\r\n";
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
		String signature = MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_" + merchantNum,
				"key_password_" + merchantNum, plain);
		
		map.put("signature", signature);
		
		return AppUtil.returnObject(new PageData(), map);
		
	}
	
	/**
	 * 获取可选择的站点
	 * 
	 * @param mv
	 */
	private void getStations(ModelAndView mv) {
		
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
	
	/**
	 * 生成加油券
	 * 
	 * @param termSsn 订单号
	 */
	private void createCardTicket(String termSsn){
		
		// 生成交易码 默认8位
		String payCode = getRandomString(8);
		
		/*	
		// 获取订单信息
		Order order = orderService.getOrderInfo(termSsn);
		if (order != null){
			
			// 获取购买数量
			int number = order.getNumber();
			
			Map<String,String> mapForCeateCard = new HashMap<String,String>();
			
			// 订单时间
			mapForCeateCard.put("orderTime", order.getOrderTime());
			// 购买的用户
			mapForCeateCard.put("userCode", order.getUserCode());
			// 商户号
			mapForCeateCard.put("merchantNum", order.getMerchantNum());
			// 商户名称
			mapForCeateCard.put("merchantName", order.getMerchantName());
			// 商品名称
			mapForCeateCard.put("goodsName", order.getGoodsName());
			// 商品面值
			mapForCeateCard.put("goodsValue", String.valueOf(order.getGoodsValue()));
		}
		
		// 获取表中当天的最大卡号
		String maxCode = dispCardMapper.findMaxCardId(dispCardMap);
		
		
		// 当天开始时间
		dispCardMap.put("startTime", DateUtil.getStartTime());
		Integer tempCode = 0;
		if(maxCode != null && maxCode != ""){
			tempCode = Integer.valueOf(maxCode.substring(12));
		}
		Integer num = Integer.valueOf(number.toString());
		List<DispCardMap> dispCardMapList = new ArrayList<DispCardMap>();
		String zero = "00000000";
		for(int i = 1; i <= num ; i++){
			String str = String.valueOf(tempCode + i);
			String newCode = "LPK0" + nowDay + zero.substring(str.length()) + str;
			
			// 卡号
			dispCardMap.put("code", newCode);
			// 密码
			dispCardMap.put("password", encoderByMd5(newCode));
			// 站点编号
			dispCardMap.put(SysConsts.ORG_CODE, Common.findAttrValue(SysConsts.ORG_CODE));
			// 操作编号
			dispCardMap.put(SysConsts.OPER_CODE, Common.findAttrValue(SysConsts.OPER_CODE));
			// 当前系统时间
			dispCardMap.put("nowDate", DateUtil.getCurrDate());
			
			dispCardMapList.add(dispCardMap);
			
			dispCardMap = getFormMap(DispCardMap.class);
		}
		
		try {
			int count = DispCardMap.mapper().insertNewData(dispCardMapList);
			if(count != dispCardMapList.size()){
				return "insertwrong";
			}
		} catch (Exception e) {
			throw new SystemException("生成卡异常");
		}
		*/
	}
	
	/**
	 * 生成随机字符串，包括大写字母和数字
	 * 
	 * @param length
	 * @return
	 */
	private String getRandomString(int length) {
        String str="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        
        for(int i = 0 ; i < length; ++i){
        	
        	int number = random.nextInt(46);//[0,36)
        	sb.append(str.charAt(number));
        }
        
        return sb.toString();
    }
}
