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
	<link href="static/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="user/listUsers.do" method="post" name="userForm" id="userForm">
			<!-- 当前登录的部门id -->
			<input type="hidden" name="currUdepId" id="currUdepId" value="${currUdepId}" />
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="keyWord" value="${pd.keyWord }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<%-- <td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="最近登录开始"/></td> --%>
					<%-- <td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd"  value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="最近登录结束"/></td> --%>
					<td>
						<div class="input-append date form_datetime">
						    <input style="width:120px;" size="16" type="text" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart }" placeholder="开始时间" readonly title="最近登录开始"/>
						    <span class="add-on"><i class="icon-remove"></i></span>
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</td>
					<td>
						<div class="input-append date form_datetime">
						    <input style="width:120px;" size="16" type="text" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd }" placeholder="结束时间" readonly title="最近登录结束"/>
						    <span class="add-on"><i class="icon-remove"></i></span>
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择角色" style="vertical-align:top;width: 120px;">
						<option value=""></option>
						<option value="">全部</option>
						<c:forEach items="${roleList}" var="role">
							<option value="${role.id }" <c:if test="${pd.ROLE_ID==role.id}">selected</c:if>>${role.name }</option>
						</c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${user.USERNAME != 'admin'}">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="window.location.href='<%=basePath%>/user/listtabUsers.do';" title="切换模式"><i id="nav-search-icon" class="icon-exchange"></i></a></td>
					</c:if>
					<c:if test="${pd.showExport}">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
					<%-- <c:if test="${pd.showImport}">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a></td>
					</c:if> --%>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover" style="width:1183px;max-width:1183px">
				<thead>
					<tr style="height:41px">
						<th style="width:4%" class="center"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
						<th style="width:4%">序号</th>
						<th style="width:6%">编号</th>
						<th style="width:8%">用户名</th>
						<th style="width:8%">真实姓名</th>
						<th style="width:8%">角色</th>
						<th style="width:12%">所属机构</th>
						<th style="width:10%">联系电话</th>
						<th style="width:10%">邮箱地址</th>
						<th style="width:12%"><i class="icon-time hidden-phone"></i>最近登录时间</th>
						<th style="width:10%">上次登录IP</th>
						<th style="width:8%" class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty userList}">
						<c:forEach items="${userList}" var="user" varStatus="vs">
									
							<tr style="height:41px;">
								<td class='center' style="width: 30px;">
									<c:if test="${user.USERNAME != 'admin'}"><label><input type='checkbox' name='ids' value="${user.USER_ID }" id="${user.USERNAME }" alt="${user.PHONE }"/><span class="lbl"></span></label></c:if>
									<c:if test="${user.USERNAME == 'admin'}"><label><input type='checkbox' disabled="disabled" /><span class="lbl"></span></label></c:if>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${user.NUMBER }</td>
								<td>${user.USERNAME }</td>
								<td>${user.NAME }</td>
								<td>${user.role_name }</td>
								<td>${user.departmentName }</td>
								<td>${user.PHONE }</td>
								<td>${user.EMAIL }</td>
								<td>${user.LAST_LOGIN}</td>
								<td>${user.IP}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<c:if test="${pd.showEdit}">
											<c:if test="${user.USERNAME != 'admin'}">
												<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.USER_ID }');"><i class='icon-edit'></i></a>
											</c:if>
										</c:if>
										<c:if test="${pd.showDelete}">
											<c:choose>
												<c:when test="${user.USERNAME=='admin'}">
													<a class='btn btn-mini btn-danger' title="不能删除"><i class='icon-trash'></i></a>
												</c:when>
												<c:otherwise>
													 <a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.USER_ID }','${user.USERNAME }');"><i class='icon-trash'></i></a>
												</c:otherwise>
											</c:choose>
										</c:if>
									</div>
								</td>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="12" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
					
				
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${pd.showAdd}">
						<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</c:if>
					<c:if test="${pd.showDelete}">
						<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i></a>
					</c:if>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
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
		<!-- <script src="static/js/ace.min.js"></script> -->
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script><!-- 日期框 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		
		//去发送电子邮件页面
		function sendEmail(EMAIL){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送电子邮件";
			 diag.URL = '<%=basePath%>head/goSendEmail.do?EMAIL='+EMAIL;
			 diag.Width = 660;
			 diag.Height = 470;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//去发送短信页面
		function sendSms(phone){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="发送短信";
			 diag.URL = '<%=basePath%>head/goSendSms.do?PHONE='+phone+'&msg=appuser';
			 diag.Width = 600;
			 diag.Height = 265;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
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
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>user/goEditU.do?user_id='+user_id+'&currUdepId='+$("#currUdepId").val();
			 diag.Width = 360;
			 diag.Height = 480;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,USERNAME){
			bootbox.confirm("确定要删除["+USERNAME+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>user/deleteU.do?userId="+userId+"&tm="+new Date().getTime()+"&USRNAME="+USERNAME;
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var USERNAMEstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(USERNAMEstr=='') USERNAMEstr += document.getElementsByName('ids')[i].id;
						  	else USERNAMEstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
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
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
						    	data: {USER_IDS:str,USERNAMES:USERNAMEstr},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
						
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			//时间框
			$("#lastLoginStart").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#lastLoginStart").datetimepicker("setEndDate", $("#lastLoginEnd").val());
		    });
			$("#lastLoginEnd").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#lastLoginEnd").datetimepicker("setStartDate", $("#lastLoginStart").val());
		    });
			
			//时间清除实现
			$("#lastLoginStart").next().find("i").on("click",function(){
				$("#lastLoginStart").val("");
			});
			$("#lastLoginEnd").next().find("i").on("click",function(){
				$("#lastLoginEnd").val("");
			});
			
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
			var keyWord = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?keyWord='+keyWord+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID+'&currUdepId='+$("#currUdepId").val();
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

