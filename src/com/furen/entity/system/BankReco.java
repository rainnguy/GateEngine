package com.furen.entity.system;

import java.math.BigDecimal;

/**
 * 
* 类名称：TransAccount.java
* 类描述： 
* @author zhouj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年12月13日
* @version 1.0
 */
public class BankReco {

	private int id;
	
	private String txnName;//交易类型
	
	private String pan;//卡号
	
	private BigDecimal txnAmt;//交易金额
	
	private String tid;//终端号
	
	private String mid;//商户号
	
	private String acpName;//商户名称
	
	private String tranDate;//交易时间
	
    private String systrace;//POS流水号 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAcpName() {
		return acpName;
	}

	public void setAcpName(String acpName) {
		this.acpName = acpName;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getSystrace() {
		return systrace;
	}

	public void setSystrace(String systrace) {
		this.systrace = systrace;
	}

}
