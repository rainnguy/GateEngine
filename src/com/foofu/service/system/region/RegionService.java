package com.foofu.service.system.region;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.foofu.dao.DaoSupport;
import com.foofu.entity.system.Region;
import com.foofu.util.PageData;

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
