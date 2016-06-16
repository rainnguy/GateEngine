<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String goodsName = java.net.URLEncoder.encode("加油券","GBK");
%>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<!-- 引入 -->
<c:set value="${Plain}" var="ttt"></c:set>

<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<!-- <script src="static/js/ace.min.js"></script> -->

<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<!-- 确认窗口 -->
<script type="text/javascript"
	src="<%=basePath%>/static/js/gateway/payPage.js"></script>
<!-- 支付页面处理js -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<!--提示框-->

<body>
	<div>
		<div style="width: 35%; background-color: #FFF5EE; float: left;">
			<br>
			<div>
				<label>选择站点</label> <select class="input-large" id="station"
					name="station" onchange="showUseAbleStations()">
					<c:forEach items="${orgValue}" var="map">
						<option value="${map.key}">${map.value}</option>
					</c:forEach>
				</select> <input type="hidden" name="merchantNum" id="merchantNum" value="" />
				<input type="hidden" name="merchantName" id="merchantName" value="" />
			</div>
			<br>
			<div>
				<label>可使用的站点</label>
				<textarea id="useAbleStations"
					style="width: 355px; height: 400px; color: #0000FF; resize: none"
					readonly="readonly" ></textarea>
			</div>
		</div>
		<div style="width: 65%; background-color: #AEEEEE; float: right;">
			<div class="container-fluid" id="main-container">
				<div id="page-content" class="clearfix">
					<div class="row-fluid">
						<div class="row-fluid">
							<%-- 					<input type="hidden" name="mercCode" id="mercCode" value="${mercCode}" /> --%>
							<%-- 					<input type="hidden" name="oprTypeSel" id="oprTypeSel" value="${pd.oprTypeSel }" /> --%>
							<form action="http://124.74.239.32/payment/main" method="post"
								name="payForm" id="payForm">
								<div>
									<br>
									<div>
										<label>商品信息</label> <label name="description"
											id="description">加油券</label>
										<input type="hidden" id="goodsName" value="<%=goodsName%>" />
									</div>
									<br>
									<div>
										<label>面值</label> <select id="money" name="money"
											onchange="showValue()">
											<option value="50">50</option>
											<option value="100">100</option>
											<option value="150">150</option>
											<option value="200" selected>200</option>
											<option value="300">300</option>
											<option value="500">500</option>
											<option value="1000">1000</option>
										</select>
									</div>
									<div>
										<label>价格</label> <input type="hidden" name="discount"
											id="discount" value="0.99" /> <label name="realMoney"
											id="realMoney" style="color: #FF0000">198</label>
									</div>
									<br>
									<div>
										<label>数量</label>
										<div>
											<input type="text" name="number" id="number" value="1"
												onkeyup="value=value.replace(/[^\d]/g,'')"
												style="color: #0000FF" ; ime-mode:Disabled" />
										</div>
									</div>
									<br>
									<div>
										<label>支付方式</label> <select id="payType" name="payType"
											onchange="changePayType()">
											<option value="0">手机快捷支付</option>
											<option value="1" selected>网银支付</option>
										</select>
									</div>
									<br>
									<div>
										<label>银行</label> <select id="bank" name="bank"
											onchange="changeBank()" disabled="disabled">
											<option value="" selected>选择银行</option>
											<option value="vbank">虚拟银行</option>
											<option value="cmb">招商银行</option>
											<option value="icbc">中国工商银行</option>
											<option value="ccb">中国建设银行</option>
											<option value="abc">中国农业银行</option>
											<option value="cmbc">中国民生银行</option>
											<option value="spdb">上海浦东发展银行</option>
											<option value="cgb">广东发展银行</option>
											<option value="cib">兴业银行</option>
											<option value="ceb">光大银行</option>
											<option value="comm">交通银行</option>
											<option value="boc">中国银行</option>
											<option value="citic">中信银行</option>
											<option value="bos">上海银行</option>
											<option value="pingan">平安银行</option>
											<option value="psbc">邮政储蓄</option>
										</select>
									</div>
									<div>
										<input type="hidden" name="transName" id="transName" value="" />
										<br> <input type="hidden" name="Plain" id="plain" value="" />
										<br> <input type="hidden" name="Signature" id="signature"
											value="" /> <br> <input type="submit"
											id="submit" name="submit" class="orgBtnlong" value="支付"
											disabled="disabled"/>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div style="clear: both;"></div>
	</div>


	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>


	<script type="text/javascript">
	$(top.hangge());
	
	//检索
	function search(){
		top.jzts();
		$("#logForm").submit();
	}
	
	//初始化类型
	function initSystemType(){
		$.ajax({
			type: "POST",
			url : "<%=basePath%>log/getSystemType.do",
			dataType:'json',
			data: {},
			cache: false,
			success: function(data){
				if("success" == data.result){
					//清空
					var emptyOption = [];
					$("#systemType").html(emptyOption.join(''));
					$("#systemType").trigger('liszt:updated');
					//添加新的option
					var option = [];
					option.push("<option value=''></option>");
					option.push("<option value=''>全部</option>");
					$.each(data.systemTypeList,function(index,item){
						if($("#sysTypeSel").val() != null && $("#sysTypeSel").val()== item.value){
							option.push("<option value='"+item.value+"' selected='selected'>",item.name,"</option>");
						}else{
							option.push("<option value='"+item.value+"'>",item.name,"</option>");
						}
					});
					$("#systemType").html(option.join(''));
					$("#systemType").trigger('liszt:updated');
				}else{
					alert("系统错误，请联系管理员");
				}
			}
		});
	}
	
	//初始化操作
	function initOperatorType(){
		$.ajax({
			type: "POST",
			url : "log/getOperatorType.do",
			data : {
				"systemType" : ""
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" == data.result) {
					//清空
					var emptyOption = [];
					$("#operatorType").html(emptyOption.join(''));
					$("#operatorType").trigger('liszt:updated');
					//添加新的option
					var option = [];
					option.push("<option value=''></option>");
					option.push("<option value=''>全部</option>");
					$
							.each(
									data.operatorTypeList,
									function(index, item) {
										if ($("#oprTypeSel").val() != null
												&& $("#oprTypeSel")
														.val() == item.value) {
											option
													.push(
															"<option value='"+item.value+"' selected='selected'>",
															item.name,
															"</option>");
										} else {
											option
													.push(
															"<option value='"+item.value+"'>",
															item.name,
															"</option>");
										}
									});
					$("#operatorType").html(option.join(''));
					$("#operatorType").trigger('liszt:updated');
				} else {
					alert("系统错误，请联系管理员");
				}
			}
		});
	}
	</script>
</body>
</html>