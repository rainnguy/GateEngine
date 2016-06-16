var payFlg = "1";
// 支付银行
var bankValue = "";

var locat = (window.location + '').split('/');
$(function() {
	if ('tool' == locat[3]) {
		locat = locat[0] + '//' + locat[2];
	} else {
		locat = locat[0] + '//' + locat[2] + '/' + locat[3];
	}
	;
});

// 支付方式变更
function changePayType() {
	for (var i = 0; i < payType.length; i++) {
		if (payType.options[i].selected == true) {
			switch (payType.options[i].value) {
			case "0":
				payFlg = "0";
				break;
			case "1":
				payFlg = "1";
				break;
			}
			break;
		}
	}
}

// 选择银行变更
function changeBank() {
	for (var i = 0; i < bank.length; i++) {
		if (bank.options[i].selected == true) {
			bankValue = bank.options[i].value;
			if (bankValue != "") {
				document.getElementById("submit").disabled="";
			} else {
				document.getElementById("submit").disabled="disabled";
				document.getElementById("transName").value = "";
				document.getElementById("plain").value = "";
				return;
			}
			break;
		}
	}

	// 确认并组装数据
	checkCustomerOder();
	
	// 调用后台，对数据进行签名
	signDatatt();
};


//确认并组装数据
function checkCustomerOder() {
	
	var flag = 0;
	var number = document.getElementById("number").value;
	
	// 判断金额
	if (document.getElementById("realMoney").innerText == "") {
		flag = 1;
		alert("请选择要购买的加油券的面值");
	}
	
	// 判断数量
	if (number == "") {
		flag = 1;
		alert("请输入要购买的数量");
	}
	
	// 判断数量是数字
	if (isNaN(number)) {
		flag = 1;
		alert("数量请输入数字");
	}
	
	// 判断银行卡
	if (bankValue == "") {
		flag = 1;
		alert("请选择支付的银行卡");
	}
	
	if (flag == 0) {
		establishData();
	} else {
		return;
	}
}

// 对数据进行签名
function signDatatt() {
	
	// 调用后台进行签名
	$.ajax({
		type : "POST",
		url : locat + '/gateway/signData.do',
		data : {
			plain : $("#plain").val(),
			merchantNum :  $("#merchantNum").val()
		},
		dataType : 'json',
		cache : false,
		success : function(data) {
			document.getElementById("signature").value = data.signature;
		}
	});
}

// 显示实际金额
function showValue() {
	var discount = document.getElementById("discount").value;
	
	for (var i = 0; i < money.length; i++) {
		if (money.options[i].selected == true) {
			document.getElementById("realMoney").innerText = money.options[i].value * discount;
			break;
		}
	}
}

// 选择站点后显示可使用的站点
function showUseAbleStations() {
	var merchantNum = "";

	for (var i = 0; i < station.length; i++) {
		if (station.options[i].selected == true) {
			merchantNum = station.options[i].value;
			document.getElementById("merchantNum").value = merchantNum;
			document.getElementById("merchantName").value = station.options[i].text;
			
			if (merchantNum != "") {
				document.getElementById("bank").disabled = "";
			} else {
				document.getElementById("bank").disabled = "disabled";
			}
			
			document.getElementById("submit").disabled = "disabled";
			document.getElementById("bank").value = "";
			document.getElementById("transName").value = "";
			document.getElementById("plain").value = "";
			break;
		}
	}

	// 调用后台去获取可使用的站
	$.ajax({
		type : "POST",
		url : locat + '/gateway/showUseAbleStations.do',
		data : {
			requestMethod : merchantNum
		},
		dataType : 'json',
		cache : false,
		success : function(data) {
			if ("success" == data.errInfo) {
				document.getElementById("useAbleStations").value = data.useAbleStations;
			} else {
				alert("获取可使用的站点时发生异常！");
			}
		}
	});
}

// 组织数据
function establishData() {
	
	// 交易金额
	var realMoney = document.getElementById("realMoney").innerText
			* document.getElementById("number").value;
	
	// 商户日期时间
	var nowDateTime = getNowDateTime();
	// 订单号
	var TermSsn = nowDateTime.substr(2);
	// 商户号 TODO 目前为假商户号
	// var mercCode = document.getElementById("mercCode").value;
	var mercCode = "983708160009501";
	// 账户类型 TODO 1为借记卡 U为信用卡
	var accountType = 1;
	// 支付类型 1-商品购买,4-捐赠,47-电子卡券
	var payType = 47;
	// 商品名称
	// var goodsName = document.getElementById("description").innerText;
	var goodsName = document.getElementById("goodsName").value;
	// 商户名称
//	var merchantName = document.getElementById("merName").value;
	var merchantName = "aaaa";
	
	// 支付交易中，接收交易结果的url
	// var mercUrl =
	// "http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2Fmain%2Findex";
	var mercUrl = "http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2F404.jsp";
//	http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2Fmain%2Findex

	
	//交易缩写
	var transName = "";
	var plain = "";
	
	if (bankValue == "spdb") {
		transName = "IPER";
		plain ="TranAbbr="+ transName + "|MasterID=|MercDtTm=" + nowDateTime
		+ "|TermSsn=" + TermSsn + "|OSttDate=|OAcqSsn=|MercCode="
		+ mercCode + "|TermCode=|TranAmt=" + realMoney + "|Remark1=|Remark2=|MercUrl="
		+ mercUrl + "|Ip=|SubMercFlag=0|SubMercName=" + merchantName
		+ "|SubMercGoodsName=" + goodsName;
		alert(plain);
	} else {
		transName = "KHZF";
		plain = "TranAbbr=" + transName + "|MasterID=|MercDtTm=" + nowDateTime
			+ "|TermSsn=" + TermSsn + "|OSttDate=|OAcqSsn=|MercCode="
			+ mercCode + "|TermCode=|TranAmt=" + realMoney + "|PayBank="
			+ bankValue + "|AccountType=" + accountType + "|PayType=" + payType
			+ "|Subject=" + goodsName + "|Notice=|Remark1=|Remark2=|MercUrl="
			+ mercUrl + "|Ip=|SubMercFlag=|SubMercName=|SubMercGoodsName=";
	}

	document.getElementById("transName").value = transName;
	document.getElementById("plain").value = plain;
}

// 获取日期、时间
function getNowDateTime() {
	var myDate = new Date();
	// myDate.getYear(); //获取当前年份(2位)
	// myDate.getFullYear(); //获取完整的年份(4位,1970-????)
	// myDate.getMonth(); //获取当前月份(0-11,0代表1月)
	// myDate.getDate(); //获取当前日(1-31)
	// myDate.getDay(); //获取当前星期X(0-6,0代表星期天)
	// myDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数)
	// myDate.getHours(); //获取当前小时数(0-23)
	// myDate.getMinutes(); //获取当前分钟数(0-59)
	// myDate.getSeconds(); //获取当前秒数(0-59)
	// myDate.getMilliseconds(); //获取当前毫秒数(0-999)
	// myDate.toLocaleDateString(); //获取当前日期
	// myDate.toLocaleString(); //获取日期与时间

	// 时
	var hour = myDate.getHours();
	if (hour < 10) {
		hour = "0" + hour;
	}
	// 分
	var minute = myDate.getMinutes();
	if (minute < 10) {
		minute = "0" + minute;
	}
	// 秒
	var second = myDate.getSeconds();
	if (second < 10) {
		second = "0" + second;
	}
	// 日
	var day = myDate.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	// 月
	var month = myDate.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}

	return myDate.getFullYear() + month + day + hour + minute + second;
}
