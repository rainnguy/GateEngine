package com.furen.service.system.trans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.dao.DaoSupport1;
import com.furen.entity.Page;
import com.furen.entity.system.TransStatistic;
import com.furen.entity.system.TransType;
import com.furen.util.TransUtil;

@Service("statisticService")
public class TransStatisticService {

	//连接自己系统的数据源
	@Resource(name = "daoSupport")
	private DaoSupport localDao;

	//连接云融能源的oracle数据库
	@Resource(name = "daoSupport1")
	private DaoSupport1 YRDao;

	/**
	 * 获取交易统计数据
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<TransStatistic> statisticTransByCondition(Page page, List<TransStatistic> transStatisticListToS) throws Exception {
		List<TransStatistic> transStatisticedList = new ArrayList<TransStatistic>();
		//加密卡号
		String oldPan = "";//记录用户输入的卡号
		if(null != page.getPd().get("pan") && !"".equals(page.getPd().get("pan").toString())) {
			oldPan = page.getPd().get("pan").toString();
			String pan = TransUtil.panEncryption(page.getPd().get("pan").toString());
			page.getPd().put("pan", pan);
		}
		for(TransStatistic transStatisticToS : transStatisticListToS){
			//条件追加当前需要统计的部门及其所属的商户号
			page.getPd().put("allMids", transStatisticToS.getAllMids());
			//获取交易笔数
			TransStatistic statisitcForNum = (TransStatistic) YRDao.findForObject("TransStatisticMapper.calTransNumByCondition", page);
			transStatisticToS.setTransNum(statisitcForNum.getTransNum());
			//获取交易金额
			TransStatistic statisitcForAggreate =  (TransStatistic) YRDao.findForObject("TransStatisticMapper.calTransAggregateByCondition", page);
			if(statisitcForAggreate == null || statisitcForAggreate.getTransAggregate() == null){
				transStatisticToS.setTransAggregate(new BigDecimal(0.0));
			}else{
				transStatisticToS.setTransAggregate(statisitcForAggreate.getTransAggregate());
			}
			//结果添加到统计好的list中
			transStatisticedList.add(transStatisticToS);
		}
		page.getPd().put("pan", oldPan);
		return transStatisticedList;
	}

	/**
	 * 获取所有的交易类型
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<TransType> findAllTransType() throws Exception {
		return (List<TransType>) YRDao.findForList("TransStatisticMapper.listAllTransType", null);
	}

	/**
	 * 获取所有需要统计的基础信息分页查询用
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<TransStatistic> findAllStatisticDep(Page page) throws Exception {
		return (List<TransStatistic>) localDao.findForList("TransStatisticMapper.listPageAllStatisticDep",page);
	}
	
	/**
	 * 获取所有需要统计的基础信息导出用
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<TransStatistic> findAllStatisticDepForExcel(Page page) throws Exception {
		return (List<TransStatistic>) localDao.findForList("TransStatisticMapper.listAllStatisticDep",page);
	}

}