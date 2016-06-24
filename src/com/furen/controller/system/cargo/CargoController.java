package com.furen.controller.system.cargo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.furen.controller.base.BaseController;
import com.furen.entity.system.Cargo;
import com.furen.service.system.cargo.CargoService;
import com.furen.util.AppUtil;
import com.furen.util.PageData;


/**
 * 商品
 * 
 * @author xk 2016-06-23
 * @version 1.0v
 */
@Controller
@RequestMapping("/cargo/")
public class CargoController extends BaseController {

	@Resource(name="cargoService")
	private CargoService cargoService;
	
	/**
	 * 商品管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cargoList")
	public ModelAndView cargoList() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		
		mv.addObject("user", "admin");
		mv.setViewName("/system/cargo/cargoList");
		
		return mv;
	}
	
	/**
	 * 获取可选择的商品
	 * 
	 * @param mv
	 */
	@ResponseBody
	@RequestMapping("getSelectableCargoes")
	public Object getSelectableCargoes() {
		
		Map<String,String> map = new HashMap<String,String>();
		String errorInfo = "success";
		PageData pd = new PageData();
		
		pd = this.getPageData();
		String merchantNum = pd.getString("merchantNum");
		
		// 获取所有可选择的面值
		List<Cargo> mapList = new ArrayList<Cargo>();
		
		try{
			mapList = cargoService.getSelectableGoods(merchantNum);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			errorInfo = "error";
		}
		String price = "";
		String goodsValue = "";
		for(Cargo cargo : mapList){
			goodsValue = goodsValue + "," + cargo.getGoodsValue();
			price = price + "," + cargo.getPrice();
		}
		
		map.put("errInfo", errorInfo);
		map.put("price", price);
		map.put("goodsValue", goodsValue);
		
		return AppUtil.returnObject(new PageData(), map);
	}
}
