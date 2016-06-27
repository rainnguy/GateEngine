package com.furen.controller.system.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csii.payment.client.core.MerchantSignVerify;
import com.csii.payment.client.core.MerchantSignVerifyExt;
import com.furen.controller.base.BaseController;
import com.furen.entity.system.Coupon;
import com.furen.entity.system.Department;
import com.furen.entity.system.Gateway;
import com.furen.entity.system.Order;
import com.furen.service.system.coupon.CouponService;
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
	
	@Resource(name="couponService")
	private CouponService couponService;
	
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
					String result = createCardTicket(map.get("termSsn"));
					if (!"success".equals(result)) {
						// TODO 异常处理
					}
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
	 * @throws Exception 
	 * @return 
	 */
	private String createCardTicket(String termSsn) throws Exception{
		
		// 获取订单信息
		Order order = orderService.getOrderInfo(termSsn);
		
		if (order == null) {
			// TODO 异常处理
			return "error";
		}
		
		// 购买数量
		int number = order.getNumber();
		// 订单时间
		String orderTime = order.getOrderTime();
		
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		
		// 获取表中当天的最大卡号
		Coupon coupon = couponService.findMaxCode(orderTime);
		String maxCode = "";
		if (coupon != null) {
			maxCode = coupon.getCode(); 
		}
		
		for(int i = 1; i <= number ; i++){
			
			// 新的卡券号
			String newCode = null;
			String zero = "0000";
			if ("".equals(maxCode)) {
				String str = String.valueOf(i);
				if (i < 1000) {
					newCode = orderTime + zero.substring(str.length()) + str;
				} else {
					newCode = orderTime + str;
				}
			} else {
				int numTemp = Integer.valueOf(maxCode.substring(14)) + i;
				String numStr = String.valueOf(numTemp);
				if (numTemp < 1000) {
					newCode = maxCode.substring(0,14) + zero.substring(numStr.length()) + numStr;
				} else {
					newCode = maxCode.substring(0,14) + numStr;
				}
			}
			
			// 支付码
			String payCode = null;
			while (true) {
				Map<String,String> mapTemp = new HashMap<String,String>();
				payCode = getRandomString();
				mapTemp.put("payCode", payCode);
				mapTemp.put("orderTime", orderTime);
				// 判断该支付码是否冲突
				Coupon couponTemp = couponService.isPayCodeExisted(mapTemp);
				if (couponTemp != null && couponTemp.getPayCode() != null && !"".equals(couponTemp.getPayCode())) {
					continue;
				} else {
					break;
				}
			}
			map.put("payCode", payCode);
			
			// 卡券号
			map.put("newCode", newCode);
			// 订单时间
			map.put("orderTime", orderTime);
			// 购买的用户
			map.put("userCode", order.getUserCode());
			// 商户号
			map.put("merchantNum", order.getMerchantNum());
			// 商户名称
			map.put("merchantName", order.getMerchantName());
			// 商品名称
			map.put("goodsName", order.getGoodsName());
			// 商品面值
			map.put("goodsValue", String.valueOf(order.getGoodsValue()));
			// 商品价格
			map.put("price", String.valueOf(order.getPrice()));
			
			mapList.add(map);
			map = new HashMap<String,String>();
		}
		
		// 插入卡券数据
		int count = couponService.insertCoupons(mapList);
		if(count != mapList.size()){
			return "insertwrong";
		} else {
			return "success";
		}
	}
	
	/**
	 * 生成随机字符串，包括大写字母和数字
	 * 
	 * @return
	 */
	private String getRandomString() {
		//定义支付码长度
		int length = 8;
		//定义支付码每一位的范围
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
