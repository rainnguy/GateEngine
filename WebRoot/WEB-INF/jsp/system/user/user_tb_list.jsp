﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<!-- 当前登录的部门id -->
		<input type="hidden" name="currUdepId" id="currUdepId" value="${currUdepId}" />
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
					</th>
					<th>编号</th>
					<th>用户名</th>
					<th>真实姓名</th>
					<th>角色</th>
					<th><i class="icon-envelope"></i>邮箱</th>
					<th class="center">操作</th>
				</tr>
			</thead>
								
			<tbody>
				
		<!-- 开始循环 -->	
		<c:choose>
			<c:when test="${not empty userList}">
				<c:forEach items="${userList}" var="user" varStatus="vs">
				<tr>
					<td class='center' style="width: 5%">
						<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.USERNAME }"/><span class="lbl"></span></label></c:if>
						<c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" /><span class="lbl"></span></label></c:if>
					</td>
					<td style="width: 12%">${user.NUMBER }</td>
					<td style="width: 15%">${user.USERNAME }</td>
					<td style="width: 15%">${user.NAME }</td>
					<td style="width: 15%">${user.role_name }</td>
					<td style="width: 22%">${user.EMAIL }</td>
					<td style="width: 15%">
						<div class='hidden-phone visible-desktop btn-group'>
							<c:if test="${pd.showEdit}">
							<c:if test="${user.USERNAME != 'admin'}"><a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');"><i class='icon-edit'></i></a></c:if>
							<c:if test="${user.USERNAME == 'admin'}"><a class='btn btn-mini btn-info' title="不能编辑"><i class='icon-edit'></i></a></c:if>
							</c:if>
							<c:choose>
								<c:when test="${user.USERNAME=='admin'}"></c:when>
								<c:otherwise>
									<c:if test="${pd.showDelete}">
									 <a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.USER_ID }','${user.USERNAME }');"><i class='icon-trash'></i></a>
									</c:if>
								</c:otherwise>
							</c:choose>
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
	
</div>
 
 		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${pd.showAdd}">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</c:if>
					<c:if test="${pd.showDelete}">
					<a class="btn btn-small btn-danger" onclick="delAll();" title="批量删除" ><i class='icon-trash'></i></a>
					</c:if>
				</td>
			</tr>
		</table>
		</div>
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/jquery.dataTables.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.dataTables.bootstrap.js"></script>
		
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			var oTable1 = $('#table_report').dataTable( {
			"aoColumns": [
		      { "bSortable": false },
		      null, null,null, null, null,
			  { "bSortable": false }
			] } );
			
			
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			$('[data-rel=tooltip]').tooltip();
		})
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>user/goAddU.do?currUdepId='+$("#currUdepId").val();
			 diag.Width = 360;
			 diag.Height = 460;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					setTimeout("self.location.reload()",100);
					top.jzts();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>user/goEditU.do?user_id='+user_id+'&currUdepId='+$("#currUdepId").val();
			 diag.Width = 360;
			 diag.Height = 460;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					setTimeout("self.location.reload()",100);
					top.jzts();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,USERNAME){
			bootbox.confirm("确定要删除["+USERNAME+"]吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>user/deleteU.do?userId="+userId+"&tm="+new Date().getTime()+"&USRNAME="+USERNAME;
					$.get(url,function(data){
						/* document.location.reload(); */
						setTimeout("self.location.reload()",100);
						top.jzts();
					});
				}
			});
		}
		
		
		//批量删除
		function delAll(){
			bootbox.confirm("确定要删除选中的数据吗?", function(result) {
				if(result) {
					var str = '';
					var USERNAMEstr = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(USERNAMEstr=='') USERNAMEstr += document.getElementsByName('ids')[i].id;
						  	else USERNAMEstr += ';' + document.getElementsByName('ids')[i].id;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						$.ajax({
							type: "POST",
							url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
					    	data: {USER_IDS:str,USERNAMES:USERNAMEstr},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								 $.each(data.list, function(i, list){
									 document.location.reload();
									 top.jzts();
								 });
							}
						});
						
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			//日期框
			$('.date-picker').datepicker();
		});
		</script>
		
	</body>
</html>

