package com.foofu.controller.system.gataway;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.system.User;
import com.foofu.service.system.fusionCharts.FusionChartsService;
import com.foofu.util.AppUtil;
import com.foofu.util.Const;
import com.foofu.util.PageData;
/** 
 * 类名称：FusionChartsController
 * 创建人：xiajj
 * 创建时间：2015年11月10日
 * @version
 */
@Controller
@RequestMapping(value="/fusionCharts")
public class FusionChartsController extends BaseController {
	
	@Resource(name="fusionChartsService")
	private FusionChartsService fusionChartsService;

	
	/**
	 * 查询周报表
	 * @return
	 */
	@RequestMapping(value="/querhWeekCharts")
	@ResponseBody
	public Object querhWeekCharts() {
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		String resultString = "";
		try{
			//session中取得该用户所属机构
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pd = this.getPageData();
			resultString = fusionChartsService.querhWeekCharts(pd,user);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
//		String strXML = "";
//		strXML += "<graph caption='营业额周报表' xAxisName='日期' yAxisName='营业额（万元）' decimalPrecision='0' formatNumberScale='0'>";
//		strXML += "<set name='2015-12-11' value='0' color='AFD8F8'/>";
//		strXML += "<set name='2015-12-12' value='0' color='F6BD0F'/>";
//		strXML += "<set name='2015-12-13' value='0' color='8BBA00'/>";
//		strXML += "<set name='2015-12-14' value='0' color='FF8E46'/>";
//		strXML += "<set name='2015-12-15' value='0' color='008E8E'/>";
//		strXML += "<set name='2015-12-16' value='0' color='D64646'/>";
//		strXML += "<set name='2015-12-17' value='0' color='8E468E'/>";
//		strXML += "</graph>";
//		resultString = strXML;
		map.put("result", resultString);				//返回结果
		map.put("pd.queryDate", pd.getString("queryDate"));
		map.put("pd.queryDepartmentName", pd.get("queryDepartmentName").toString());
		map.put("pd.departmentIdWeek", pd.getString("depId"));
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**
	 * 查询月报表
	 * @return
	 */
	@RequestMapping(value="/queryMonthCharts")
	@ResponseBody
	public Object queryMonthCharts() {
		Map<String,String> map = new HashMap<String,String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try{
			//session中取得该用户所属机构
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pd = this.getPageData();
			String monthChartsData = fusionChartsService.queryMonthChartsData(pd,user);
			map.put("monthChartsData", monthChartsData);
		} catch(Exception e){
			logger.error(e.toString(), e);
			errInfo="fail";
		}
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
}
