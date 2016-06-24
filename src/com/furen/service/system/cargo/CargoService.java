package com.furen.service.system.cargo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.system.Cargo;

@Service("cargoService")
public class CargoService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取可选择的面值
	 * @param merchantNum
	 * @return
	 * @throws Exception
	 */
	public List<Cargo> getSelectableGoods(String merchantNum) throws Exception  {
		return (List<Cargo>) dao.findForList("CargoMapper.getSelectableGoods", merchantNum);
	}
}
