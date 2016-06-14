package com.furen.service.system.region;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.system.Region;
import com.furen.util.PageData;

@Service("regionService")
public class RegionService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 根据父ID查询下级
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Region> queryRegionByParentId(PageData pd) throws Exception  {
		return (List<Region>) dao.findForList("RegionMapper.queryRegionByParentId", pd);
	}
}
