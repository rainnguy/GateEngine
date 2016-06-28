package com.furen.controller.system.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.furen.controller.base.BaseController;
import com.furen.entity.Page;
import com.furen.entity.system.User;
import com.furen.service.system.order.OrderService;
import com.furen.util.AppUtil;
import com.furen.util.Const;
import com.furen.util.Jurisdiction;
import com.furen.util.PageData;

/**
 * 订单
 * 
 * @author _xk 2016-06-21
 * @version 1.0v
 */
@Controller
@RequestMapping("/order/")
public class OrderController extends BaseController {

	String menuUrl = "order/orderList.do"; //菜单地址(权限用)
	@Resource(name="orderService")
	private OrderService orderService;
	
	/**
	 * 订单查询页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("orderList")
	public ModelAndView orderList(Page page) throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		
		pd = this.getPageData();
		pd.put("userCode", getUserCode());
		page.setPd(pd);
		
		// 获取用户的所有订单
		List<PageData> orderList = orderService.getOrderList(page);
		//获得页面按钮
		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
		mv.addObject("pd", pd);
		mv.addObject("orderList", orderList);
		mv.setViewName("/system/order/orderList");

		return mv;
	}
	
	/**
	 * 把订单信息保存到数据库
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("saveOrderInfo")
	public Object saveOrderInfo() {

		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String errInfo = "success";
		Integer count = 0;
		
		pd = this.getPageData();
		pd.put("userCode", getUserCode());
		
		try {
			count = orderService.insertOrder(pd);
		} catch (Exception e) {
			e.printStackTrace();
			errInfo = "error";
			map.put("errInfo", errInfo);
			return AppUtil.returnObject(new PageData(), map);
		}
		
		if (count != 1) {
			errInfo = "error";
		}
		
		map.put("errInfo", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 获取登陆的用户名
	 * @return
	 */
	private String getUserCode() {
		
		Subject subject = SecurityUtils.getSubject();  
		Session session = subject .getSession();
		User usera = (User)session.getAttribute(Const.SESSION_USER);
		return usera.getUSERNAME();
	}
}
