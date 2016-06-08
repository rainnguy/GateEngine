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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.foofu.controller.base.BaseController;
import com.foofu.entity.Page;
import com.foofu.entity.system.Dictionary;
import com.foofu.entity.system.TransAccount;
import com.foofu.entity.system.TransType;
import com.foofu.entity.system.User;
import com.foofu.service.system.dictionaries.DictionaryService;
import com.foofu.service.system.log.LogService;
import com.foofu.service.system.trans.TransAccountService;
import com.foofu.service.system.trans.TransStatisticService;
import com.foofu.util.Const;
import com.foofu.util.FileUpload;
import com.foofu.util.Jurisdiction;
import com.foofu.util.ObjectExcelViewForTrans;
import com.foofu.util.PageData;
import com.foofu.util.PathUtil;

@Controller
@RequestMapping(value="/account")
public class TransAccountController extends BaseController{
	String menuUrl = "account/listTransAccount.do"; //菜单地址(权限用)
	@Resource(name="transAccountService")
	private TransAccountService transAccountService;
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	@Resource(name="statisticService")
	private TransStatisticService transStatisticService;
	@Resource(name="logService")
	private LogService logService;
	
	/**
	 * 交易对账
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/listTransAccount")
	public ModelAndView listtrans(Page page) throws Exception {
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		//存放前台输入的卡号
		String elementPan = pd.getString("pan");
		//当前登录用户所属机构Id
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("userDepartmentId", user.getDepartmentId());
		String txnId = pd.getString("txnId");
		if(null == txnId || "".equals(txnId.trim())) {
			txnId = "6";
		}
		pd.put("txnId", txnId);
		//传入查询SQLID
		pd.put("sqlId", "TransAccountMapper.transAccountlistPage");
		//获得交易对账列表
		List<TransAccount> transAccountList = transAccountService.listTransAccount(page);
		mv.addObject("transAccountList", transAccountList);
		//发卡行下拉框
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("type", Const.TRANS_DIC_BANK);
		List<Dictionary> bankList = dictionaryService.listDictionary(condition);
		mv.addObject("bankList", bankList);
		//交易类型
		List<TransType> txnList = transStatisticService.findAllTransType();
		mv.addObject("txnList", txnList);
		//设置视图
		mv.setViewName("system/trans/transAccount");
		//获取模块按钮权限
		Jurisdiction.buttonJurisdictionForPage(menuUrl,pd);
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
		//定义返回对象
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		//当前登录用户所属机构Id
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("userDepartmentId", user.getDepartmentId());
		String txnId = pd.getString("txnId");
		if(null == txnId || "".equals(txnId.trim())) {
			txnId = "6";
		}
		pd.put("txnId", txnId);
		//传入查询SQLID
		pd.put("sqlId", "TransAccountMapper.transAccountForExcel");
		try {
			List<String> titleItem = new ArrayList<String>();
			List<String> titleDetail = new ArrayList<String>();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			titleItem.add("前置端");//1
			titleItem.add("银行端");//2
			dataMap.put("titleItem", titleItem);
			
			titleDetail.add("序号");  		    //1
			titleDetail.add("流水号");  	    //2
			titleDetail.add("商户名称");  	    //3	
			titleDetail.add("卡号"); 		    //4
			titleDetail.add("终端号");	        //5
			titleDetail.add("交易金额");		//6	
			titleDetail.add("交易时间");		//7
			titleDetail.add("交易类型");		//8
			titleDetail.add("流水号");  	    //9
			titleDetail.add("商户名称");  	    //10	
			titleDetail.add("卡号"); 		    //11
			titleDetail.add("终端号");	        //12
			titleDetail.add("交易金额");		//13
			titleDetail.add("交易时间");		//14
			titleDetail.add("交易类型");		//15
			
			dataMap.put("titleDetail", titleDetail);
			
			//获得交易对账列表
			List<TransAccount> transAccountList = transAccountService.listTransAccount(page);
			
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<transAccountList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", (i+1)+"");	                             //1   序号
				vpd.put("var2", transAccountList.get(i).getSystrace() == null ? "" : transAccountList.get(i).getSystrace());			 //2   前置端 流水号
				vpd.put("var3", transAccountList.get(i).getAcpName() == null ? "" : transAccountList.get(i).getAcpName());			     //3   前置端 商户名称
				vpd.put("var4", transAccountList.get(i).getPan() == null ? "" : transAccountList.get(i).getPan());			             //4   前置端 卡号
				vpd.put("var5", transAccountList.get(i).getTid() == null ? "" : transAccountList.get(i).getTid());			             //5   前置端 终端号    
				vpd.put("var6", transAccountList.get(i).getTxnAmt() == null ? "" : transAccountList.get(i).getTxnAmt()+"");			     //6   前置端 交易金额
				vpd.put("var7", transAccountList.get(i).getTranDate() == null ? "" : transAccountList.get(i).getTranDate());			 //7   前置端 交易时间
				vpd.put("var8", transAccountList.get(i).getTxnName() == null ? "" : transAccountList.get(i).getTxnName());               //8   前置端 交易类型
				vpd.put("var9", transAccountList.get(i).getSystraceB() == null ? "" : transAccountList.get(i).getSystraceB());			 //9   银行端 流水号
				vpd.put("var10", transAccountList.get(i).getAcpNameB() == null ? "" : transAccountList.get(i).getAcpNameB());			     //10   银行端 商户名称
				vpd.put("var11", transAccountList.get(i).getPanB() == null ? "" : transAccountList.get(i).getPanB());			             //11   银行端 卡号  
				vpd.put("var12", transAccountList.get(i).getTidB() == null ? "" : transAccountList.get(i).getTidB());			             //12   银行端 终端号    
				vpd.put("var13", transAccountList.get(i).getTxnAmtB() == null ? "" : transAccountList.get(i).getTxnAmtB()+"");			     //13   银行端 交易金额
				vpd.put("var14", transAccountList.get(i).getTranDateB() == null ? "" : transAccountList.get(i).getTranDateB());			 //14   银行端 交易时间
				vpd.put("var15", transAccountList.get(i).getTxnNameB() == null ? "" : transAccountList.get(i).getTxnNameB());              //15   银行端 交易类型
				//对账失败
				if(!vpd.get("var2").equals(vpd.get("var9")) || !vpd.get("var4").equals(vpd.get("var11")) || 
						!vpd.get("var5").equals(vpd.get("var12")) || !vpd.get("var6").equals(vpd.get("var13")) || 
						!vpd.get("var7").equals(vpd.get("var14"))){
					vpd.put("var0", 0+"");
				}else{//对账成功
					vpd.put("var0", 1+"");
				}
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			
			ObjectExcelViewForTrans erv = new ObjectExcelViewForTrans();					//执行excel操作
			mv = new ModelAndView(erv,dataMap);
			//日志
			logService.insertOneLog(menuUrl,
					Const.LOG_SYSTEM_TYPE_DATA,
					Const.LOG_OPERATOR_TYPE_EXPORT,
					"导出对账信息");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readTxt")
	public ModelAndView readTxt(
			@RequestParam(value="excel",required=false) MultipartFile file
			) {
		ModelAndView mv = this.getModelAndView();
		String result = "error";
		try {
			PageData pd = new PageData();
			if (null != file && !file.isEmpty()) {
				String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
				//获得文件名
				
				String fileName =  FileUpload.fileUp(file, filePath);							//执行上传
				result = transAccountService.readTxt(filePath, fileName);	    //执行读TXT操作
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		mv.addObject("msg",result);
		mv.setViewName("save_result");
		return mv;
	}
}
