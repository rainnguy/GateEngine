package com.furen.entity.system;

/**
 * 
* 类名称：Gateway.java
* 类描述： 
* @author xukai
* 作者单位：富仁高科 
* 联系方式：
* 创建时间：2016年06月21日
* @version 1.0
 */
public class Gateway {
	
	/** 返回码 */
	String respCode = null;
	
	/** 返回码的含义 */
	String respCodeContent = null;

	/**
	 * 返回码
	 * @return the respCode
	 */
	public String getRespCode() {
		return respCode;
	}

	/**
	 * 返回码
	 * @param respCode the respCode to set
	 */
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	/**
	 * 返回码的含义
	 * @return the respCodeContent
	 */
	public String getRespCodeContent() {
		return respCodeContent;
	}

	/**
	 * 返回码的含义
	 * @param respCodeContent the respCodeContent to set
	 */
	public void setRespCodeContent(String respCodeContent) {
		this.respCodeContent = respCodeContent;
	}

}
