package com.furen.entity.system;

/**
 * 
* 类名称：Log.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年12月03日
* @version 1.0
 */
public class Log {
	/** ID */
	private int id;
	
	/** 用户真实姓名  */
	private String name;
	
	/** 操作  */
	private String systemType;
	
	/** 类型  */
	private String operatorType;
	
	/** 模块名  */
	private String menuName;
	
	/** 备注 */
	private String remark;
	
	/** 创建时间 */
	private String createDate;
	
	/** 创建用户 */
	private String createUserId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
}