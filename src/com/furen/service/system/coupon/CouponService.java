package com.furen.service.system.coupon;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.Page;
import com.furen.entity.system.Cargo;
import com.furen.util.PageData;

@Service("couponService")
public class CouponService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取可选择的面值
	 * @param merchantNum
	 * @return
	 * @throws Exception
	 */
	public List<Cargo> getSelectableGoods(String merchantNum) throws Exception {
		return (List<Cargo>) dao.findForList("CargoMapper.getSelectableGoods", merchantNum);
	}
	
	/**
	 * 获取各站点出售的商品
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getCargoList(Page pd) throws Exception {
		return (List<PageData>) dao.findForList("CargoMapper.getCargoList", pd);
	}
	
	/**
	 * 删除商品
	 * @param pd
	 * @throws Exception
	 */
	public void deleteCargo(PageData pd) throws Exception {
		//将商品的删除标识设置为2
		dao.update("CargoMapper.deleteCargo", pd);
	}
}
