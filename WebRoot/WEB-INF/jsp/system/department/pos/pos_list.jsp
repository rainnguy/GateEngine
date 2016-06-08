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
	<%@ include file="../../admin/top.jsp"%> 
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
								<c:if test="${pd.showAddForPage}">
									<c:choose>
										<c:when test="${not empty listDepForNavigation}">
											<td style="vertical-align:top;"><a href="<%=basePath%>/department/listDepartment.do" class="btn btn-mini btn-purple" title="查看">顶级<i class="icon-arrow-right  icon-on-right"></i></a></td>
											<c:forEach items="${listDepForNavigation}" var="var" varStatus="vsd">
												<td style="vertical-align:top;padding-bottom:10px;"><a href="<%=basePath%>/department/listDepartment.do?parentId=${var.id }&id=${var.id }" class="btn btn-mini btn-purple" title="查看">${var.departmentName }<i class="icon-arrow-right  icon-on-right"></i></a></td>
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
									<th>加油站名称</th>
									<th>终端号</th>
									<th>机具产权</th>
									<th>机具型号</th>
									<th>机身序列号</th>
									<th>寄出日期</th>
									<th>备注</th>
									<th class="center">操作</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty posList}">
										<c:forEach items="${posList}" var="pos" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${pos.departmentName }</td>
												<td>${pos.posNumber }</td>
												<td>${pos.machineProperty }</td>
												<td>${pos.machineModel }</td>
												<td>${pos.machineSerialNumber }</td>
												<td>${pos.mailedDate }</td>
												<td>${pos.remark }</td>
												<td style="width: 60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<c:if test="${pd.showEdit }">
															<a class='btn btn-mini btn-info' title="编辑" onclick="editPosMachine('${pos.id }');">
																<i class='icon-edit'></i>
															</a>
														</c:if>
														<c:if test="${pd.showDelete }">
															<a class='btn btn-mini btn-danger' title="删除" onclick="delPosMachine('${pos.id }','${pos.posNumber }');">
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
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增POS机";
			 diag.URL = '<%=basePath%>department/goPOSMachineInfo.do?departmentId='+$("#parentId").val();
			 diag.Width = 320;
			 diag.Height = 360;
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
		function editPosMachine(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑POS机信息";
			 diag.URL = '<%=basePath%>department/goPOSMachineInfo.do?id='+id+'&departmentId='+$("#parentId").val();
			 diag.Width = 320;
			 diag.Height = 360;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delPosMachine(id,posNumber){
			var msg = "确定要删除该POS机吗？";
			bootbox.confirm(msg, function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>department/delPosMachine.do?id="+id+"&posNumber="+posNumber+"&tm="+new Date().getTime();
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

