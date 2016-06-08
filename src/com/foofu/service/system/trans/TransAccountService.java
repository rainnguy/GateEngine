package com.foofu.service.system.trans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.foofu.dao.DaoSupport;
import com.foofu.dao.DaoSupport1;
import com.foofu.entity.Page;
import com.foofu.entity.system.BankReco;
import com.foofu.entity.system.TransAccount;
import com.foofu.service.system.department.DepartmentService;
import com.foofu.util.TransUtil;


@Service("transAccountService")
public class TransAccountService {

	//连接第1个数据源（DaoSupport）
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//连接第2个数据源（DaoSupport1）
	@Resource(name = "daoSupport1")
	private DaoSupport1 dao1;
	
	@Resource(name="departmentService")
	private DepartmentService departmentService;

	/**
	 * 交易对账分页查询
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<TransAccount> listTransAccount(Page pd) throws Exception {
		@SuppressWarnings("unchecked")
		List<TransAccount> transAccountList = new ArrayList<TransAccount>();
		//获得机构名称，有机构名称，先查询出机构名称对应的商户号
		String midStr = this.departmentService.queryMerchantNumByDepNameOrMerchantName(pd.getPd());
		pd.getPd().put("midStr", midStr);
		String oldPan = "";
		if(null != pd.getPd().get("pan") && !"".equals(pd.getPd().get("pan").toString())) {
			oldPan = pd.getPd().get("pan").toString();
			String pan = TransUtil.panEncryption(pd.getPd().get("pan").toString());
			pd.getPd().put("pan", pan);
		}
		if(midStr.isEmpty()) {
			return transAccountList;
		}
		//从histrans中查出历史交易
		transAccountList = (List<TransAccount>) dao1.findForList(pd.getPd().getString("sqlId"), pd);
		
		if(null != transAccountList && transAccountList.size() > 0) {
			for(int index=0;index < transAccountList.size();index++){
				TransAccount transAccount = transAccountList.get(index);
				//奇数行默认设置为normal
				if(index%2==0){
					transAccount.setStyleClass("normal");
				}
				//卡号显示前4后6
				transAccount.setPan(TransUtil.panDecryptHideCenter(transAccount.getPan().trim(), 6, 4));
				//根据商户号取商户名称
				transAccount.setAcpName(departmentService.queryDepNameByMerchantNum(transAccount.getMid()) == null ? "" : departmentService.queryDepNameByMerchantNum(transAccount.getMid()));
				//根据流水号从bank_reconciliation查出银行的对账交易
				BankReco transAccountB = (BankReco) dao.findForObject("BankRecoMapper.findBankRecoBySystrace", transAccount.getSystrace());
				//交易金额格式化 保留两位小数
				java.text.DecimalFormat df=new java.text.DecimalFormat("#.00");
				transAccount.setTxnAmt(new BigDecimal(df.format(transAccount.getTxnAmt())));
				if(transAccountB != null){
					transAccount.setAcpNameB(transAccountB.getAcpName());
					transAccount.setTxnNameB(transAccountB.getTxnName());
					transAccount.setMidB(transAccountB.getMid());
					transAccount.setPanB(transAccountB.getPan());
					transAccount.setSystraceB(transAccountB.getSystrace());
					transAccount.setTidB(transAccountB.getTid());
					transAccount.setTranDateB(transAccountB.getTranDate());
					transAccount.setTxnAmtB(transAccountB.getTxnAmt());
					if(!transAccount.getPanB().equals(transAccount.getPan()) || 
							!transAccount.getTidB().equals(transAccount.getTid()) || 
							transAccount.getTxnAmt().compareTo(transAccount.getTxnAmtB()) !=0 || 
							!transAccount.getTranDateB().equals(transAccount.getTranDate()) || 
							!transAccount.getTxnNameB().equals(transAccount.getTxnName())){
						//对账失败用notMatcthed
						transAccount.setStyleClass("notMatcthed");
					}
				}else{
					//对账失败用notMatcthed
					transAccount.setStyleClass("notMatcthed");
				}
			}
		}
		pd.getPd().put("pan", oldPan);
		return transAccountList;
	}
	
	/**
	 * 导入对账文件
	 * @param filepath  文件路径
	 * @param fileName  文件名
	 * @return
	 */
	public String readTxt(String filePath, String fileName) throws IOException {
		String result = "error";
		try {
        	//记录商户名称
        	String merChantName = "";
        	//记录交易日期
			String transactionDate = "";
			//记录商户号
			String transactionMerChantNum = "";
			//记录行号
			int lineNum = 0;
			String encoding="GBK";
            File file=new File(filePath+fileName);
            if(file.isFile() && file.exists()){ //判断文件是否存在
            	//存放交易记录List
            	List<TransAccount> list = new ArrayList<TransAccount>();
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	//行号+1
                	lineNum ++;
                	System.out.println(lineNum);
                	//去前后空格
                	lineTxt = lineTxt.trim();
                	if(lineTxt.indexOf("清算日期:") >= 0) {
                		//清算日期
                		transactionDate = lineTxt.substring(lineTxt.length()-9,lineTxt.length()-1).trim();
                		transactionDate = transactionDate.substring(0,4) + "-" + transactionDate.substring(4,6) + "-" + transactionDate.substring(6);
                		System.out.println(transactionDate);
                	} else if(lineTxt.indexOf("商户编号:") >= 0) {
                		//商户号
                		transactionMerChantNum = lineTxt.replaceAll("\"", ",").split(",")[3];
                		merChantName = lineTxt.replaceAll("\"", ",").split(",")[7];
                	} else if(lineNum >= 8 && lineTxt.indexOf(":") < 0
                		&& lineTxt.indexOf("积分") < 0 && lineTxt.indexOf("：") < 0) {
                		TransAccount  transAccount = new TransAccount();
                		//商户号
                		transAccount.setMid(transactionMerChantNum);
                		//商户名称
                		transAccount.setAcpName(merChantName);
                		String[] transLineArr = lineTxt.split("\t");
            			//终端号
            			transAccount.setTid(transLineArr[0].substring(transLineArr[0].indexOf("\"")+1,transLineArr[0].lastIndexOf("\"")));
            			//交易时间
            			transAccount.setTranDate(transactionDate + " " +
            					transLineArr[1].substring(transLineArr[1].indexOf("\"")+5,transLineArr[1].indexOf("\"")+7)
            					+ ":" +transLineArr[1].substring(transLineArr[1].indexOf("\"")+7,transLineArr[1].indexOf("\"")+9)
            					+ ":" +transLineArr[1].substring(transLineArr[1].indexOf("\"")+9,transLineArr[1].lastIndexOf("\""))
            				);
            			//交易类型
            			transAccount.setTxnName(transLineArr[2].substring(transLineArr[2].indexOf("\"")+1,transLineArr[2].lastIndexOf("\"")));
            			//流水号
            			transAccount.setNumerical(transLineArr[3].substring(transLineArr[3].indexOf("\"")+1,transLineArr[3].lastIndexOf("\"")));
            			//终端流水号
            			transAccount.setSystrace(transLineArr[4].substring(transLineArr[4].indexOf("\"")+1,transLineArr[4].lastIndexOf("\"")));
            			//卡号
            			transAccount.setPan(transLineArr[5].substring(transLineArr[5].indexOf("\"")+1,transLineArr[5].lastIndexOf("\"")));
            			//交易本金
            			transAccount.setTxnAmt(new BigDecimal(transLineArr[6].trim()));
            			//应收商户手续费
            			transAccount.setMerchantFee(new BigDecimal(transLineArr[7].trim()));
            			//清算金额
            			transAccount.setAmountOfSettlement(new BigDecimal(transLineArr[8].trim()));
                		list.add(transAccount);
                	}
                }
                read.close();
                
                if(list.size() > 0) {
                	this.dao.save("TransAccountMapper.batchInsertTransAccount", list);
                	result = "success";
                }
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
		return result;
	}
	
}
