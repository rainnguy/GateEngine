package com.furen.service.system.order;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
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
}
