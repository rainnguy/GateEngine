package com.furen.service.system.gateway;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.system.Gateway;



@Service("gatewayService")
public class GatewayService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 获取响应码的含义
	 * @param codeMap
	 * @return
	 * @throws Exception
	 */
	public Gateway getContOfRespCode(Map<String, String> codeMap) throws Exception  {
		return (Gateway) dao.findForObject("GatewayMapper.getContOfRespCode", codeMap);
	}
	
}
