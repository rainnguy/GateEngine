package com.furen.service.system.order;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.Page;
import com.furen.entity.system.Order;
import com.furen.util.PageData;



@Service("orderService")
public class OrderService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 把订单数据保存到数据库
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer insertOrder(PageData pd) throws Exception  {
		return (Integer) dao.save("OrderMapper.insertOrder", pd);
	}
	
	/**
	 * 更新订单状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer updateOrderStatus(Map<String,String> map) throws Exception {
		return (Integer) dao.update("OrderMapper.updateOrderStatus", map);
	}
	
	/**
	 * 更新网关响应信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer updateOrderInfo(Map<String,String> map) throws Exception {
		return (Integer) dao.update("OrderMapper.updateOrderInfo", map);
	}
	
	/**
	 * 获取用户的所有订单
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getOrderList(Page pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMapper.getOrderlistPage", pd);
	}
	
	/**
	 * 获取订单信息
	 * @param termSsn
	 * @return
	 * @throws Exception
	 */
	public Order getOrderInfo(String termSsn) throws Exception {
		return (Order) dao.findForObject("OrderMapper.getOrderInfo", termSsn);
	}
}
