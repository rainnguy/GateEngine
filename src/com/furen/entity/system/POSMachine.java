package com.furen.entity.system;

import java.util.Date;

import com.furen.entity.Page;

/**
 * 
* 类名称：POSMachine.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class POSMachine {
	/** id */
	private int id ;
	
	/** 加油站ID */
	private int departmentId;
	
	/** 加油站名称 */
	private String departmentName;
	
	/** 终端号 */
	private String posNumber;
	
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
	
	/** 寄出日期 */
	private String mailedDate;
	
	/** 机具产权 */
	private String machineProperty;
	
	/** 机具型号 */
	private String machineModel;
	
	/** 机身序列号 */
	private String machineSerialNumber;

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
	 * 加油站名称
	 * @return
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 加油站名称
	 * @param departmentName
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
	 * 加油站ID
	 * @return
	 */
	public int getDepartmentId() {
		return departmentId;
	}

	/**
	 * 加油站ID
	 * @param departmentId
	 */
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 终端号
	 * @return
	 */
	public String getPosNumber() {
		return posNumber;
	}

	/**
	 * 终端号
	 * @param posNumber
	 */
	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

	/**
	 * 寄出日期
	 * @return
	 */
	public String getMailedDate() {
		return mailedDate;
	}

	/**
	 * 寄出日期
	 * @param mailedDate
	 */
	public void setMailedDate(String mailedDate) {
		this.mailedDate = mailedDate;
	}

	/**
	 * 机具产权
	 * @return
	 */
	public String getMachineProperty() {
		return machineProperty;
	}

	/**
	 * 机具产权
	 * @param machineProperty
	 */
	public void setMachineProperty(String machineProperty) {
		this.machineProperty = machineProperty;
	}

	/**
	 * 机具型号
	 * @return
	 */
	public String getMachineModel() {
		return machineModel;
	}

	/**
	 * 机具型号
	 * @param machineModel
	 */
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	/**
	 * 机身序列号
	 * @return
	 */
	public String getMachineSerialNumber() {
		return machineSerialNumber;
	}

	/**
	 * 机身序列号
	 * @param machineSerialNumber
	 */
	public void setMachineSerialNumber(String machineSerialNumber) {
		this.machineSerialNumber = machineSerialNumber;
	}
}
