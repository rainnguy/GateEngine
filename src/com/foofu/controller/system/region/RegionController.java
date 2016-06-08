package com.foofu.controller.system.region;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.system.Region;
import com.foofu.service.system.region.RegionService;
import com.foofu.util.PageData;

/** 
 * 类名称：RegionController
 * 创建人：xiajj 
 * 创建时间：2015年11月10日
 * @version
 */
@Controller
@RequestMapping(value="/region")
public class RegionController extends BaseController {
	@Resource(name="regionService")
	private RegionService regionService;
	
	/**
	 * 根据父级ID获得下级
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/queryRegionByParentId" ,produces="application/json;charset=UTF-8")
	public void queryRegionByParentId(@RequestParam String regionParentId,HttpServletResponse response) throws Exception {
		//定义返回对象
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("regionParentId", regionParentId);
			List<Region> subRegionList = regionService.queryRegionByParentId(pd);
			JSONArray arr = JSONArray.fromObject(subRegionList);
			PrintWriter out;
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
}
