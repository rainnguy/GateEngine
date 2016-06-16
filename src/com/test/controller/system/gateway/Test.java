package com.test.controller.system.gateway;

import com.csii.payment.client.core.MerchantSignVerifyExt;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String plain="TranAbbr=KHZF|MasterID=|MercDtTm=20160612111225|TermSsn=160612111225|OSttDate=|OAcqSsn=|MercCode=983708160009501|TermCode=|TranAmt=100|PayBank=vbank|AccountType=U|PayType=1|Subject=|Notice=|Remark1=|Remark2=|MercUrl=http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2Fmain%2Findex|Ip=|SubMercFlag=|SubMercName=|SubMercGoodsName=";
		String Signature=MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_983708160001601","key_password_983708160001601",plain);
		String Signature2=MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_983708160000201","key_password_983708160000201",plain);
		String Signature3=MerchantSignVerifyExt.merchantSignData_ABA_EXT("key_alias_983708160009501","key_password_983708160009501",plain);
		System.out.println(Signature);
		System.out.println(Signature2);
		System.out.println(Signature3);
	}

}
