package com.furen.entity.system;

import java.util.Date;

import com.furen.entity.Page;

/**
 * 
* 类名称：Department.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class Department {
	/** id */
	private int id ;
	
	/** 父级ID */
	private int parentId;

	/** 机构名称 */
	private String departmentName;

	/** 机构类型   0：富仁    1：总公司    2：分公司    3：加油站 */
	private int departmentType;
	
	/** 机构类型名称 */
	private String departmentTypeName;

	/** 商户号 */
	private String merchantNum;

	/** 商户名称 */
	private String merchantName;

	/** 联系人 */
	private String concator;

	/** 联系电话 */
	private String phone;
	
	/** 机构地址 */
	private String address;
	
	/** 备注 */
	private String remark;

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

	/** 省份 */
	private int province;

	/** 城市 */
	private int city;

	/** 区 */
	private int area;
	
	/** 分页对象 */
	private Page page;			

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
	 * 父级ID
	 * @return
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * 父级ID
	 * @param parentId
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	/**
	 * 机构名称
	 * @return
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 机构名称
	 * @param departmentName
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 机构类型   
	 * 0：富仁    
	 * 1：总公司    
	 * 2：分公司   
	 * 3：加油站
	 * @return
	 */
	public int getDepartmentType() {
		return departmentType;
	}

	/**
	 * 机构类型   
	 * 0：富仁    
	 * 1：总公司    
	 * 2：分公司   
	 * 3：加油站
	 * @param departmentType
	 */
	public void setDepartmentType(int departmentType) {
		this.departmentType = departmentType;
	}

	/**
	 * 机构类型名称
	 * @return
	 */
	public String getDepartmentTypeName() {
		return departmentTypeName;
	}

	/**
	 * 机构类型名称
	 * @param departmentTypeName
	 */
	public void setDepartmentTypeName(String departmentTypeName) {
		this.departmentTypeName = departmentTypeName;
	}

	/**
	 * 商户号
	 * @return
	 */
	public String getMerchantNum() {
		return merchantNum;
	}

	/**
	 * 商户号
	 * @param merchantNum
	 */
	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	/**
	 * 商户名称
	 * @return
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * 商户名称
	 * @param merchantName
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * 联系人
	 * @return
	 */
	public String getConcator() {
		return concator;
	}

	/**
	 * 联系人
	 * @param concator
	 */
	public void setConcator(String concator) {
		this.concator = concator;
	}

	/**
	 * 联系电话
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 联系电话
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 机构地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 机构地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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

	/**
	 * 分页对象
	 * @return
	 */
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}

	/**
	 * 分页对象
	 * @param page
	 */
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 省份
	 * @return
	 */
	public int getProvince() {
		return province;
	}

	/**
	 * 省份
	 * @param province
	 */
	public void setProvince(int province) {
		this.province = province;
	}

	/**
	 * 城市
	 * @return
	 */
	public int getCity() {
		return city;
	}

	/**
	 * 城市
	 * @param city
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * 区
	 * @return
	 */
	public int getArea() {
		return area;
	}

	/**
	 * 区
	 * @param area
	 */
	public void setArea(int area) {
		this.area = area;
	}
	
	
}
