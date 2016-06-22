package com.furen.controller.system.order;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.furen.controller.base.BaseController;
import com.furen.service.system.order.OrderService;
import com.furen.util.AppUtil;
import com.furen.util.PageData;

/**
 * 订单
 * 
 * @author _xk 2016-06-21
 * @version 2.0v
 */
@Controller
@RequestMapping("/order/")
public class OrderController extends BaseController {

	@Resource(name="orderService")
	private OrderService orderService;
	
	/**
	 * 把订单信息保存到数据库
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("saveOrderInfo")
	public Object saveOrderInfo() {

		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		
		PageData pd = new PageData();
		
		pd = this.getPageData();
		// TODO
		Integer count = 0;
		try {
			count = orderService.insertOrder(pd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
}
