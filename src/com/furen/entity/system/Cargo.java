package com.furen.entity.system;

public class Cargo {

	/** 商户号 */
	private String merchantCode = null;

	/** 商品名 */
	private String goodsName = null;

	/** 面值 */
	private String goodsValue = null;

	/** 价格 */
	private String price = null;

	/** 折扣 */
	private String discount = null;

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
}
