package com.furen.entity.system;

public class Order {

	/** 订单号 */
	private String orderCode =null;
	
	/** 订单时间 */
	private String orderTime = null;
	
	/** 订单状态 */
	private String orderStatus = null;
	
	/** 交易缩写 */
	private String tranAbbr = null;
	
	/** 用户编号 */
	private String userCode = null;
	
	/** 商户号 */
	private String merchantNum = null;
	
	/** 商户名称 */
	private String merchantName = null;
	
	/** 商品名称 */
	private String goodsName = null;
	
	/** 商品面值 */
	private float goodsValue = 0;
	
	/** 商品价格 */
	private float price = 0;
	
	/** 数量 */
	private int number = 0;
	
	/** 订单总金额 */
	private float amount = 0;
	
	/** 支付方式代码 */
	private String payType = null;
	
	/** 支付方式 */
	private String payTypeContent = null;
	
	/** 支付银行代码 */
	private String payBank = null;
	
	/** 支付银行 */
	private String payBankName = null;
	
	/** 银行卡种类代码 */
	private String cardType = null;
	
	/** 银行卡种类 */
	private String cardTypeContent = null;
	
	/** 流水号 */
	private String acqSsn = null;
	
	/** 返回码 */
	private String respCode = null;
	
	/** 终端号 */
	private String termCode = null;
	
	/** 清算日期 */
	private String clearDate = null;

	/**
	 * 订单号
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 订单号
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * 订单时间
	 * @return the orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * 订单时间
	 * @param orderTime the orderTime to set
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * 订单状态
	 * @return the orderStatus
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	/**
	 * 订单状态
	 * @param orderStatus the orderStatus to set
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * 交易缩写
	 * @return the tranAbbr
	 */
	public String getTranAbbr() {
		return tranAbbr;
	}

	/**
	 * 交易缩写
	 * @param tranAbbr the tranAbbr to set
	 */
	public void setTranAbbr(String tranAbbr) {
		this.tranAbbr = tranAbbr;
	}

	/**
	 * 用户编号
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * 用户编号
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * 商户号
	 * @return the merchantNum
	 */
	public String getMerchantNum() {
		return merchantNum;
	}

	/**
	 * 商户号
	 * @param merchantNum the merchantNum to set
	 */
	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	/**
	 * 商户名称
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * 商户名称
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * 商品名称
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 商品名称
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 商品面值
	 * @return the goodsValue
	 */
	public float getGoodsValue() {
		return goodsValue;
	}

	/**
	 * 商品面值
	 * @param goodsValue the goodsValue to set
	 */
	public void setGoodsValue(float goodsValue) {
		this.goodsValue = goodsValue;
	}

	/**
	 * 商品价格
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * 商品价格
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * 数量
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * 数量
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 订单总金额
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * 订单总金额
	 * @param amount the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * 支付方式代码
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * 支付方式代码
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * 支付方式
	 * @return the payTypeContent
	 */
	public String getPayTypeContent() {
		return payTypeContent;
	}

	/**
	 * 支付方式
	 * @param payTypeContent the payTypeContent to set
	 */
	public void setPayTypeContent(String payTypeContent) {
		this.payTypeContent = payTypeContent;
	}

	/**
	 * 支付银行代码
	 * @return the payBank
	 */
	public String getPayBank() {
		return payBank;
	}

	/**
	 * 支付银行代码
	 * @param payBank the payBank to set
	 */
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}

	/**
	 * 支付银行
	 * @return the payBankName
	 */
	public String getPayBankName() {
		return payBankName;
	}

	/**
	 * 支付银行
	 * @param payBankName the payBankName to set
	 */
	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	/**
	 * 银行卡种类代码
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * 银行卡种类代码
	 * @param cardType the cardType to set
	 */
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * 银行卡种类
	 * @return the cardTypeContent
	 */
	public String getCardTypeContent() {
		return cardTypeContent;
	}

	/**
	 * 银行卡种类
	 * @param cardTypeContent the cardTypeContent to set
	 */
	public void setCardTypeContent(String cardTypeContent) {
		this.cardTypeContent = cardTypeContent;
	}

	/**
	 * 流水号
	 * @return the acqSsn
	 */
	public String getAcqSsn() {
		return acqSsn;
	}

	/**
	 * 流水号
	 * @param acqSsn the acqSsn to set
	 */
	public void setAcqSsn(String acqSsn) {
		this.acqSsn = acqSsn;
	}

	/**
	 * 返回码
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * 返回码
	 * @param respCode the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	/**
	 * 终端号
	 * @return the termCode
	 */
	public String getTermCode() {
		return termCode;
	}

	/**
	 * 终端号
	 * @param termCode the termCode to set
	 */
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	/**
	 * 清算日期
	 * @return the clearDate
	 */
	public String getClearDate() {
		return clearDate;
	}

	/**
	 * 清算日期
	 * @param clearDate the clearDate to set
	 */
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
}
