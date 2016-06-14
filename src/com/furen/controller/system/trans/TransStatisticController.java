package com.furen.controller.system.trans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.furen.controller.base.BaseController;
import com.furen.entity.Page;
import com.furen.entity.system.Department;
import com.furen.entity.system.Dictionary;
import com.furen.entity.system.TransStatistic;
import com.furen.entity.system.TransType;
import com.furen.entity.system.User;
import com.furen.service.system.department.DepartmentService;
import com.furen.service.system.dictionaries.DictionaryService;
import com.furen.service.system.trans.TransStatisticService;
import com.furen.util.Const;
import com.furen.util.Jurisdiction;
import com.furen.util.ObjectExcelView;
import com.furen.util.PageData;

@Controller
@RequestMapping(value="/statistic")
public class TransStatisticController extends BaseController{
	String menuUrl = "statistic/transStatistic.do"; //统计地址(权限用)
	@Resource(name="statisticService")
	private TransStatisticService transStatisticService;
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	/**
	 * 跳转统计信息画面
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/transStatistic.do")
	public ModelAndView listStatistic(Page page) throws Exception {
		//1.定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		//2.获取查询条件
		String depName = pd.getString("depName");
		String acqName = pd.getString("acqName");
		String mid = pd.getString("mid");
		String tid = pd.getString("tid");
		String batchNo = pd.getString("batchNo");
		String tranStartDate = pd.getString("tranStartDate");
		String tranEndDate = pd.getString("tranEndDate");
		String pan = pd.getString("pan");
		String txnId = pd.getString("txnId");
		if(null == txnId || "".equals(txnId.trim())) {
			txnId = "6";
		}
		pd.put("txnId", txnId);
		pd.put("depName", depName==null||depName==""?"":depName.trim());
		pd.put("acqName", acqName==null||acqName==""?"":acqName.trim());
		pd.put("mid", mid==null||mid==""?"":mid.trim());
		pd.put("tid", tid==null||tid==""?"":tid.trim());
		pd.put("batchNo", batchNo==null||batchNo==""?"":batchNo.trim());
		if(tranStartDate==null||tranStartDate==""){
			pd.put("tranStartDate", new SimpleDateFormat("yyyy-MM-dd 00:00").format(new Date()));
		}
		if(tranEndDate==null||tranEndDate==""){
			pd.put("tranEndDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		}
		pd.put("pan", pan==null||pan==""?"":pan.trim());
		
		//获取session中的用户信息
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("userDepartmentId", user.getDepartmentId());
		
		//根据公司名称和商户名称查询商户号串
		if((pd.getString("acqName")!=null && !pd.getString("acqName").isEmpty())||(pd.getString("depName") !=null && !pd.getString("depName").isEmpty())){
			String queryMids = departmentService.queryMerchantNumByDepNameOrMerchantName(pd);
			pd.put("queryMids", queryMids);
		}
		
		int showNav = 1;
		if((pd.getString("acqName")!=null && !pd.getString("acqName").isEmpty())||
				(pd.getString("depName") !=null && !pd.getString("depName").isEmpty())||
				(pd.getString("mid")!=null && !pd.getString("mid").isEmpty())||
				(pd.getString("tid")!=null && !pd.getString("tid").isEmpty())||
				(pd.getString("batchNo")!=null && !pd.getString("batchNo").isEmpty())||
				(pd.getString("pan")!=null && !pd.getString("pan").isEmpty())){
			showNav=0;
		}
		pd.put("showNav", showNav+"");
		page.setPd(pd);
		//查询条件-end-
		
		//3.获取顶部机构导航列表
		//获取页面当前机构导航的参数并取得最新的导航结果
		if(Integer.parseInt(pd.getString("showNav"))==1){
			String id = pd.getString("id");
			String parentId = pd.getString("parentId");
			if(null == id) {
				id = String.valueOf(user.getDepartmentId());
			}
			pd.put("id", id);
			List<Department> listDepForNavigation = new ArrayList<Department>();
			queryListDepForNavigation(Integer.parseInt(id),user.getDepartmentId(),listDepForNavigation);
			Collections.reverse(listDepForNavigation);
			if(null == parentId || parentId.equals("0")) {
				pd.put("showAddForPage", false);
			} else {
				pd.put("showAddForPage", true);
			}
			
			if(null == parentId) {
				pd.put("parentId", user.getParentDepartmentId());
			}
			mv.addObject("listDepForNavigation",listDepForNavigation);
		}
		
		//4.获取统计数据
		//获取需要统计的部门
		List<TransStatistic> transStatisticListToS = transStatisticService.findAllStatisticDep(page);
		//交易统计结果
		List<TransStatistic> transStatisticList = transStatisticService.statisticTransByCondition(page,transStatisticListToS);
		
		//5.获取下拉框型查询条件的数据
		//交易类型
		List<TransType> txnList = transStatisticService.findAllTransType();
		//银行
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type", Const.TRANS_DIC_BANK);
		List<Dictionary> bankList = dictionaryService.listDictionary(condition);
		
		//6.准备页面返回数据并返回
		//获得页面按钮
		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
		mv.setViewName("system/trans/transStatistic");
		mv.addObject("transStatisticList", transStatisticList);
		mv.addObject("txnList", txnList);
		mv.addObject("bankList", bankList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/*
	 * 导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//2.获取查询条件
			String depName = pd.getString("depName");
			String acqName = pd.getString("acqName");
			String mid = pd.getString("mid");
			String tid = pd.getString("tid");
			String batchNo = pd.getString("batchNo");
			String tranStartDate = pd.getString("tranStartDate");
			String tranEndDate = pd.getString("tranEndDate");
			String pan = pd.getString("pan");
			String txnId = pd.getString("txnId");
			if(null == txnId || "".equals(txnId.trim())) {
				txnId = "6";
			}
			pd.put("txnId", txnId);
			pd.put("depName", depName==null||depName==""?"":depName.trim());
			pd.put("acqName", acqName==null||acqName==""?"":acqName.trim());
			pd.put("mid", mid==null||mid==""?"":mid.trim());
			pd.put("tid", tid==null||tid==""?"":tid.trim());
			pd.put("batchNo", batchNo==null||batchNo==""?"":batchNo.trim());
			if(tranStartDate==null||tranStartDate==""){
				pd.put("tranStartDate", new SimpleDateFormat("yyyy-MM-dd 00:00").format(new Date()));
			}
			if(tranEndDate==null||tranEndDate==""){
				pd.put("tranEndDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
			}
			pd.put("pan", pan==null||pan==""?"":pan.trim());
			
			//获取session中的用户信息
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pd.put("userDepartmentId", user.getDepartmentId());
			
			//根据公司名称和商户名称查询商户号串
			if((pd.getString("acqName")!=null && !pd.getString("acqName").isEmpty())||(pd.getString("depName") !=null && !pd.getString("depName").isEmpty())){
				String queryMids = departmentService.queryMerchantNumByDepNameOrMerchantName(pd);
				pd.put("queryMids", queryMids);
			}
			int showNav = 1;
			if((pd.getString("acqName")!=null && !pd.getString("acqName").isEmpty())||
					(pd.getString("depName") !=null && !pd.getString("depName").isEmpty())||
					(pd.getString("mid")!=null && !pd.getString("mid").isEmpty())||
					(pd.getString("tid")!=null && !pd.getString("tid").isEmpty())||
					(pd.getString("txnId")!=null && !pd.getString("txnId").isEmpty())||
					(pd.getString("batchNo")!=null && !pd.getString("batchNo").isEmpty())||
					(pd.getString("pan")!=null && !pd.getString("pan").isEmpty())||
					(pd.getString("bankNo")!=null && !pd.getString("bankNo").isEmpty())){
				showNav=0;
			}
			pd.put("showNav", showNav+"");
			page.setPd(pd);
			//查询条件-end-
			
			//4.获取统计数据
			//获取需要统计的部门
			List<TransStatistic> transStatisticListToS = transStatisticService.findAllStatisticDepForExcel(page);
			//交易统计结果
			List<TransStatistic> transStatisticList = transStatisticService.statisticTransByCondition(page,transStatisticListToS);
			
			//封装要导出的数据
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			
			titles.add("序号");  		//1
			titles.add("机构");  		//2
			titles.add("商户号"); 		//3
			titles.add("商户名称"); 		//4
			titles.add("交易笔数");	    //5
			titles.add("交易金额");	    //6
			dataMap.put("titles", titles);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<transStatisticList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", (i+1)+"");		                                    //1
				vpd.put("var2", transStatisticList.get(i).getDepartmentName());	    //2
				vpd.put("var3", transStatisticList.get(i).getMid());			    //3
				vpd.put("var4", transStatisticList.get(i).getAcqName());	        //4
				vpd.put("var5", transStatisticList.get(i).getTransNum()+"");	    //5
				vpd.put("var6", transStatisticList.get(i).getTransAggregate()+"");  //6
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 获得顶部导航列表
	 * @param parentId
	 */
	public void queryListDepForNavigation(int id,int userDepartmentId,List<Department> listDepForNavigation) {
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			Department department = departmentService.queryListDepForNavigation(paramMap);
			if(department != null){
				listDepForNavigation.add(department);
				if(department.getId() != userDepartmentId) {
					int parentId = department.getParentId();
					this.queryListDepForNavigation(parentId,userDepartmentId,listDepForNavigation);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	
}