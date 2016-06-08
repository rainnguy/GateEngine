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
	<base href="<%=basePath%>">
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%> 
	</head> 
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
  			<div class="row-fluid">
				<div class="row-fluid">
					<form action="department/listDepartment.do" method="post" name="departmentForm" id="departmentForm">
						<input type="hidden" name="parentId" id="parentId" value="${pd.parentId }"/>
						<!-- 检索  -->
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="keyValue" value="${pd.keyValue }" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
								<c:if test="${pd.showAddForPage}">
									<c:choose>
										<c:when test="${not empty listDepForNavigation}">
											<td style="vertical-align:top;"><a href="<%=basePath%>/department/listDepartment.do" class="btn btn-mini btn-purple" title="查看">顶级<i class="icon-arrow-right  icon-on-right"></i></a></td>
											<c:forEach items="${listDepForNavigation}" var="var" varStatus="vsd">
												<td style="vertical-align:top;"><a href="<%=basePath%>/department/listDepartment.do?parentId=${var.id }&id=${var.id }" class="btn btn-mini btn-purple" title="查看">${var.departmentName }<i class="icon-arrow-right  icon-on-right"></i></a></td>
											</c:forEach>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>序号</th>
									<th>名称</th>
									<th>类型</th>
									<th>商户号</th>
									<th>商户名称</th>
									<th>联系人</th>
									<th>联系电话</th>
									<th>地址</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty departmentList}">
										<c:forEach items="${departmentList}" var="dep" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>
													<c:if test="${pd.showPOSMachine || dep.departmentType != 3}">
														<a href="<%=basePath%>/department/listDepartment.do?parentId=${dep.id }&id=${dep.id }" title="查看下级"><i class="icon-arrow-right  icon-on-right"></i>&nbsp;
													</c:if>
													${dep.departmentName }
												</td>
												<td style="width:6.5%;">
													${dep.departmentTypeName }
												</td>
												<td>${dep.merchantNum }</td>
												<td>${dep.merchantName }</td>
												<td style="width:7%;">${dep.concator }</td>
												<td>${dep.phone}</td>
												<td style="width:15%;">${dep.address}</td>
												<td style="width: 60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<c:if test="${pd.showEdit }">
															<a class='btn btn-mini btn-info' title="编辑" onclick="editDepartment('${dep.id }');">
																<i class='icon-edit'></i>
															</a>
														</c:if>
														<c:if test="${pd.showDelete && pd.showAddForPage }">
															<a class='btn btn-mini btn-danger' title="删除" onclick="delDepartment('${dep.id }','${dep.departmentName }','${dep.departmentTypeName }');">
														 		<i class='icon-trash'></i>
														 	</a>
														</c:if>
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
										<c:if test="${pd.showAdd && pd.showAddForPage}">
											<a class="btn btn-small btn-success" onclick="add();">新增</a>
										</c:if>
									</td>
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
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#departmentForm").submit();
		}
		
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增机构";
			 diag.URL = '<%=basePath%>department/goDepartmentInfo.do?parentId='+$("#parentId").val();
			 diag.Width = 320;
			 diag.Height = 500;
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
		
		//修改
		function editDepartment(departmentId){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑机构";
			 diag.URL = '<%=basePath%>department/goDepartmentInfo.do?id='+departmentId;
			 diag.Width = 320;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delDepartment(departmentId,departmentName,departmentType){
			var msg = "确定要删除："+departmentName;
			//type   1:总公司；2：分公司；3：加油站
			if(departmentType == '总公司') {
				msg = msg+"及其下的所有分公司、加油站";
			} else if(departmentType == '分公司') {
				msg = msg+"及其下的所有加油站";
			}
			bootbox.confirm(msg+"吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>department/deleteDepartment.do?departmentName="+departmentName+"&departmentId="+departmentId+"&departmentType="+departmentType+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>user/goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		</script>
	</body>
</html>

