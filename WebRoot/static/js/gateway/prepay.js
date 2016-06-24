var locat = (window.location + '').split('/');
$(function() {
	if ('tool' == locat[3]) {
		locat = locat[0] + '//' + locat[2];
	} else {
		locat = locat[0] + '//' + locat[2] + '/' + locat[3];
	}
	;
});

$(top.hangge());

$(function() {
	$('[data-rel=tooltip]').tooltip();
	$(".chzn-select").css('width','150px').chosen({allow_single_deselect:true , no_results_text: "No such state!"})
	.on('change', function(){
		$(this).closest('form').validate().element($(this));
	});
	// 获取点击的按钮，是“上一步”还是“下一步”
	var button = "";
	var arr = document.getElementsByTagName('button');
	for(var i = 0;i<arr.length;i++){
		arr[i].onclick = function(){
			button = this.id;
		}
	}
	
	$('#fuelux-wizard').ace_wizard().on('change' , function(e, info){
		
		var step = info.step;
		
		// 最后一步，组装数据
		if(button == "after" && step == 3 && document.getElementById("station").value != "" &&
			document.getElementById("number").value != "" && document.getElementById("money").value != "" &&
				document.getElementById("bank").value != "") {
			// 确认并组装数据
			checkCustomerOder();
			
			// 调用后台，对数据进行签名
			signDatatt();
		}
		
		// 判断可否点击“下一步”
		if (document.getElementById("station").value == "") {
			document.getElementById("after").disabled = "disabled";
		} else if (button == "before" && ((step == 3 && (document.getElementById("money").value == "" ||
			document.getElementById("numberHelp").innerText != "")) || (step == 4 && document.getElementById("bank").value == ""))) {
			document.getElementById("after").disabled = "disabled";
		} else if (button == "after" &&
			((step == 1 && (document.getElementById("money").value == "" || document.getElementById("numberHelp").innerText != "")) ||
				(step == 2 && document.getElementById("bank").value == "") ||
				(step == 3 && (document.getElementById("confirmStation").value == "" || document.getElementById("confirmMoney").value == "" ||
				document.getElementById("confirmPrice").value == "" || document.getElementById("confirmNumber").value == "" ||
				document.getElementById("confirmAmount").value == "" || document.getElementById("confirmPayBank").value == "")))) {
			document.getElementById("after").disabled = "disabled";
			if(step == 3){
				//
			}
		} else {
			document.getElementById("after").disabled = "";
		}
		
		if (step == 1) {
			// 调用后台去获取可选择的面值
			$.ajax({
				type : "POST",
				url : locat + '/cargo/getSelectableCargoes.do',
				data : {
					merchantNum : $("#merchantNum").val()
				},
				dataType : 'json',
				cache : false,
				success : function(data) {
					
					// 清空下拉列表
					document.getElementById("money").length = 0;
					
					if ("success" == data.errInfo) {
						var s1 = data.goodsValue.split(",");
						var s2 = data.price.split(",");
						var sel = document.getElementById("money");
						
						if (s1.length == s2.length) {
							for (var i = 0; i < s1.length; i++) {
								var op = new Option(s1[i],s2[i]);
								sel.options.add(op);
							}
						} else {
							alert("获取可选择的面值时发生异常！");
						}
					} else {
						alert("获取可选择的面值时发生异常！");
					}
				}
			});
		}
	}).on('finished', function(e) {
		
		// 把订单信息保存到数据库, 调用网关进行支付
		saveOrder();
	});

	$('#validation-form').hide();
	$('#skip-validation').removeAttr('checked').on('click', function(){
		$validation = this.checked;
		if(this.checked) {
			$('#sample-form').hide();
			$('#validation-form').show();
		}
		else {
			$('#validation-form').hide();
			$('#sample-form').show();
		}
	});

	$.mask.definitions['~']='[+-]';
	$('#phone').mask('(999) 999-9999');
	jQuery.validator.addMethod("phone", function (value, element) {
		return this.optional(element) || /^\(\d{3}\) \d{3}\-\d{4}( x\d{1,6})?$/.test(value);
	}, "Enter a valid phone number.");

	$('#validation-form').validate({
		errorElement: 'span',
		errorClass: 'help-inline',
		focusInvalid: false,
		rules: {
			email: {
				required: true,
				email:true
			},
			password: {
				required: true,
				minlength: 5
			},
			password2: {
				required: true,
				minlength: 5,
				equalTo: "#password"
			},
			name: {
				required: true
			},
			phone: {
				required: true,
				phone: 'required'
			},
			url: {
				required: true,
				url: true
			},
			comment: {
				required: true
			},
			state: {
				required: true
			},
			platform: {
				required: true
			},
			subscription: {
				required: true
			},
			gender: 'required',
			agree: 'required'
		},
		messages: {
			email: {
				required: "Please provide a valid email.",
				email: "Please provide a valid email."
			},
			password: {
				required: "Please specify a password.",
				minlength: "Please specify a secure password."
			},
			subscription: "Please choose at least one option",
			gender: "Please choose gender",
			agree: "Please accept our policy"
		},
		invalidHandler: function (event, validator) { //display error alert on form submit   
			$('.alert-error', $('.login-form')).show();
		},
		highlight: function (e) {
			$(e).closest('.control-group').removeClass('info').addClass('error');
		},
		success: function (e) {
			$(e).closest('.control-group').removeClass('error').addClass('info');
			$(e).remove();
		},
		errorPlacement: function (error, element) {
			if(element.is(':checkbox') || element.is(':radio')) {
				var controls = element.closest('.controls');
				if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
				else error.insertAfter(element.nextAll('.lbl').eq(0));
			} 
			else if(element.is('.chzn-select')) {
				error.insertAfter(element.nextAll('[class*="chzn-container"]').eq(0));
			}
			else error.insertAfter(element);
		},
		submitHandler: function (form) {
		},
		invalidHandler: function (form) {
		}
	});
});

// 支付方式变更
function changePayType() {
	var payType = document.getElementById("payType").value;
	if (payType == "0") {
		document.getElementById("confirmPayType").value = "手机快捷支付";
	} else if (payType == "1") {
		document.getElementById("confirmPayType").value = "网银支付";
	} else {
		document.getElementById("confirmPayType").value = "";
	}
	document.getElementById("after").disabled = "disabled";
	document.getElementById("bank").value = "";
	document.getElementById("bankValue").value = "";
	document.getElementById("transName").value = "";
	document.getElementById("plain").value = "";
}

// 银行卡类型变更
function changeCardType() {
	var cardType = document.getElementById("cardType").value;
	if (cardType == "1") {
		document.getElementById("confirmPayCardType").value = "借记卡";
	} else if (cardType == "U") {
		document.getElementById("confirmPayCardType").value = "信用卡";
	} else {
		document.getElementById("confirmPayCardType").value = "";
	}
	document.getElementById("after").disabled = "disabled";
	document.getElementById("bank").value = "";
	document.getElementById("bankValue").value = "";
	document.getElementById("transName").value = "";
	document.getElementById("plain").value = "";
}

// 选择银行变更
function changeBank() {
	for (var i = 0; i < bank.length; i++) {
		if (bank.options[i].selected == true) {
			bankValue = bank.options[i].value;
			document.getElementById("bankValue").value = bankValue;
			if (bankValue != "") {
				document.getElementById("confirmPayBank").value = bank.options[i].text;
				document.getElementById("bankHelp").innerText = "";
				document.getElementById("after").disabled = "";
			} else {
				document.getElementById("confirmPayBank").value = "";
				document.getElementById("bankHelp").innerText = "请选择支付银行！";
				document.getElementById("after").disabled = "disabled";
				document.getElementById("transName").value = "";
				document.getElementById("plain").value = "";
				return;
			}
			break;
		}
	}
}


//确认并组装数据
function checkCustomerOder() {
	
	var flag = 0;
	var number = document.getElementById("number").value;
	
	// 判断金额
	if (document.getElementById("realPrice").value == "") {
		flag = 1;
		alert("请选择要购买的加油券的面值");
	}
	
	// 判断数量
	if (number == "" || number == "0") {
		flag = 1;
		alert("请输入要购买的数量");
	}
	
	// 判断数量是数字
	if (isNaN(number)) {
		flag = 1;
		alert("数量请输入数字");
	}
	
	// 判断银行卡
	if (document.getElementById("bankValue").value == "") {
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
			merchantNum : '983708160009501'
//			merchantNum : $("#merchantNum").val()  TODO 商户号目前是假的
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
	for (var i = 0; i < money.length; i++) {
		if (money.options[i].selected == true) {
			var moneyTemp = money.options[i].text;
			var price = money.options[i].value;
			
			if (moneyTemp == "") {
				document.getElementById("confirmMoney").value = "";
				document.getElementById("confirmPrice").value = "";
				document.getElementById("realPrice").value = "";
				document.getElementById("moneyHelp").innerText = "请选择要购买的面值！";
				document.getElementById("after").disabled = "disabled";
			} else {
				document.getElementById("confirmMoney").value = moneyTemp + " 元";
				document.getElementById("confirmPrice").value = price + " 元";
				document.getElementById("realPrice").value = price;
				document.getElementById("moneyHelp").innerText = "";
				if (document.getElementById("numberHelp").innerText == "") {
					document.getElementById("after").disabled = "";
				}
			}
			document.getElementById("bank").value = "";
			document.getElementById("bankValue").value = "";
			document.getElementById("transName").value = "";
			document.getElementById("plain").value = "";
			break;
		}
	}
}

// 改变数量时，重新选择银行来计算应付金额
function changeNumber(){
	var number = document.getElementById("number").value;
	if (number == "" || number == "0") {
		document.getElementById("confirmNumber").value = "";
		document.getElementById("numberHelp").innerText = "请输入要购买的数量！";
		document.getElementById("after").disabled = "disabled";
	} else {
		document.getElementById("confirmNumber").value = number;
		document.getElementById("numberHelp").innerText = "";
		if (document.getElementById("moneyHelp").innerText == "") {
			document.getElementById("after").disabled = "";
		}
	}
	document.getElementById("bank").value = "";
	document.getElementById("bankValue").value = "";
	document.getElementById("transName").value = "";
	document.getElementById("plain").value = "";
}

// 选择站点后显示可使用的站点和可选择的面值
function showUseAbleStations() {
	
	for (var i = 0; i < station.length; i++) {
		if (station.options[i].selected == true) {
			var merchantNum = "";
			merchantNum = station.options[i].value;
			document.getElementById("merchantNum").value = merchantNum;
			if (merchantNum == "") {
				document.getElementById("confirmStation").value = "";
				document.getElementById("stationHelp").innerText = "请选择站点！";
				document.getElementById("bank").disabled = "disabled";
				document.getElementById("after").disabled = "disabled";
			} else {
				document.getElementById("confirmStation").value = station.options[i].text;
				document.getElementById("stationHelp").innerText = "";
				document.getElementById("bank").disabled = "";
				document.getElementById("after").disabled = "";
			}
			
			document.getElementById("money").length = 0; // 清空下拉列表
			document.getElementById("realPrice").value = "";
			document.getElementById("bank").value = "";
			document.getElementById("bankValue").value = "";
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
			merchantNum : $("#merchantNum").val()
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
	var realMoney = document.getElementById("realPrice").value
			* document.getElementById("number").value;
	
	// 商户日期时间
	var nowDateTime = getNowDateTime();
	// 订单号
	var termSsn = nowDateTime.substr(2);
	// 商户号 TODO 目前为假商户号
	// var mercCode = document.getElementById("mercCode").value;
	var mercCode = "983708160009501";
	// 账户类型 1为借记卡 U为信用卡
	var accountType = document.getElementById("cardType").value;
	// 支付类型 1-商品购买,4-捐赠,47-电子卡券
	var payType = 47;
	// 商品名称
	// var goodsName = document.getElementById("description").value;
	var goodsName = document.getElementById("goodsName").value;
	// 商户名称 TODO 要encode一下才能传给网关，不然乱码
	//	var merchantName = document.getElementById("confirmStation").value;
	var merchantName = "";
	
	// 支付交易中，接收交易结果的url
	var mercUrl = "http%3A%2F%2Flocalhost%3A8088%2FGateWayEngine%2Fgateway%2FpayPage.do";

	//交易缩写
	var transName = "";
	var plain = "";
	
	bankValue = document.getElementById("bankValue").value;
	
	if (bankValue == "spdb") {
		transName = "IPER";
		plain ="TranAbbr="+ transName + "|MasterID=|MercDtTm=" + nowDateTime
		+ "|TermSsn=" + termSsn + "|OSttDate=|OAcqSsn=|MercCode="
		+ mercCode + "|TermCode=|TranAmt=" + realMoney + "|Remark1=|Remark2=|MercUrl="
		+ mercUrl + "|Ip=|SubMercFlag=0|SubMercName=" + merchantName
		+ "|SubMercGoodsName=" + goodsName;
	} else {
		transName = "KHZF";
		plain = "TranAbbr=" + transName + "|MasterID=|MercDtTm=" + nowDateTime
			+ "|TermSsn=" + termSsn + "|OSttDate=|OAcqSsn=|MercCode="
			+ mercCode + "|TermCode=|TranAmt=" + realMoney + "|PayBank="
			+ bankValue + "|AccountType=" + accountType + "|PayType=" + payType
			+ "|Subject=" + goodsName + "|Notice=|Remark1=|Remark2=|MercUrl="
			+ mercUrl + "|Ip=|SubMercFlag=|SubMercName=|SubMercGoodsName=";
	}

	document.getElementById("transName").value = transName;
	document.getElementById("plain").value = plain;
	document.getElementById("termSsn").value = termSsn;
	
	// 总金额
	document.getElementById("confirmAmount").value = realMoney + " 元";
}

// 把订单信息保存到数据库
function saveOrder() {
	$.ajax({
		type : "POST",
		url : locat + '/order/saveOrderInfo.do',
		data : {
			user : '', // TODO 未获取用户信息
			transName : document.getElementById("transName").value,
			termSsn : document.getElementById("termSsn").value,
			merchantNum : document.getElementById("merchantNum").value,
			merchantName : document.getElementById("confirmStation").value,
			goodsName : document.getElementById("confirmDescription").value,
			goodsValue : document.getElementById("money").value,
			price : document.getElementById("realPrice").value,
			number : document.getElementById("confirmNumber").value,
			amount : document.getElementById("realPrice").value
					* document.getElementById("confirmNumber").value,
			payType : document.getElementById("payType").value,
			payTypeContent : document.getElementById("confirmPayType").value,
			payBank : document.getElementById("bankValue").value,
			payBankName : document.getElementById("confirmPayBank").value,
			cardType : document.getElementById("cardType").value,
			cardTypeContent : document.getElementById("confirmPayCardType").value
		},
		dataType : 'json',
		cache : false,
		success : function(data) {
			if ("success" == data.errInfo) {
				// 调用网关进行支付
				document.getElementById("payform").submit();
			}else{
				alert("保存订单信息异常！");
			}
		}
	});
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
