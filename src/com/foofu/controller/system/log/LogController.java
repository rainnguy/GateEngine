package com.foofu.controller.system.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.Page;
import com.foofu.entity.system.Dictionary;
import com.foofu.entity.system.User;
import com.foofu.service.system.dictionaries.DictionaryService;
import com.foofu.service.system.log.LogService;
import com.foofu.util.AppUtil;
import com.foofu.util.Const;
import com.foofu.util.PageData;

/** 
 * 类名称：UserController
 * 创建人：xiajj 
 * 创建时间：2015年11月10日
 * @version
 */
@Controller
@RequestMapping(value="/log")
public class LogController extends BaseController {
	String menuUrl = "log/listLog.do"; //机构地址(权限用)
	
	@Resource(name="logService")
	private LogService logService;
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	
	/**
	 * 日志列表
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listLog")
	public ModelAndView listLog(Page page) throws Exception {
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String keyWord = pd.getString("keyWord");
		if(null != keyWord && !"".equals(keyWord)){
			keyWord = keyWord.trim();
			pd.put("keyWord", keyWord);
		}
		
		String logStart = pd.getString("logStart");
		String logEnd = pd.getString("logEnd");
		
		if(logStart != null && !"".equals(logStart)){
			logStart = logStart+" 00:00:00";
			pd.put("logStart", logStart);
		}
		if(logEnd != null && !"".equals(logEnd)){
			logEnd = logEnd+" 00:00:00";
			pd.put("logEnd", logEnd);
		}
		//获取当前登录用户的部门id
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int currUdepId = user.getDepartmentId();
		pd.put("currUdepId", currUdepId);
		page.setPd(pd);
		
		//获得log列表
		List<PageData>	logList = logService.listLogs(page);
		//获得页面按钮
		mv.addObject("logList", logList);
		mv.addObject("pd", pd);
//		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
		mv.setViewName("system/log/log_list");
		return mv;
	}
	
	@RequestMapping("/getSystemType.do")
	@ResponseBody
	public Object getSystemType(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		Map<String,Object> condition = new HashMap<String,Object>();
		List<Dictionary> systemTypeList = new ArrayList<Dictionary>();
		try{
			condition = new HashMap<String,Object>();
			condition.put("type", Const.LOG_DIC_SYSTEM_TYPE);
			systemTypeList = dictionaryService.listDictionary(condition);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("systemTypeList", systemTypeList);
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
	@RequestMapping("/getOperatorType.do")
	@ResponseBody
	public Object getOperatorType(){
		Map<String,Object> map = new HashMap<String,Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		Map<String,Object> condition = new HashMap<String,Object>();
		List<Dictionary> operatorTypeList = new ArrayList<Dictionary>();
		try{
			pd = this.getPageData();
			if(pd.getString("systemType").equals("1")){
				condition = new HashMap<String,Object>();
				condition.put("type", Const.LOG_DIC_OPERATOR_TYPE_DATA);
				operatorTypeList = dictionaryService.listDictionary(condition);
			}else if(pd.getString("systemType").equals("2")){
				condition = new HashMap<String,Object>();
				condition.put("type", Const.LOG_DIC_OPERATOR_TYPE_LOGIN);
				operatorTypeList = dictionaryService.listDictionary(condition);
			}else{
				condition.put("type", Const.LOG_DIC_OPERATOR_TYPE_DATA);
				List<Dictionary> operatorTypeListD = dictionaryService.listDictionary(condition);
				condition = new HashMap<String,Object>();
				condition.put("type", Const.LOG_DIC_OPERATOR_TYPE_LOGIN);
				List<Dictionary> operatorTypeListL = dictionaryService.listDictionary(condition);
				operatorTypeList.addAll(operatorTypeListD);
				operatorTypeList.addAll(operatorTypeListL);
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		map.put("operatorTypeList", operatorTypeList);
		map.put("result", errInfo);				//返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	
}
