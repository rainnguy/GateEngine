package com.furen.service.system.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.system.Coupon;

@Service("couponService")
public class CouponService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取当天的最大编号
	 * @param orderTime
	 * @return
	 * @throws Exception
	 */
	public Coupon findMaxCode(String orderTime) throws Exception {
		return (Coupon) dao.findForObject("CouponMapper.findMaxCode", orderTime);
	}
	
	/**
	 * 判断支付码是否冲突
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Coupon isPayCodeExisted(Map<String, String> map) throws Exception {
		return (Coupon) dao.findForObject("CouponMapper.isPayCodeExisted", map);
	}
	
	/**
	 * 插入卡券数据
	 * @param mapList
	 * @return
	 * @throws Exception 
	 */
	public int insertCoupons(List<Map<String,String>> mapList) throws Exception{
		return (Integer) dao.save("CouponMapper.insertCoupons", mapList);
	}
}
