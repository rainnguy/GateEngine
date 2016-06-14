package com.furen.entity.system;


/**
 * 
* 类名称：Region.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class Region {
	/** id */
	private int regionId;
	
	/** 代码 */
	private String regionCode;
	
	/** 名称 */
	private String regionName;
	
	/** 父ID */
	private int parentId;
	
	/** 等级 */
	private int regionLevel;
	
	/** 排序号 */
	private int regionOrder;
	
	/** 全拼 */
	private String regionNameEn;
	
	/** 拼音缩写 */
	private String regionShortNameEn;

	/**
	 * id
	 * @return
	 */
	public int getRegionId() {
		return regionId;
	}

	/**
	 * id
	 * @param regionId
	 */
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	/**
	 * 代码
	 * @return
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * 代码
	 * @param regionCode
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * 名称
	 * @return
	 */
	public String getRegionName() {
		return regionName;
	}

	/**
	 * 名称
	 * @param regionName
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	/**
	 * 父ID
	 * @return
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * 父ID
	 * @param parentId
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * 等级
	 * @return
	 */
	public int getRegionLevel() {
		return regionLevel;
	}

	/**
	 * 等级
	 * @param regionLevel
	 */
	public void setRegionLevel(int regionLevel) {
		this.regionLevel = regionLevel;
	}

	/**
	 * 排序号
	 * @return
	 */
	public int getRegionOrder() {
		return regionOrder;
	}

	/**
	 * 排序号
	 * @param regionOrder
	 */
	public void setRegionOrder(int regionOrder) {
		this.regionOrder = regionOrder;
	}

	/**
	 * 全拼
	 * @return
	 */
	public String getRegionNameEn() {
		return regionNameEn;
	}

	/**
	 * 全拼
	 * @param regionNameEn
	 */
	public void setRegionNameEn(String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}

	/**
	 * 拼音缩写
	 * @return
	 */
	public String getRegionShortNameEn() {
		return regionShortNameEn;
	}

	/**
	 * 拼音缩写
	 * @param regionShortNameEn
	 */
	public void setRegionShortNameEn(String regionShortNameEn) {
		this.regionShortNameEn = regionShortNameEn;
	}
	
	
}
