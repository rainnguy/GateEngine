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
					<form action="log/listLog.do" method="post" name="logForm" id="logForm">
						<input type="hidden" name="sysTypeSel" id="sysTypeSel" value="${pd.sysTypeSel }" />
						<input type="hidden" name="oprTypeSel" id="oprTypeSel" value="${pd.oprTypeSel }" />
						<!-- 检索  -->
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="keyWord" id="keyWord" value="${pd.keyWord }" placeholder="这里输入关键词" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top;" > 
								 	<select class="chzn-select" name="systemType" id="systemType" data-placeholder="请选择类型" style="vertical-align:top;width: 120px;">
									</select>
								</td>
								<td style="vertical-align:top;" id="operatorTypeSel"> 
								 	<select class="chzn-select" name="operatorType" id="operatorType" data-placeholder="请选择操作" style="vertical-align:top;width: 120px;">
								  	</select>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="logStart" id="logStart" value="${pd.logStart }" placeholder="开始时间" readonly title="日志记录开始"/>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="logEnd" id="logEnd" value="${pd.logEnd }" placeholder="结束时间" readonly title="日志记录结束"/>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<%-- <td><input class="span10 date-picker" name="logStart" id="logStart"  value="${pd.logStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="日志记录开始"/></td>
								<td><input class="span10 date-picker" name="logEnd" name="logEnd"  value="${pd.logEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="日志记录结束"/></td> --%>
								<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover" style="width:1183px;max-width:1183px">
							<thead>
								<tr>
									<th style="width: 5%" class="center">序号</th>
									<th style="width: 15%">操作者</th>
									<th style="width: 12%">类型</th>
									<th style="width: 12%">操作</th>
									<th style="width: 12%">模块</th>
									<th style="width: 18%">操作时间</th>
									<th style="width: 26%">备注</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty logList}">
										<c:forEach items="${logList}" var="log" varStatus="vs">
											<tr>
												<td class="center">${vs.index+1}</td>
												<td>${log.name }</td>
												<td>${log.systemType }</td>
												<td>${log.operatorType }</td>
												<td>${log.menuName }</td>
												<td>${log.createDate}</td>
												<td>${log.remark}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="7" class="center">没有相关数据</td>
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
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<!-- <script src="static/js/ace.min.js"></script> -->
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
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
				url : "<%=basePath%>log/getOperatorType.do",
		    	data: {"systemType":""},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						//清空
						var emptyOption = [];
						$("#operatorType").html(emptyOption.join(''));
						$("#operatorType").trigger('liszt:updated');
						//添加新的option
						var option = [];
						option.push("<option value=''></option>");
						option.push("<option value=''>全部</option>");
						$.each(data.operatorTypeList,function(index,item){
							if($("#oprTypeSel").val() != null && $("#oprTypeSel").val()== item.value){
								option.push("<option value='"+item.value+"' selected='selected'>",item.name,"</option>");
							}else{
								option.push("<option value='"+item.value+"'>",item.name,"</option>");
							}
						});
						$("#operatorType").html(option.join(''));
						$("#operatorType").trigger('liszt:updated');
					}else{
						alert("系统错误，请联系管理员");
					}
				}
			});
		}
		
		function changOperator(systemType){
			$.ajax({
				type: "POST",
				url : "<%=basePath%>log/getOperatorType.do",
		    	data: {"systemType":systemType},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						//清空
						var emptyOption = [];
						$("#operatorType").html(emptyOption.join(''));
						$("#operatorType").trigger('liszt:updated');
						//添加新的option
						var option = [];
						option.push("<option value=''></option>");
						option.push("<option value=''>全部</option>");
						$.each(data.operatorTypeList,function(index,item){
							if($("#oprTypeSel").val() != null && $("#oprTypeSel").val()== item.value){
								option.push("<option value='"+item.value+"' selected='selected'>",item.name,"</option>");
							}else{
								option.push("<option value='"+item.value+"'>",item.name,"</option>");
							}
						});
						$("#operatorType").html(option.join(''));
						$("#operatorType").trigger('liszt:updated');
					}else{
						alert("系统错误，请联系管理员");
					}
				}
			});
		}
		</script>
		
		<script type="text/javascript">
		$(function() {
			//时间框
			$("#logStart").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#logStart").datetimepicker("setEndDate", $("#logEnd").val());
		    });
			$("#logEnd").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#logEnd").datetimepicker("setStartDate", $("#logStart").val());
		    });
			
			//时间清除实现
			$("#logStart").next().find("i").on("click",function(){
				$("#logStart").val("");
			});
			$("#logEnd").next().find("i").on("click",function(){
				$("#logEnd").val("");
			});
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true});
			
			//初始话类型下拉框
			initSystemType();
			//当类型下拉框非null或非空时，调用
			if($("#sysTypeSel").val()==null || $("#sysTypeSel").val()==""){
				initOperatorType();
			}else{
				changOperator($("#sysTypeSel").val());
			}
			
			//注册类型下拉框事件  
		    $("#systemType").change(function(){
		    	$("#sysTypeSel").val($(this).val());
		        changOperator($(this).val());
		    });
		    $("#operatorType").change(function(){
		    	$("#oprTypeSel").val($(this).val());
		    });
		});
		
		<%-- //导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var lastLoginStart = $("#lastLoginStart").val();
			var lastLoginEnd = $("#lastLoginEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&lastLoginStart='+lastLoginStart+'&lastLoginEnd='+lastLoginEnd+'&ROLE_ID='+ROLE_ID;
		} --%>
		</script>
	</body>
</html>

