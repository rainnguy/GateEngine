package com.furen.entity.system;

import java.math.BigDecimal;

/**
 * 
* 类名称：FusionCharts.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class FusionCharts {
	/** 日期 */
	private String date;
	
	/** 交易额 */
	private BigDecimal money;

	/**
	 * 日期
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 日期
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 交易额
	 * @return
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * 交易额
	 * @param money
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	

}
