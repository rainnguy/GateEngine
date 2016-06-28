<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/common/common.jsp"%>
<%-- 		<script type="text/javascript" src="<%=basePath%>static/js/cargo/cargoList.js"></script> --%>
	</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
  			<div class="row-fluid">
				<div class="row-fluid">
					<form action="<%=basePath%>order/orderList.do" method="post" name="orderForm" id="orderForm">
						<!-- 检索  -->
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="code" value="${pd.code}" placeholder="这里输入订单号" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th>订单号</th>
									<th>下单时间</th>
									<th>订单状态</th>
									<th>下单用户</th>
									<th>商户名称</th>
									<th>商品名称</th>
									<th>商品面值</th>
									<th>商品价格</th>
									<th>购买数量</th>
									<th>总金额</th>
									<th>支付方式</th>
									<th>支付银行</th>
									<th>银行卡种类</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty orderList}">
										<c:forEach items="${orderList}" var="order" varStatus="vs">
											<tr>
												<td class='center'>${vs.index+1}</td>
												<td>${order.orderCode}</td>
												<td>
													${order.orderTime}
												</td>
												<td>
													${order.orderStatusName}
												</td>
												<td>
													${order.userCode}
												</td>
												<td>
													${order.merchantName}
												</td>
												<td>
													${order.goodsName}
												</td>
												<td>
													${order.goodsValue}
												</td>
												<td>
													${order.price}
												</td>
												<td>
													${order.number}
												</td>
												<td>
													${order.amount}
												</td>
												<td>
													${order.payTypeContent}
												</td>
												<td>
													${order.payBankName}
												</td>
												<td>
													${order.cardTypeContent}
												</td>
												<td>
													${order.remark}
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="10" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;">
										<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">
											${page.pageStr}
										</div>
									</td>
								</tr>
							</table>
						</div>
					</form>
				</div>
  			</div>
		</div>
	</div>
		
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
		<i class="icon-double-angle-up icon-only"></i>
	</a>
	
	<script type="text/javascript">
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#orderForm").submit();
		}
	</script>
		
	</body>
</html>
