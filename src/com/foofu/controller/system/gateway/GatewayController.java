package com.foofu.controller.system.gatewayEngine;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.csii.payment.client.core.MerchantSignVerify;
import com.foofu.controller.base.BaseController;

/**
 * 网关
 * 
 * @author _wsq 2016-05-16
 * @version 2.0v
 */
@Controller
@RequestMapping("/gateway/")
public class GatewayController extends BaseController {

	/**
	 * 购买页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("payPage")
	public ModelAndView payPage() throws Exception {
		
		ModelAndView mv = this.getModelAndView();
		
		mv.addObject("description", "加油券");
		mv.setViewName("/system/gatewayEngine/payPage");
		
		return mv;
	}
	
	/**
	 * 调用支付接口
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@ResponseBody
	@RequestMapping("pay")
	@Transactional(readOnly=false)
	public String pay() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MerchantSignVerify.merchantSignData_ABA("Plain");
		
		String plain;
		String signature;
		String transName;

		transName="IPER";
		plain="TranAbbr=IPER|MasterID=|MercDtTm=20150803153700|TermSsn=000000000018|OSttDate=|OAcqSsn=|MercCode=983708160001601|TermCode=|TranAmt=12.05|Remark1=|Remark2=|MercUrl=https%3A%2F%2F112.4.3.50%3A443%2Fpayment%2FpaymentSpdbNotify%2Fv1|Ip=|SubMercFlag=0|SubMercName=system_api_provider|SubMercGoodsName=%BA%AE%B1%F9%B1%A6%BD%A3|PayFlag=";
		signature="38f817f0fefca694050c14806004865ed6fbe0b53de6fdecb4eb73590c9c6e8b378321be2c283d6a45c1bba02409616d2f2316298ec1cc5e60c6640f4fd82bb6a3e59c73953f24a1e862fa187d4e86e2ef0f2178f6a93a6099fc0b735d70d9174085e405f213ae34842d5c4abf3f023de27096b14f4a9f3104095a032789f735";

		
		
		return null;
		
	}
	
}
