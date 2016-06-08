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
		<style type="text/css">
		/* 对账失败用的样式  */
		.notMatcthed{
			background-color:red;
			color:#ccc;
		}
		/* 奇数行对账成功用正常的样式  */
		.normal{
			background-color: #F9F9F9;
		}
		</style>
	</head> 
  <body>
    <div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
  			<div class="row-fluid">
				<div class="row-fluid">
					<form action="account/listTransAccount.do" method="post" name="transAccountForm" id="transAccountForm">
						<input type="hidden" name="parentId" id="parentId" value="${pd.parentId }"/>
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="depName" value="${pd.depName }" placeholder="公司名称"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="acqName" value="${pd.acqName }" placeholder="商户名称"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="mid" value="${pd.mid }" placeholder="商户号" style="width:156px;"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="tid" value="${pd.tid }" placeholder="终端号" style="width:156px;"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top">
									<span class="input-icon">
										<select class="chzn-select" name="txnId" id="txnId" data-placeholder="交易类型" style="vertical-align:top; width:190px;">
											<option value=""></option>
											<option value="0" <c:if test="${pd.txnId==0}">selected</c:if>>全部</option>
											<c:forEach items="${txnList}" var="txn">
												<option value="${txn.txnId }" <c:if test="${pd.txnId==txn.txnId}">selected</c:if>>${txn.txnName}</option>
											</c:forEach>
									  	</select>
									</span>
								</td>
							</tr>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="pan" value="${pd.pan }" placeholder="卡号"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top">
									<span class="input-icon">
										<select class="chzn-select" name="bankNo" id="bankNo" data-placeholder="发卡行" style="vertical-align:top; width:152px;">
											<option value=""></option>
											<option value="">全部</option>
											<c:forEach items="${bankList}" var="bank">
												<option value="${bank.value }" <c:if test="${pd.bankNo==bank.value}">selected</c:if>>${bank.name }</option>
											</c:forEach>
									  	</select>
									</span>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="transStartDate" id="transStartDate" value="${pd.transStartDate }" placeholder="开始时间" readonly>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="transEndDate" id="transEndDate" value="${pd.transEndDate }" placeholder="结束时间" readonly>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<td style="vertical-align:top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
									<c:if test="${pd.showExport}">
										<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a>
									</c:if>
									<a class="btn btn-mini btn-light" onclick="importExcel('account/readTxt.do');" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload"></i></a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->
						<!-- <table id="table_report" class="table table-striped table-bordered table-hover"> -->
						<table id="table_report" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th ></th>
									<th colspan="7" style="text-align:center;">前置端</th>
									<th colspan="7" style="text-align:center;">银行端</th>
								</tr>
								<tr>
									<th >序号</th>
									<th>流水号</th>
									<th>商户名称</th>
									<th>卡号</th>
									<th>终端号</th>
									<th>交易金额</th>
									<th>交易时间</th>
									<th>交易类型</th>
									
									<th>流水号</th>
									<th>商户名称</th>
									<th>卡号</th>
									<th>终端号</th>
									<th>交易金额</th>
									<th>交易时间</th>
									<th>交易类型</th>
								</tr>
							</thead>
							<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty transAccountList}">
										<c:forEach items="${transAccountList}" var="tr" varStatus="vs">
											<tr class="${tr.styleClass}">
												<td class='center' style="width: 30px;" class="${tr.styleClass}">${vs.index+1}</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.systrace }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.acpName }</td>
												<td style="width:50px;" class="${tr.styleClass}">${tr.pan }</td>
												<td style="width:50px;" class="${tr.styleClass}">${tr.tid }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.txnAmt }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.tranDate }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.txnName }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.systraceB }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.acpNameB }</td>
												<td style="width:50px;" class="${tr.styleClass}">${tr.panB }</td>
												<td style="width:50px;" class="${tr.styleClass}">${tr.tidB }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.txnAmtB }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.tranDateB }</td>
												<td style="width:60px;" class="${tr.styleClass}">${tr.txnNameB }</td>
											</tr>
										</c:forEach>
								   </c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="15" class="center">没有相关数据</td>
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
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	
	<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script><!-- 日期框 -->
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript">
		$(top.hangge());
		
		$(function() {
			//时间框
			$("#transStartDate").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#transStartDate").datetimepicker("setEndDate", $("#transEndDate").val());
		    });
		  	//开始时间后的清空开始时间事件
			$("#transStartDate").next().find("i").on("click",function(){
				$("#transStartDate").val("");
			});
			$("#transEndDate").datetimepicker({
				language: 'zh-CN',
				autoclose: true,
		        format: "yyyy-mm-dd hh:ii",
		        todayHighlight: true
		    }).on("click",function(ev){
		        $("#transEndDate").datetimepicker("setStartDate", $("#transStartDate").val());
		    });
			//结束时间后的清空结束时间事件
			$("#transEndDate").next().find("i").on("click",function(){
				$("#transEndDate").val("");
			});
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true});
		});
		
		//检索
		function search(){
			top.jzts();
			$("#transAccountForm").submit();
		}
		
		//导出excel
		function toExcel(){
			//机构名称
			var depName = $("input[name='depName']").val();
			//商户名称
			var acqName = $("input[name='acqName']").val();
			//商户号
			var mid = $("input[name='mid']").val();
			//终端号
			var tid = $("input[name='tid']").val();
			//交易类型
			var txnId = $("#txnId").val();
			//卡号
			var pan = $("input[name='pan']").val();
			//发卡行
			var bankNo = $("#bankNo").val();
			//开始时间
			var transStartDate = $("#transStartDate").val();
			//结束时间
			var transEndDate = $("#transEndDate").val();
			//导出
			window.location.href='<%=basePath%>account/excel.do?'
					+'depName='+depName
					+'&acqName='+acqName
					+'&mid='+mid
					+'&tid='+tid
					+'&txnId='+txnId
					+'&pan='+pan
					+'&bankNo='+bankNo
					+'&transStartDate='+transStartDate
					+'&transEndDate='+transEndDate;
		}
		
		//打开上传excel页面
		function importExcel(url){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="对账文件 导入到数据库";
			 diag.URL = '<%=basePath%>user/goUploadExcel.do?url='+url+'&uploadFileType=txt';
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
