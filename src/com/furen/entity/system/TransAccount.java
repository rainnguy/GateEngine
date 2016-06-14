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
public class TransAccount {

	private int id;
	
	private String txnName;//交易类型
	
	private String pan;//卡号
	
	private BigDecimal txnAmt;//交易金额
	
	private String tid;//终端号
	
	private String mid;//商户号
	
	private String acpName;//商户名称
	
	private String tranDate;//交易时间
	
    private String systrace;//POS流水号 
    
    private String numerical;//流水号
    
    private BigDecimal merchantFee;//商户手续费.
    
    private BigDecimal amountOfSettlement;//清算金额
    
    private String txnNameB;//交易类型
	
	private String panB;//卡号
	
	private BigDecimal txnAmtB;//交易金额
	
	private String tidB;//终端号
	
	private String midB;//商户号
	
	private String acpNameB;//商户名称
	
	private String tranDateB;//交易时间
	
    private String systraceB;//POS流水号 
    
    private String styleClass;//显示的class 对账对上用normal 否则用 notMatched
    
	public String getNumerical() {
		return numerical;
	}

	public void setNumerical(String numerical) {
		this.numerical = numerical;
	}

	public BigDecimal getMerchantFee() {
		return merchantFee;
	}

	public void setMerchantFee(BigDecimal merchantFee) {
		this.merchantFee = merchantFee;
	}

	public BigDecimal getAmountOfSettlement() {
		return amountOfSettlement;
	}

	public void setAmountOfSettlement(BigDecimal amountOfSettlement) {
		this.amountOfSettlement = amountOfSettlement;
	}

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

	public String getTxnNameB() {
		return txnNameB;
	}

	public void setTxnNameB(String txnNameB) {
		this.txnNameB = txnNameB;
	}

	public String getPanB() {
		return panB;
	}

	public void setPanB(String panB) {
		this.panB = panB;
	}

	public BigDecimal getTxnAmtB() {
		return txnAmtB;
	}

	public void setTxnAmtB(BigDecimal txnAmtB) {
		this.txnAmtB = txnAmtB;
	}

	public String getTidB() {
		return tidB;
	}

	public void setTidB(String tidB) {
		this.tidB = tidB;
	}

	public String getMidB() {
		return midB;
	}

	public void setMidB(String midB) {
		this.midB = midB;
	}

	public String getAcpNameB() {
		return acpNameB;
	}

	public void setAcpNameB(String acpNameB) {
		this.acpNameB = acpNameB;
	}

	public String getTranDateB() {
		return tranDateB;
	}

	public void setTranDateB(String tranDateB) {
		this.tranDateB = tranDateB;
	}

	public String getSystraceB() {
		return systraceB;
	}

	public void setSystraceB(String systraceB) {
		this.systraceB = systraceB;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
}
