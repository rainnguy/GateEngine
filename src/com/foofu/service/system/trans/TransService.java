package com.foofu.service.system.trans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.foofu.dao.DaoSupport1;
import com.foofu.entity.Page;
import com.foofu.entity.system.Trans;
import com.foofu.service.system.department.DepartmentService;
import com.foofu.service.system.dictionaries.DictionaryService;
import com.foofu.util.TransUtil;

@Service("transService")
public class TransService {

	//连接第2个数据源（DaoSupport1）
	@Resource(name = "daoSupport1")
	private DaoSupport1 dao;
	
	@Resource(name="dictionaryService")
	private DictionaryService dictionaryService;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	/**
	 * 交易查询
	 */
	@SuppressWarnings("unchecked")
	public List<Trans> listAddTrans(Page pd) throws Exception{
		List<Trans> transList = new ArrayList<Trans>();
		
		//获得机构名称，有机构名称，先查询出机构名称对应的商户号
		String midStr = this.departmentService.queryMerchantNumByDepNameOrMerchantName(pd.getPd());
		pd.getPd().put("midStr", midStr);
		//加密卡号
		String oldPan = "";//记录用户输入的卡号
		if(null != pd.getPd().get("pan") && !"".equals(pd.getPd().get("pan").toString())) {
			oldPan = pd.getPd().get("pan").toString();
			String pan = TransUtil.panEncryption(pd.getPd().get("pan").toString());
			pd.getPd().put("pan", pan);
		}
		if(midStr.isEmpty()) {
			return transList;
		}
		transList = (List<Trans>) dao.findForList(pd.getPd().getString("sqlId"), pd);
		if(null != transList && transList.size() > 0) {
			//交易状态查询map
			Map<String,Object> transMap = new HashMap<String,Object>();
			transMap.put("type", "tran_status");
			//发卡行查询map
			Map<String,Object> bankMap = new HashMap<String,Object>();
			bankMap.put("type", "bank");
			for(Trans trans : transList) {
				//查询商户名称
				trans.setAcpName(this.departmentService.queryDepNameByMerchantNum(trans.getMid()));
				//交易状态
				transMap.put("value", trans.getTranStatus());
				trans.setTranStatus(dictionaryService.queryDictionaryNameByTypeAndValue(transMap) == null ? "其他" : dictionaryService.queryDictionaryNameByTypeAndValue(transMap).getName());
				//发卡行
				bankMap.put("value", trans.getBankNo());
				trans.setBankNo(dictionaryService.queryDictionaryNameByTypeAndValue(bankMap) == null ? "其他" : dictionaryService.queryDictionaryNameByTypeAndValue(bankMap).getName());
				//卡号显示前4后6
				trans.setPan(TransUtil.panDecryptHideCenter(trans.getPan().trim(), 6, 4));
				//根据商户号取商户名称
				trans.setDepartmentName(departmentService.queryDepNameByMerchantNum(trans.getMid()) == null ? "" : departmentService.queryDepNameByMerchantNum(trans.getMid()));
			}
		}
		pd.getPd().put("pan", oldPan);
		return transList;
	}
}
