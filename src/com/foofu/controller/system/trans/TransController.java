package com.foofu.controller.system.trans;

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
import org.springframework.web.servlet.ModelAndView;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.Page;
import com.foofu.entity.system.Dictionary;
import com.foofu.entity.system.Trans;
import com.foofu.entity.system.TransType;
import com.foofu.entity.system.User;
import com.foofu.service.system.dictionaries.DictionaryService;
import com.foofu.service.system.trans.TransService;
import com.foofu.service.system.trans.TransStatisticService;
import com.foofu.util.Const;
import com.foofu.util.Jurisdiction;
import com.foofu.util.ObjectExcelView;
import com.foofu.util.PageData;

@Controller
@RequestMapping(value="/trans")
public class TransController extends BaseController{
	String menuUrl = "trans/listTrans.do"; //菜单地址(权限用)
	String transExceptionMenuUrl = "trans/listExceptionTrans.do";
	@Resource(name="transService")
	private TransService transService;
	
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	
	@Resource(name="statisticService")
	private TransStatisticService transStatisticService;
	/**
	 * 交易查询
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listTrans")
	public ModelAndView listtrans(Page page) throws Exception {
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		/**
		 * 查询条件        
		 * 机构名称        depName
		 * 商户名称        acqName      
		 * 商户号             mid 
		 * 终端号             tid  
		 * 交易类型        txnId      
		 * 批次号             batchNo  
		 * 卡号                  pan
		 * 开始时间        transStartDate      
		 * 结束时间        transEndDate       
		 * 发卡行             bankNo  
		 */
		//当前登录用户所属机构Id
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("userDepartmentId", user.getDepartmentId());
		//记录页面输入的卡号
		String elementPan = pd.getString("pan");
		page.setPd(pd);
		//获得交易列表
		//传入查询SQLID
		pd.put("sqlId", "TransMapper.dllTranslistPage");
		List<Trans> transList = transService.listAddTrans(page);
		//发卡行下拉框
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type", Const.TRANS_DIC_BANK);
		List<Dictionary> bankList = dictionaryService.listDictionary(condition);
		mv.addObject("bankList", bankList);
		//交易类型
		List<TransType> txnList = transStatisticService.findAllTransType();
		//获取模块按钮权限
		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
		mv.addObject("txnList", txnList);
		mv.setViewName("system/trans/transList");
		mv.addObject("transList", transList);
		pd.put("pan", elementPan);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 异常查询
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listExceptionTrans")
	public ModelAndView listExceptionTrans(Page page) throws Exception {
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		/**
		 * 查询条件        
		 * 机构名称        depName
		 * 商户名称        acqName      
		 * 商户号             mid 
		 * 终端号             tid  
		 * 交易类型        txnId      
		 * 批次号             batchNo  
		 * 卡号                  pan
		 * 开始时间        transStartDate      
		 * 结束时间        transEndDate       
		 * 发卡行             bankNo  
		 */
		//当前登录用户所属机构Id
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("userDepartmentId", user.getDepartmentId());
		//记录页面输入的卡号
		String elementPan = pd.getString("pan");
		page.setPd(pd);
		//获得交易列表
		//传入查询SQLID
		pd.put("sqlId", "TransMapper.ExceptionTransDllTranslistPage");
		List<Trans> transList = transService.listAddTrans(page);
		//发卡行下拉框
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type", Const.TRANS_DIC_BANK);
		List<Dictionary> bankList = dictionaryService.listDictionary(condition);
		mv.addObject("bankList", bankList);
		//交易类型
		List<TransType> txnList = transStatisticService.findAllTransType();
		//获取模块按钮权限
		Jurisdiction.buttonJurisdictionForPage(transExceptionMenuUrl,pd);
		mv.addObject("txnList", txnList);
		mv.setViewName("system/trans/listExceptionTrans");
		mv.addObject("transList", transList);
		pd.put("pan", elementPan);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 导出查询交易信息到Excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			/**
			 * 查询条件        
			 * 机构名称        depName
			 * 商户名称        acqName      
			 * 商户号             mid 
			 * 终端号             tid  
			 * 交易类型        txnId      
			 * 批次号             batchNo  
			 * 卡号                  pan
			 * 开始时间        transStartDate      
			 * 结束时间        transEndDate       
			 * 发卡行             bankNo  
			 */
			//当前登录用户所属机构Id
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pd.put("userDepartmentId", user.getDepartmentId());
			page.setPd(pd);
			//获得交易列表
			//传入查询SQLID
			pd.put("sqlId", "TransMapper.dllTransForExcel");
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			
			titles.add("序号");  		//1
			titles.add("机构名称");  	//2
			titles.add("商户名称");  	//3	
			titles.add("商户号"); 		//4
			titles.add("卡号");	        //5
			titles.add("发卡行");		//6	
			titles.add("交易类型");		//7
			titles.add("批次号");		//8
			titles.add("授权码");		//9
			titles.add("终端号");	    //10
			titles.add("参考号");       //11
			titles.add("流水号");       //12
			titles.add("交易金额");     //13
			titles.add("应答码");       //14
			titles.add("交易时间");     //15
			titles.add("交易状态");     //16
			
			dataMap.put("titles", titles);
			
			List<Trans> transList = transService.listAddTrans(page);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<transList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", (i+1)+"");		                             //1   序号
				vpd.put("var2", transList.get(i).getDepartmentName() == null ? "" : transList.get(i).getDepartmentName());			             //2   机构名称
				vpd.put("var3", transList.get(i).getAcpName() == null ? "" : transList.get(i).getAcpName());			             //3   商户名称
				vpd.put("var4", transList.get(i).getMid() == null ? "" : transList.get(i).getMid());			             //4   商户号  
				vpd.put("var5", transList.get(i).getPan() == null ? "" : transList.get(i).getPan());			             //5   卡号    
				vpd.put("var6", transList.get(i).getBankNo() == null ? "" : transList.get(i).getBankNo());			             //6   发卡行
				vpd.put("var7", transList.get(i).getTxnName() == null ? "" : transList.get(i).getTxnName());			             //7   交易类型
				vpd.put("var8", transList.get(i).getBatchNo() == null ? "" : transList.get(i).getBatchNo());			             //8   批次号
				vpd.put("var9", transList.get(i).getAuthCode() == null ? "" : transList.get(i).getAuthCode());			             //9  授权码
				vpd.put("var10", transList.get(i).gettid() == null ? "" : transList.get(i).gettid());			             //10  终端号
				vpd.put("var11", transList.get(i).getRrN() == null ? "" : transList.get(i).getRrN());			             //11  参考号
				vpd.put("var12", transList.get(i).getCustomId1() == null ? "" : transList.get(i).getCustomId1());			             //12  流水号
				vpd.put("var13", transList.get(i).getTxnAmt() == 0.00 ? "" : String.valueOf(transList.get(i).getTxnAmt()));			             //13  交易金额
				vpd.put("var14", transList.get(i).getTxnId() == null ? "" : transList.get(i).getTxnId());			             //14  应答码
				vpd.put("var15", transList.get(i).getTranDate() == null ? "" : transList.get(i).getTranDate());			             //15  交易时间
				vpd.put("var16", transList.get(i).getTranStatus() == null ? "" : transList.get(i).getTranStatus());			             //16  交易状态
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
	 * 导出异常交易信息到Excel
	 * @return
	 */
	@RequestMapping(value="/exceptionTrans")
	public ModelAndView exceptionTrans(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			/**
			 * 查询条件        
			 * 机构名称        depName
			 * 商户名称        acqName      
			 * 商户号             mid 
			 * 终端号             tid  
			 * 交易类型        txnId      
			 * 批次号             batchNo  
			 * 卡号                  pan
			 * 开始时间        transStartDate      
			 * 结束时间        transEndDate       
			 * 发卡行             bankNo  
			 */
			//当前登录用户所属机构Id
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			pd.put("userDepartmentId", user.getDepartmentId());
			page.setPd(pd);
			//获得交易列表
			//传入查询SQLID
			pd.put("sqlId", "TransMapper.exceptionTransDllTransForExcel");
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			
			titles.add("序号");  		//1
			titles.add("机构名称");  	//2
			titles.add("商户名称");  	//3	
			titles.add("商户号"); 		//4
			titles.add("卡号");	        //5
			titles.add("发卡行");		//6	
			titles.add("交易类型");		//7
			titles.add("批次号");		//8
			titles.add("授权码");		//9
			titles.add("终端号");	    //10
			titles.add("参考号");       //11
			titles.add("流水号");       //12
			titles.add("交易金额");     //13
			titles.add("应答码");       //14
			titles.add("交易时间");     //15
			titles.add("交易状态");     //16
			
			dataMap.put("titles", titles);
			
			List<Trans> transList = transService.listAddTrans(page);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<transList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", (i+1)+"");		                             //1   序号
				vpd.put("var2", transList.get(i).getDepartmentName() == null ? "" : transList.get(i).getDepartmentName());			             //2   机构名称
				vpd.put("var3", transList.get(i).getAcpName() == null ? "" : transList.get(i).getAcpName());			             //3   商户名称
				vpd.put("var4", transList.get(i).getMid() == null ? "" : transList.get(i).getMid());			             //4   商户号  
				vpd.put("var5", transList.get(i).getPan() == null ? "" : transList.get(i).getPan());			             //5   卡号    
				vpd.put("var6", transList.get(i).getBankNo() == null ? "" : transList.get(i).getBankNo());			             //6   发卡行
				vpd.put("var7", transList.get(i).getTxnName() == null ? "" : transList.get(i).getTxnName());			             //7   交易类型
				vpd.put("var8", transList.get(i).getBatchNo() == null ? "" : transList.get(i).getBatchNo());			             //8   批次号
				vpd.put("var9", transList.get(i).getAuthCode() == null ? "" : transList.get(i).getAuthCode());			             //9  授权码
				vpd.put("var10", transList.get(i).gettid() == null ? "" : transList.get(i).gettid());			             //10  终端号
				vpd.put("var11", transList.get(i).getRrN() == null ? "" : transList.get(i).getRrN());			             //11  参考号
				vpd.put("var12", transList.get(i).getCustomId1() == null ? "" : transList.get(i).getCustomId1());			             //12  流水号
				vpd.put("var13", transList.get(i).getTxnAmt() == 0.00 ? "" : String.valueOf(transList.get(i).getTxnAmt()));			             //13  交易金额
				vpd.put("var14", transList.get(i).getTxnId() == null ? "" : transList.get(i).getTxnId());			             //14  应答码
				vpd.put("var15", transList.get(i).getTranDate() == null ? "" : transList.get(i).getTranDate());			             //15  交易时间
				vpd.put("var16", transList.get(i).getTranStatus() == null ? "" : transList.get(i).getTranStatus());			             //16  交易状态
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
