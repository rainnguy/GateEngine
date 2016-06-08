package com.foofu.service.system.fusionCharts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.foofu.dao.DaoSupport1;
import com.foofu.entity.system.FusionCharts;
import com.foofu.entity.system.User;
import com.foofu.service.system.department.DepartmentService;
import com.foofu.util.Const;
import com.foofu.util.DateUtil;
import com.foofu.util.MapSort;
import com.foofu.util.PageData;

@Service("fusionChartsService")
public class FusionChartsService{

	//连接第2个数据源（DaoSupport1）
	@Resource(name = "daoSupport1")
	private DaoSupport1 dao1;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;

	public String queryMonthChartsData(PageData pd,User user) throws Exception {
		//1.准备查询数据
		//获取页面查询条件
		String departmentId = pd.getString("departmentId");
		String month = pd.getString("month");
		//用户当前机构id
		pd.put("userDepartmentId", user.getDepartmentId());
		//若选择了机构，则查询该机构下的交易数据
		if(null != departmentId && !departmentId.isEmpty()) {
			pd.put("depId", departmentId);
		}
		//minStr 为当前登录用户可查询数据范围
		String midStr = this.departmentService.queryMerchantNumByDepNameOrMerchantName(pd);
		pd.put("midStr", midStr);
		
		//2.获取报表数据
		//勾画报表图形的字符串（含有数据及维度等）
		String strMonthData = "";
		//定义处理结果的map
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//定义处理结果colorMap
		Map<String,Object> colorMap = new HashMap<String,Object>();
		//按月统计
//		if(null == departmentId || null == month || month.equals("0") || departmentId.isEmpty() || month.isEmpty()){
		if(null == month || month.equals("0") || month.isEmpty()){
			paramMap = this.getResultMap("month","");
			colorMap = this.getColorMap(paramMap.size());
			strMonthData = "<graph caption='营业额月报表' xAxisName='月份' yAxisName='营业额（元）' decimalPrecision='0' formatNumberScale='0'>";
			
			//统计数据的时间范围，当前年份01-01 00:00:00 ~ 当前时间
			String startDate = new SimpleDateFormat("yyyy-01-01 00:00:00").format(new Date());
			String endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			pd.put("startDate", startDate);
			pd.put("endDate", endDate);
			//查询统计结果
			@SuppressWarnings("unchecked")
			List<FusionCharts> monthDataList = (List<FusionCharts>) dao1.findForList("FusionChartsMapper.calMonthData", pd);
			for(int index = 0;index < monthDataList.size();index++){
				FusionCharts monthData = monthDataList.get(index);
				paramMap.put(monthData.getDate(), monthData.getMoney());
			}
			//组装返回页面的信息
			//先按map中的key排序
			paramMap = MapSort.sortMapByKey(paramMap);
			for (String key : paramMap.keySet()) {  
				strMonthData += "<set name='"+key+"' value='"+paramMap.get(key)+"' color='"+colorMap.get(key)+"'/>";
			}
			strMonthData += "</graph>";
		}else{//每月按天统计
			paramMap = this.getResultMap("month_day",month);
			colorMap = this.getColorMap(paramMap.size());
			strMonthData = "<graph caption='营业额月报表' xAxisName='日' yAxisName='营业额（元）' decimalPrecision='0' formatNumberScale='0'>";
			
			//统计数据的时间范围，当前月份01 00:00:00 ~ 下个月01 00:00:00
			String startDate = new SimpleDateFormat("yyyy-"+month+"-01 00:00:00").format(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(startDate));
			calendar.add(Calendar.MONTH, 1);
			String endDate = sdf.format(calendar.getTime());
			pd.put("startDate", startDate);
			pd.put("endDate", endDate);
			//查询统计结果
			@SuppressWarnings("unchecked")
			List<FusionCharts> monthDataList = (List<FusionCharts>) dao1.findForList("FusionChartsMapper.calMonthDayData", pd);
			for(int index = 0;index < monthDataList.size();index++){
				FusionCharts monthData = monthDataList.get(index);
				paramMap.put(monthData.getDate(), monthData.getMoney());
			}
			//组装返回页面的信息
			//先按map中的key排序
			paramMap = MapSort.sortMapByKey(paramMap);
			for (String key : paramMap.keySet()) {  
				strMonthData += "<set name='"+key+"' value='"+paramMap.get(key)+"' color='"+colorMap.get(key)+"'/>";
			}
			strMonthData += "</graph>";
		}
		/*报表原数据格式
		strMonthData = "<graph caption='对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
		strMonthData += "<set name='1' value='99999' color='AFD8F8'/>";
		strMonthData += "<set name='2' value='857' color='F6BD0F'/>";
		strMonthData += "<set name='3' value='671' color='8BBA00'/>";
		strMonthData += "<set name='4' value='494' color='FF8E46'/>";
		strMonthData += "<set name='5' value='761' color='008E8E'/>";
		strMonthData += "<set name='6' value='960' color='D64646'/>";
		strMonthData += "<set name='7' value='629' color='8E468E'/>";
		strMonthData += "<set name='8' value='622' color='588526'/>";
		strMonthData += "<set name='9' value='376' color='B3AA00'/>";
		strMonthData += "<set name='10' value='494' color='008ED6'/>";
		strMonthData += "<set name='11' value='761' color='9D080D'/>";
		strMonthData += "<set name='12' value='960' color='A186BE'/>";
		strMonthData += "</graph>";
		*/
		return strMonthData;
	}
	
	/**
	 * 获取存放月报表初始数据map的方法，初始结果
	 * @param type
	 * @param month
	 * @return
	 */
	private Map<String,Object> getResultMap(String type,String month){
		Map<String,Object> paramMap = new HashMap<String,Object>();
		int mapLength = 0;
		//一年12个月
		if(type.equals("month")){
			mapLength=12;
		}
		//按一个月28/29/30/31天
		if(type.equals("month_day")){
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			if(month.equals("1") || month.equals("3") || month.equals("5") || month.equals("7") || month.equals("8")
					 || month.equals("10") || month.equals("12")){
				mapLength=31;
			}
			if(month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")){
				mapLength=30;
			}
			if(month.equals("2")){
				if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
					mapLength=29;
				}else{
					mapLength=28;
				}
			}
		}
		
		for(int index=1;index<=mapLength;index++){
			if(index<10){
				paramMap.put("0"+index, "0.00");
			}else{
				paramMap.put(""+index, "0.00");
			}
		}
		return paramMap;
	}
	
	/**
	 * 获取存放月报表结果初始颜色map的方法，初始结果
	 * @param size
	 * @return
	 */
	private Map<String,Object> getColorMap(int size){
		Map<String,Object> colorMap = new HashMap<String,Object>();
		for(int index=1;index<=size;index++){
			if(index<10){
				colorMap.put("0"+index, Const.COLORS[index%12]);
			}else{
				colorMap.put(""+index, Const.COLORS[index%12]);
			}
		}
		return colorMap;
	}
	
	/**
	 * 查询周报表
	 * @param pd
	 * @return
	 */
	public String querhWeekCharts(PageData pd,User user) throws Exception {
		//定义返回结果
		String resultString = "<graph caption='营业额周报表' xAxisName='日期' yAxisName='营业额（元）' decimalPrecision='0' formatNumberScale='0'>";
		//定义处理结果的map
		Map<String,Object> paramMap = new HashMap<String,Object>();
		//定义处理结果colorMap
		Map<String,String> colorMap = new HashMap<String,String>();
		//判断查询机构是否为空
		Object queryDepartmentName = pd.get("queryDepartmentName");
		//准备当前登录用户可查询数据范围
		pd.put("userDepartmentId", user.getDepartmentId());
		pd.put("acqName", "");
		if(null == queryDepartmentName) {
			//查询条件中的机构为空，则默认查询当前登录用户可查询范围内的所有数据
			pd.put("depName", "");
		} else {
			//查询条件中的机构不为空，则根据查询条件中的机构名称，查询对应的商户号
			pd.put("depName", queryDepartmentName.toString());
		}
		//minStr 为当前登录用户可查询数据范围
		String midStr = this.departmentService.queryMerchantNumByDepNameOrMerchantName(pd);
		pd.put("midStr", midStr);
		
		//判断查询日期是否为空
		Object queryDate = pd.get("queryDate");
		//准备查询统计报表的日期条件
		String queryDatePd = "";
		if(null == queryDate || "".equals(queryDate) || DateUtil.fomatDate(new Date(), "yyyy-MM-dd").equals(queryDate.toString())) {
			//查询条件中的日期为空，或查询日期等于当天日期，则按当前日期向前推进7天
			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0; i < 7 ;i++) {
				/**
				*java中对日期的加减操作
				*gc.add(1,-1)表示年份减一.
				*gc.add(2,-1)表示月份减一.
				*gc.add(3.-1)表示周减一.
				*gc.add(5,-1)表示天减一.
				*/
				GregorianCalendar gc=new GregorianCalendar(); 
				gc.setTime(new Date());
				gc.add(5, -i);
				gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
				paramMap.put(sf.format(gc.getTime()), 0);
				queryDatePd += sf.format(gc.getTime()) + "','";
				switch (i) {
					case 0 : 
						colorMap.put(sf.format(gc.getTime()), "8E468E");
						break;
					case 1 : 
						colorMap.put(sf.format(gc.getTime()), "D64646");
						break;
					case 2 : 
						colorMap.put(sf.format(gc.getTime()), "008E8E");
						break;
					case 3 : 
						colorMap.put(sf.format(gc.getTime()), "FF8E46");
						break;
					case 4 : 
						colorMap.put(sf.format(gc.getTime()), "8BBA00");
						break;
					case 5 : 
						colorMap.put(sf.format(gc.getTime()), "F6BD0F");
						break;
					case 6 : 
						colorMap.put(sf.format(gc.getTime()), "AFD8F8");
						break;
				}
			}
		} else {
			//查询条件中的日期不为空，则按当前日期向后推进7天
			SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 6; i >= 0 ;i--) {
				/**
				*java中对日期的加减操作
				*gc.add(1,+1)表示年份减一.
				*gc.add(2,+1)表示月份减一.
				*gc.add(3.+1)表示周减一.
				*gc.add(5,+1)表示天减一.
				*/
				GregorianCalendar gc=new GregorianCalendar(); 
				gc.setTime(DateUtil.fomatDate(queryDate.toString()));
				gc.add(5, +i);
				gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
				paramMap.put(sf.format(gc.getTime()), 0);
				queryDatePd += sf.format(gc.getTime()) + "','";
				switch (i) {
					case 0 : 
						colorMap.put(sf.format(gc.getTime()), "AFD8F8");
						break;
					case 1 : 
						colorMap.put(sf.format(gc.getTime()), "F6BD0F");
						break;
					case 2 : 
						colorMap.put(sf.format(gc.getTime()), "8BBA00");
						break;
					case 3 : 
						colorMap.put(sf.format(gc.getTime()), "FF8E46");
						break;
					case 4 : 
						colorMap.put(sf.format(gc.getTime()), "008E8E");
						break;
					case 5 : 
						colorMap.put(sf.format(gc.getTime()), "D64646");
						break;
					case 6 : 
						colorMap.put(sf.format(gc.getTime()), "8E468E");
						break;
			}
			}
		}
		queryDatePd = "'"+queryDatePd.substring(0,queryDatePd.length()-2);
		//将查询日期放到pageData中
		pd.put("queryDatePd", queryDatePd);
		List<FusionCharts> list = (List<FusionCharts>) dao1.findForList("FusionChartsMapper.querhWeekCharts", pd);
		if(null != list && list.size() > 0) {
			for(FusionCharts fc : list) {
				paramMap.put(fc.getDate(), fc.getMoney());
			}
		}
		/*String strXML = "";
		strXML += "<graph caption='营业额月报表' xAxisName='月份' yAxisName='营业额（万元）' decimalPrecision='0' formatNumberScale='0'>";
		strXML += "<set name='1' value='235' color='AFD8F8'/>";
		strXML += "<set name='2' value='857' color='F6BD0F'/>";
		strXML += "<set name='3' value='671' color='8BBA00'/>";
		strXML += "<set name='4' value='494' color='FF8E46'/>";
		strXML += "<set name='5' value='761' color='008E8E'/>";
		strXML += "<set name='6' value='960' color='D64646'/>";
		strXML += "<set name='7' value='629' color='8E468E'/>";
		strXML += "</graph>";*/
		//组装返回页面的信息
		//先按map中的key排序
		paramMap = MapSort.sortMapByKey(paramMap);
		String comment = "";
		for (String key : paramMap.keySet()) {  
			comment += "<set name='"+key.toString().substring(5,key.toString().length())+"' value='"+paramMap.get(key)+"' color='"+colorMap.get(key)+"'/>";
		}  
		resultString = resultString + comment;
		resultString = resultString + "</graph>";
		return resultString ;
	}
	
}
