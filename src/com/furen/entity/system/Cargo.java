package com.furen.entity.system;

public class Cargo {

	/** id */
	private String id = null;
	
	/** 商户号 */
	private String merchantCode = null;
	
	/** 商户名 */
	private String merchantName = null;

	/** 商品名 */
	private String goodsName = null;

	/** 面值 */
	private String goodsValue = null;

	/** 价格 */
	private String price = null;

	/** 折扣 */
	private String discount = null;
	
	/** 备注 */
	private String remark = null;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 商户号
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * 商户号
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * 商户名
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * 商户名
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * 商品名
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 商品名
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 面值
	 * @return the goodsValue
	 */
	public String getGoodsValue() {
		return goodsValue;
	}

	/**
	 * 面值
	 * @param goodsValue the goodsValue to set
	 */
	public void setGoodsValue(String goodsValue) {
		this.goodsValue = goodsValue;
	}

	/**
	 * 价格
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * 价格
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * 折扣
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * 折扣
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
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
