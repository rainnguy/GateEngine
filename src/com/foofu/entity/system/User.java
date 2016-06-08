package com.foofu.entity.system;

import com.foofu.entity.Page;

/**
 * 
* 类名称：User.java
* 类描述： 
* @author xiajj
* 作者单位：南京双富信息科技有限公司 
* 联系方式：
* 创建时间：2015年11月10日
* @version 1.0
 */
public class User {
	private int USER_ID;		//用户id
	private String USERNAME;	//用户名
	private String PASSWORD; 	//密码
	private String NAME;		//姓名
	private String RIGHTS;		//权限
	private String ROLE_ID;		//角色id
	private String LAST_LOGIN;	//最后登录时间
	private String IP;			//用户登录ip地址
	private String STATUS;		//状态
	private Role role;			//角色对象
	private Page page;			//分页对象
	private String SKIN;		//皮肤
	private int departmentId;   //机构ID
	private String departmentName;//机构名称
	private int departmentType; //机构类型
	private int parentDepartmentId; //父级机构ID
	
	
	public int getParentDepartmentId() {
		return parentDepartmentId;
	}
	public void setParentDepartmentId(int parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}
	public int getDepartmentType() {
		return departmentType;
	}
	public void setDepartmentType(int departmentType) {
		this.departmentType = departmentType;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public String getSKIN() {
		return SKIN;
	}
	public void setSKIN(String sKIN) {
		SKIN = sKIN;
	}
	
	public int getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getRIGHTS() {
		return RIGHTS;
	}
	public void setRIGHTS(String rIGHTS) {
		RIGHTS = rIGHTS;
	}
	public String getROLE_ID() {
		return ROLE_ID;
	}
	public void setROLE_ID(String rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	
}
