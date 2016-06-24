<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<%-- 	<%@include file="/common/common.jsp"%> --%>
	
			
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath%>static/1.9.1/jquery.min.js'>\x3C/script>");</script>
		<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>static/js/ace-elements.min.js"></script>
		<!-- <script src="static/js/ace.min.js"></script> -->
		
		<script type="text/javascript" src="<%=basePath%>static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="<%=basePath%>static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="<%=basePath%>static/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="<%=basePath%>static/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script><!-- 日期框 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script><!--提示框-->
		
	<script type="text/javascript" src="<%=basePath%>static/js/cargo/cargoList.js"></script>
</head>

<body>
	<div>
	<form action="user/listUsers.do" method="post" name="userForm" id="userForm">
		<!-- 检索  -->
		<table>
			<tr>
				<td>
					<span class="input-icon">
						<input autocomplete="off" id="nav-search-input" type="text" name="keyWord" value="" placeholder="这里输入关键词" />
						<i id="nav-search-icon" class="icon-search"></i>
					</span>
				</td>
				<td style="vertical-align:top;">
					<button class="btn btn-mini btn-light" onclick="search();" title="检索">
						<i id="nav-search-icon" class="icon-search"></i>
					</button>
				</td>
			</tr>
		</table>
		
		<table id="table_report" class="table table-striped table-bordered table-hover" style="width:100%;max-width:100%">
			<!-- 表格头 -->
			<thead>
				<tr style="height:41px">
					<th style="width:4%" class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
					<th style="width:15%">商户号</th>
					<th style="width:25%">商户名</th>
					<th style="width:9%">商品名称</th>
					<th style="width:8%">商品面值</th>
					<th style="width:8%">商品价格</th>
					<th style="width:6%">折扣</th>
					<th style="width:17%">备注</th>
					<th style="width:8%" class="center">操作</th>
				</tr>
			</thead>
			
			<tbody>
				<tr style="height:41px;">
					<td class='center' style="width:4%">
<%-- 						<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.USERNAME }" alt="${user.PHONE }"/><span class="lbl"></span></label></c:if> --%>
						<c:if test="${user == 'admin'}"><label><input type='checkbox' /><span class="lbl"></span></label></c:if>
<!-- 						<input type='checkbox' name='ids' /> -->
					</td>
					<td>1111</td>
					<td>2222</td>
					<td>3333</td>
					<td>4444</td>
					<td>5555</td>
					<td>6666</td>
					<td>7777</td>
					<td style="width:8%">
						<div class='hidden-phone visible-desktop btn-group'>
<%-- 							<c:if test="${pd.showEdit}"> --%>
								<c:if test="${user=='admin'}">
									<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('admin');"><i class='icon-edit'></i></a>
								</c:if>
<%-- 							</c:if> --%>
<%-- 							<c:if test="${pd.showDelete}"> --%>
								<c:choose>
									<c:when test="${user=='admin'}">
										<a class='btn btn-mini btn-danger' title="不能删除"><i class='icon-trash'></i></a>
									</c:when>
									<c:otherwise>
										<a class='btn btn-mini btn-danger' title="删除" onclick="delUser('admin');"><i class='icon-trash'></i></a>
									</c:otherwise>
								</c:choose>
<%-- 							</c:if> --%>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
<%-- 					<c:if test="${pd.showAdd}"> --%>
						<a class="btn btn-small btn-success" onclick="add();">新增</a>
<%-- 					</c:if> --%>
<%-- 					<c:if test="${pd.showDelete}"> --%>
<!-- 						<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a> -->
<%-- 					</c:if> --%>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
	</form>
	</div>

</body>
</html>