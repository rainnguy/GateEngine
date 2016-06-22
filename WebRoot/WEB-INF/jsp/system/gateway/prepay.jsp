<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String goodsName = java.net.URLEncoder.encode("加油券","GBK");
%>
<html>
<head>
	<%@include file="/common/common.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/static/js/gateway/prepay.js"></script>
</head>

<body>
	<div class="row-fluid">
	<div class="span12">
		<div class="widget-box">
			<div class="widget-header widget-header-blue widget-header-flat wi1dget-header-large">
				<h4 class="lighter">富仁移动支付</h4>
			</div>
			<div class="widget-body">
			 <div class="widget-main">
				
				<div class="row-fluid">
					<div id="fuelux-wizard" class="row-fluid hidden">
					  <ul class="wizard-steps">
						<li data-target="#step1" class="active"><span class="step">1</span> <span class="title">选择站点</span></li>
						<li data-target="#step2"><span class="step">2</span> <span class="title">选择商品</span></li>
						<li data-target="#step3"><span class="step">3</span> <span class="title">选择支付方式</span></li>
						<li data-target="#step4"><span class="step">4</span> <span class="title">确认支付</span></li>
					  </ul>
					</div>
					
					<hr />
					
					<div class="step-content row-fluid position-relative">
						<div class="step-pane active" id="step1">
							<h3 class="lighter block green" style="position: relative;left: 95px;">站点信息</h3>
							
							<div class="form-horizontal">
								<div class="control-group">
									<label class="control-label" for="station">选择站点</label>
									<div class="controls">
										<span class="span12">
											<select class="span3" id="station" name="station" onchange="showUseAbleStations()">
												<c:forEach items="${orgValue}" var="map">
													<option value="${map.key}">${map.value}</option>
												</c:forEach>
											</select>
											<input type="hidden" id="merchantNum" value="" />
										</span>
									</div>
									<span class="help-inline" id="stationHelp"></span>
								</div>
								
								<div class="control-group">
									<label class="control-label">可使用站点</label>
									<div class="controls">
										<span class="span12">
											<textarea class="span8" id="useAbleStations" style="width: 450px; height: 260px; color: #0000FF; resize: none" readonly="readonly" ></textarea>
										</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="step-pane" id="step2">
							<h3 class="lighter block green" style="position: relative;left: 95px;">商品信息</h3>
							
							<div class="form-horizontal">
								<div class="control-group">
									<label class="control-label">名称</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input type="text" class="span3" id="description" value="加油券" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
											<input type="hidden" id="goodsName" value="<%=goodsName%>" />
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="money">面值</label>
									<div class="controls">
										<span class="span12">
											<select class="span2" id="money" name="money" onchange="showValue()">
												<option value="50">50</option>
												<option value="100">100</option>
												<option value="150">150</option>
												<option value="200" selected>200</option>
												<option value="300">300</option>
												<option value="500">500</option>
												<option value="1000">1000</option>
											</select>
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">价格</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input type="hidden" name="discount" id="discount" value="0.99" />
											<input type="text" class="span3" id="realPrice" style="color: #0000FF; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" value="198" readonly="readonly" />
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">数量</label>
									<div class="controls">
										<span class="span6 input-icon input-icon-right">
											<input class="span3" type="text" name="number" id="number" value="1" 
												style="color: #0000FF; ime-mode:Disabled" onchange="changeNumber()" onkeyup="value=value.replace(/[^\d]/g,'')"/>
											<span class="help-inline" id="numberHelp" style="color: #FF0000"></span>
										</span>
									</div>
								</div>
							</div>
						</div>
						
						<div class="step-pane" id="step3">
							<h3 class="lighter block green" style="position: relative;left: 95px;">支付信息</h3>
							
							<div class="form-horizontal">
								<div class="control-group">
									<label class="control-label">支付方式</label>
									<div class="controls">
										<span class="span12">
											<label class="blue"><input id="payType" name="payType" value="0" type="radio" onclick="changePayType()"/><span class="lbl"> 手机快捷支付</span></label>
											<label class="blue"><input id="payType" name="payType" value="1" type="radio" onclick="changePayType()" checked /><span class="lbl"> 网银支付</span></label>
										</span>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">卡种</label>
									<div class="controls">
										<span class="span12">
											<label class="blue"><input id="cardType" name="cardType" value="1" type="radio" onclick="changeCardType()" checked /><span class="lbl"> 借记卡</span></label>
											<label class="blue"><input id="cardType" name="cardType" value="U" type="radio" onclick="changeCardType()"/><span class="lbl"> 信用卡</span></label>
										</span>
									</div>
								</div>
								
								<div class="control-group">	
									<label class="control-label" for="bank">支付银行</label>
									<div class="controls">
										<span class="span12">
											<select class="span3" id="bank" name="bank" onchange="changeBank()">
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
										</span>
										<span class="help-inline" id="bankHelp" style="color: #FF0000"></span>
									</div>
									<input type="hidden" id="bankValue" value="" />
								</div>
							</div>
						</div>
						
						<div class="step-pane" id="step4">
							<h3 class="lighter block green" style="position: relative;left: 95px;">确认支付</h3>
							
							<div class="form-horizontal">
								<div class="control-group">
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">站点：</label>
											<input type="text" class="span8" id="confirmStation" value="" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">商品：</label>
											<input type="text" class="span8" id="confirmDescription" value="加油券" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">面值：</label>
											<input type="text" class="span8" id="confirmMoney" value="200 元" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">价格：</label>
											<input type="text" class="span8" id="confirmPrice" value="198 元" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">数量：</label>
											<input type="text" class="span8" id="confirmNumber" value="1" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">总金额：</label>
											<input type="text" class="span8" id="confirmAmount" value="" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">支付方式：</label>
											<input type="text" class="span8" id="confirmPayType" value="网银支付" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">支付银行：</label>
											<input type="text" class="span8" id="confirmPayBank" value="" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									
									<div class="controls">
										<span class="span12 input-icon input-icon-right">
											<label class="control-label">卡种：</label>
											<input type="text" class="span8" id="confirmPayCardType" value="借记卡" style="color: #000000; outline: none; border:none; background:none; background:rgba(0, 0, 0, 0)" readonly="readonly"/>
										</span>
									</div>
									<input type="hidden" id="termSsn" value="" />
								</div>
							</div>
							
							<div class="control-group">
								<form action="http://124.74.239.32/payment/main" id="payform" method="post">
									<input type="hidden" name="transName" id="transName" value="" />
									<input type="hidden" name="Plain" id="plain" value="" />
									<input type="hidden" name="Signature" id="signature" value="" />
								</form>
							</div>
						</div>
					</div>
					
					<hr />
					
					<div class="row-fluid wizard-actions">
						<button class="btn btn-prev" id="before"><i class="icon-arrow-left"></i> 上一步</button>
						<button class="btn btn-success btn-next" id="after" data-last="支付 " disabled="disabled">下一步 <i class="icon-arrow-right icon-on-right"></i></button>
					</div>
				</div>
			 </div><!--/widget-main-->
			</div><!--/widget-body-->
		</div>
	</div>
</div>
		
</body>
</html>