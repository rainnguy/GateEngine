//交易缩写
var transName;
var plain;
var signature;
var payFlg="1";
// 支付银行
var bankValue;

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
		}
	}
}

// 选择银行变更
function changeBank() {
	for (var i = 0; i < bank.length; i++) {
		if (bank.options[i].selected == true) {
			bankValue = bank.options[i].value;
		}
	}
	
	// 确认并组装数据
	check();
};

// 显示实际金额
function showValue() {
	var discount=document.getElementsByName("discount").value;
	for (var i = 0; i < money.length; i++) {
		if (money.options[i].selected == true) {
			document.getElementById("realMoney").innerText = money.options[i].value * discount;
		}
	}
}

// 确认表单
function checkCustomerOder(){
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
	if(isNaN(number)) {
		flag = 1;
		alert("数量请输入数字");
	}

	// 判断支付方式
	if (payFlg != "0" && payFlg != "1") {
		flag = 1;
		alert("请选择支付方式");
	}

	// 判断银行卡
	if (payFlg == "1" && bankValue == "") {
		flag = 1;
		alert("请选择支付的银行卡");
	}

	if (flag == 0) {
		return true;
	} else {
		return false;
	}
}


// 确认并组装数据
function check() {

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
	if(isNaN(number)) {
		flag = 1;
		alert("数量请输入数字");
	}

	// 判断支付方式
	if (payFlg != "0" && payFlg != "1") {
		flag = 1;
		alert("请选择支付方式");
	}

	// 判断银行卡
	if (payFlg == "1" && bankValue == "") {
		flag = 1;
		alert("请选择支付的银行卡");
	}

	if (flag == 0) {
		establishData();
	} else {
		return;
	}
}

// 组织数据
function establishData() {
	if (bankValue == "spdb") {
		transName = "IPER";
	} else {
		transName = "KHZF";
	}

	document.getElementsByName("transName").value=transName;
	
	// 交易金额
	var realMoney = document.getElementById("realMoney").innerText
			* document.getElementById("number").value;
	// 商户日期时间
	var nowDateTime = getNowDateTime();
	// 订单号
	var TermSsn = nowDateTime.substr(2);
	// 商户号 TODO
	// var mercCode = document.getElementById("mercCode").value;
	var mercCode = "983708160009501";
	// 账户类型 TODO 1为借记卡 U为信用卡
	var accountType = 1;
	// 支付类型 1-商品购买,4-捐赠,47-电子卡券
	var payType = 47;
	// 商品名称
	var description = document.getElementById("description").innerText;
	// 支付交易中，接收交易结果的url
	var mercUrl = "http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2Fmain%2Findex";

	plain = "TranAbbr=" + transName + "|MasterID=|MercDtTm=" + nowDateTime
			+ "|TermSsn=" + TermSsn + "|OSttDate=|OAcqSsn=|MercCode="
			+ mercCode + "|TermCode=|TranAmt=" + realMoney + "|PayBank="
			+ bankValue + "|AccountType=" + accountType + "|PayType=" + payType
			+ "|Subject=" + description + "|Notice=|Remark1=|Remark2=|MercUrl="
			+ mercUrl + "|Ip=|SubMercFlag=|SubMercName=|SubMercGoodsName=";

	document.getElementsByName("Plain").value=plain;
	
	alert("transName="+transName);
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
