package com.furen.entity.system;

import java.math.BigDecimal;

public class TransStatistic {
	
	/** 机构id */
	private int departmentId;
	/** 机构名称 */
	private String departmentName;
	/** 机构类型 */
	private String departmentType;
	/** 商户号 */
	private String mid;
	/** 商户名称 */
	private String acqName;
	/** 需要统计的商户号 */
	private String allMids;
	/** 交易笔数 */
	private int transNum;
	/** 交易金额 */
	private BigDecimal transAggregate;
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentType() {
		return departmentType;
	}
	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getAcqName() {
		return acqName;
	}
	public void setAcqName(String acqName) {
		this.acqName = acqName;
	}
	public String getAllMids() {
		return allMids;
	}
	public void setAllMids(String allMids) {
		this.allMids = allMids;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getTransNum() {
		return transNum;
	}
	public void setTransNum(int transNum) {
		this.transNum = transNum;
	}
	public BigDecimal getTransAggregate() {
		return transAggregate;
	}
	public void setTransAggregate(BigDecimal transAggregate) {
		this.transAggregate = transAggregate;
	}
}