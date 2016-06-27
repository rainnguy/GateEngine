package com.furen.entity.system;

import java.util.Date;

public class Coupon {

	/** 编号 */
	private String code = null;
	
	/** 名称 */
	private String name = null;
	
	/** 支付码 */
	private String payCode = null;
	
	/** 面值 */
	private float cardValue = 0;
	
	/** 价格 */
	private float price = 0;
	
	/** 持有者 */
	private String owner = null;
	
	/** 购买时间 */
	private String buyTime = null;
	
	/** 使用时间 */
	private Date useTime = null;
	
	/** 使用状态 */
	private String useStatus = null;
	
	/** 购买时的商户 */
	private String buyStation = null;
	
	/** 备注 */
	private String remark = null;

	/**
	 * 编号
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 编号
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 支付码
	 * @return the pay_code
	 */
	public String getPayCode() {
		return payCode;
	}

	/**
	 * 支付码
	 * @param pay_code the pay_code to set
	 */
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	/**
	 * 面值
	 * @return the card_value
	 */
	public float getCardValue() {
		return cardValue;
	}

	/**
	 * 面值
	 * @param card_value the card_value to set
	 */
	public void setCardValue(float cardValue) {
		this.cardValue = cardValue;
	}

	/**
	 * 价格
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * 价格
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * 持有者
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * 持有者
	 * @param owner the owner to set
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * 购买时间
	 * @return the buy_time
	 */
	public String getBuyTime() {
		return buyTime;
	}

	/**
	 * 购买时间
	 * @param buy_time the buy_time to set
	 */
	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	/**
	 * 使用时间 
	 * @return the useTime
	 */
	public Date getUseTime() {
		return useTime;
	}

	/**
	 * 使用时间 
	 * @param useTime the useTime to set
	 */
	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	/**
	 * 使用状态
	 * @return the use_status
	 */
	public String getUseStatus() {
		return useStatus;
	}

	/**
	 * 使用状态
	 * @param use_status the use_status to set
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	/**
	 * 购买时的商户
	 * @return the buy_station
	 */
	public String getBuyStation() {
		return buyStation;
	}

	/**
	 * 购买时的商户
	 * @param buy_station the buy_station to set
	 */
	public void setBuyStation(String buyStation) {
		this.buyStation = buyStation;
	}

	/**
	 * 备注
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
