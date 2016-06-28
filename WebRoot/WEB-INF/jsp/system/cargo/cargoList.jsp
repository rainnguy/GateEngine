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
					<form action="cargo.do" method="post" name="cargoForm" id="cargoForm">
						<!-- 检索  -->
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="name" value="${pd.name}" placeholder="这里输入关键词" />
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
									<th>商户号</th>
									<th>商户名</th>
									<th>商品名称</th>
									<th>商品面值</th>
									<th>商品价格</th>
									<th>折扣</th>
									<th>备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty cargoList}">
										<c:forEach items="${cargoList}" var="cargo" varStatus="vs">
											<tr>
												<td style="display:none">
													<input type='hidden' id="id" value="${cargo.id}" />
												</td>
												<td class='center'>${vs.index+1}</td>
												<td>${cargo.merchantCode}</td>
												<td>
													${cargo.merchantName}
												</td>
												<td>
													${cargo.goodsName}
												</td>
												<td>
													${cargo.goodsValue}
												</td>
												<td>
													${cargo.price}
												</td>
												<td>
													${cargo.discount}
												</td>
												<td>
													${cargo.remark}
												</td>
												<td style="width: 60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<a class='btn btn-mini btn-info' title="编辑" onclick="toRoleInfo('${cargo.id}');">
															<i class='icon-edit'></i>
														</a>
														<a class='btn btn-mini btn-danger' title="删除" onclick="deleteCargo('${cargo.id}','${cargo.goodsName}','${cargo.goodsValue}');">
													 		<i class='icon-trash'></i>
													 	</a>
													</div>
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
			$("#cargoForm").submit();
		}
	
		//菜单权限
		function toRoleInfo(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag = true;
			 if(id == '') {
				 diag.Title = "新增商品";
			 } else {
				 diag.Title = "编辑商品";
			 }
			 diag.URL = '<%=basePath%>cargo/toRoleInfo.do?id='+id;
			 diag.Width = 380;
			 diag.Height = 420;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		function deleteCargo(id,goodsName,goodsValue) {
			bootbox.confirm("确定要删除该商品吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>cargo/deleteCargo.do?id="+id+"&goodsName="+goodsName+"&goodsValue="+goodsValue;
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
	</script>
		
	</body>
</html>
