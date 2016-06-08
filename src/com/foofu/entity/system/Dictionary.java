package com.foofu.entity.system;

import java.util.Date;

/**
 * 
* 类名称：Dictionary.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月20日
* @version 1.0
 */
public class Dictionary {
	/** id */
	private int id ;
	
	/** 类型 */
	private String type;

	/** 显示名称 */
	private String name;

	/** 值 */
	private int value;

	/** 拍序号 */
	private int sort;

	/** 删除标识   1：未删除    2：已删除 */
	private int isDelete;

	/** 创建人 */
	private String createUser;

	/** 创建时间 */
	private Date createDate;

	/** 更新人 */
	private String updateUser;

	/** 更新时间 */
	private Date updateDate;
	

	/**
	 * id
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 显示名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 显示名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 值
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 值
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * 排序号
	 * @return
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * 排序号
	 * @param sort
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * 删除标识   
	 * 1：未删除   
	 * 2：已删除
	 * @return
	 */
	public int getIsDelete() {
		return isDelete;
	}

	/**
	 * 删除标识   
	 * 1：未删除   
	 * 2：已删除
	 * @param isDelete
	 */
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 创建人
	 * @return
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 创建人
	 * @param createUser
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 更新人
	 * @return
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 更新人
	 * @param updateUser
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 更新时间
	 * @return
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 更新时间
	 * @param updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
