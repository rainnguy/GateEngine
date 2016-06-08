package com.foofu.entity.system;

/**
 * 
* 类名称：TransType.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年12月11日
* @version 1.0
 */
public class TransType {
	/** 交易代码 */
	private int txnId;
	/** 交易名称 */
	private String txnName;
	
	public int getTxnId() {
		return txnId;
	}
	public void setTxnId(int txnId) {
		this.txnId = txnId;
	}
	public String getTxnName() {
		return txnName;
	}
	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}
}