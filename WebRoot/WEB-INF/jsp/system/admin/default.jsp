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
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

</head>
<body>

	<div class="container-fluid" id="main-container">
		

			<div id="page-content" class="clearfix">

				<div class="page-header position-relative">
					<h1>
						后台首页 <small><i class="icon-double-angle-right"></i> </small>
					</h1>
				</div>
				<!--/page-header-->

				<div class="row-fluid">

					<div class="space-6"></div>
					<div class="row-fluid">
						<%
							String strXML = "";
							strXML += "<graph caption='营业额月报表' xAxisName='月份' yAxisName='营业额（万元）' decimalPrecision='0' formatNumberScale='0'>";
							strXML += "<set name='1' value='0' color='AFD8F8'/>";
							strXML += "<set name='2' value='0' color='F6BD0F'/>";
							strXML += "<set name='3' value='0' color='8BBA00'/>";
							strXML += "<set name='4' value='0' color='FF8E46'/>";
							strXML += "<set name='5' value='0' color='008E8E'/>";
							strXML += "<set name='6' value='0' color='D64646'/>";
							strXML += "<set name='7' value='0' color='8E468E'/>";
							strXML += "<set name='8' value='0' color='588526'/>";
							strXML += "<set name='9' value='0' color='B3AA00'/>";
							strXML += "<set name='10' value='0' color='008ED6'/>";
							strXML += "<set name='11' value='0' color='9D080D'/>";
							strXML += "<set name='12' value='0' color='A186BE'/>";
							strXML += "</graph>";
							
							String strWeekXML = "";
							strWeekXML += "<graph caption='营业额周报表' xAxisName='日期' yAxisName='营业额（元）' decimalPrecision='0' formatNumberScale='0'>";
							strWeekXML += "<set name='1' value='0' color='AFD8F8'/>";
							strWeekXML += "<set name='2' value='0' color='F6BD0F'/>";
							strWeekXML += "<set name='3' value='0' color='8BBA00'/>";
							strWeekXML += "<set name='4' value='0' color='FF8E46'/>";
							strWeekXML += "<set name='5' value='0' color='008E8E'/>";
							strWeekXML += "<set name='6' value='0' color='D64646'/>";
							strWeekXML += "<set name='7' value='0' color='8E468E'/>";
							strWeekXML += "</graph>";
							//Create the chart - Column 3D Chart with data from strXML variable using dataXML method
						%>
						<!-- 月报 折线图 -->
						<div class="center">
							月报表查询：
							<input type="text" name="departmentName" id="departmentName" value="${pd.departmentName}" maxlength="32" placeholder="请选择机构" title="机构"  onfocus="showDepTree('${pd.currUdepId}','departmentId','departmentName');" onclick="showDepTree('${pd.currUdepId}','departmentId','departmentName');"/>
							<input type="hidden" name="departmentId" id="departmentId" value="${pd.departmentId}"/>
							<select name="month" id="month" class="input_txt" onchange="setMUR()"  title="请选择月份">
								<option value="0">全年</option>
								<option value="1" onclick="">1月份</option>
								<option value="2" onclick="">2月份</option>
								<option value="3" onclick="">3月份</option>
								<option value="4" onclick="">4月份</option>
								<option value="5" onclick="">5月份</option>
								<option value="6" onclick="">6月份</option>
								<option value="7" onclick="">7月份</option>
								<option value="8" onclick="">8月份</option>
								<option value="9" onclick="">9月份</option>
								<option value="10" onclick="">10月份</option>
								<option value="11" onclick="">11月份</option>
								<option value="12" onclick="">12月份</option>
							</select>
							<button class="btn btn-mini btn-light" style="margin-bottom:10px;" onclick="queryMonthCharts();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
							<div id="divMonthChart">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Line.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="<%=strXML%>" />
												<jsp:param name="chartId" value="monthChart" />
												<jsp:param name="chartWidth" value="800" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>
						<!-- 柱状图 -->
						<div class="center" style="padding-top:20px;">
							周报表查询：
							<input type="text" name="departmentName" id="departmentNameWeek" value="${pd.queryDepartmentName}" maxlength="32" placeholder="请选择机构" title="机构"  onfocus="showDepTree('${pd.currUdepId}','departmentIdWeek','departmentNameWeek');" onclick="showDepTree('${pd.currUdepId}','departmentIdWeek','departmentNameWeek');"/>
							<input type="hidden" name="departmentId" id="departmentIdWeek" value="${pd.departmentId}"/>
							<input class="span10 date-picker" name="queryWeekDate" id="queryWeekDate"  value="${pd.queryDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="查询日期" title="查询日期"/>
							<button class="btn btn-mini btn-light" style="margin-bottom:10px;" onclick="querhWeekCharts();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
							<div id="weekCharts">
								<table border="0" width="50%">
									<tr>
										<td><jsp:include
												page="../../FusionChartsHTMLRenderer.jsp" flush="true">
												<jsp:param name="chartSWF" value="static/FusionCharts/Column3D.swf" />
												<jsp:param name="strURL" value="" />
												<jsp:param name="strXML" value="<%=strWeekXML%>" />
												<jsp:param name="chartId" value="myNext" />
												<jsp:param name="chartWidth" value="800" />
												<jsp:param name="chartHeight" value="300" />
												<jsp:param name="debugMode" value="false" />
											</jsp:include></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!--/row-->
		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<!-- basic scripts -->
	<script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.resize.min.js"></script>
	<!-- ace scripts -->
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript" src="static/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->

	<script type="text/javascript">

		$(top.hangge());
		//判断机构树是否打开的标志
		var is_dep_tree_open=false;
		$(function() {
			//日期
			$("#queryWeekDate").datepicker({
				autoclose: true,
		        format: "yyyy-mm-dd",
		        todayHighlight: true
		    });
			queryMonthCharts();
			querhWeekCharts();
		});
		
	</script>
	<script type="text/javascript" >
		//展现机构树
		function showDepTree(currUdepId,resultInputDepId,resultInputDepName){
			if(is_dep_tree_open){
				return;
			}
			top.jzts();
			var selectedId = $("#"+resultInputDepId).val();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="机构";
			diag.URL = '<%=basePath%>department/showDepartmentTree.do?departmentId='+currUdepId+'&selectedId='+selectedId;
			diag.Width = 450;
			diag.Height = 370;
			diag.CancelEvent = function(){ //关闭事件
				var selectedId = diag.innerFrame.contentWindow.document.getElementById("selectedId").value;
				var selectedName = diag.innerFrame.contentWindow.document.getElementById("selectedName").value;
				if (selectedId != null && selectedId.length > 0){
					$("#"+resultInputDepId).val(selectedId);
				}
				if (selectedName != null && selectedName.length > 0){
					$("#"+resultInputDepName).val(selectedName);
				}
				diag.close();
				is_dep_tree_open = false;
			};
			diag.show();	
			is_dep_tree_open = true;
		}
		
		//查询当月数据
		function queryMonthCharts(){
			var params = {
							departmentId:$("#departmentId").val(), //查询的机构的ID
							month:$("#month").val()                //查询的月份
			};
			
			$.ajax({
		           type: "POST",
		           dataType:"json",
		           url: "<%=basePath%>fusionCharts/queryMonthCharts.do",
		           data: params,
				   cache: false,
		           success: function(data){
		        	   			if(data.result=="success"){
		        	   				var myChart = new FusionCharts("static/FusionCharts/Line.swf", "monthChart", "800", "300", "0", "1" );  
					               	myChart.setDataXML(data.monthChartsData);        
					               	myChart.render("divMonthChart");
		        	   			}else{
		        	   				alert("系统异常，请联系管理员");
		        	   			}
		                   },
		           error: function(){
				        	   alert("系统异常，请联系管理员");
				           }
		    });
		}
		
		//查询一周数据
		function querhWeekCharts() {
			//查询机构名称
			var queryDepartmentName = $("#departmentNameWeek").val();
			//查询机构ID
			var depId = $("#departmentIdWeek").val();
			//查询日期
			var queryDate = $("#queryWeekDate").val();
			$.ajax({
				type: "POST",
				url: '<%=basePath%>fusionCharts/querhWeekCharts.do?queryDepartmentName='+encodeURIComponent(queryDepartmentName)+"&queryDate="+queryDate+"&depId="+depId,
				dataType:'json',
				cache: false,
				success: function(data){
			  		var myChart = new FusionCharts("static/FusionCharts/Column3D.swf", "myNext", "800", "300", "0", "1" );  
	               	myChart.setDataXML(data.result);        
	               	myChart.render("weekCharts");
				}
			});
		}
		
	</script>
</body>
</html>
