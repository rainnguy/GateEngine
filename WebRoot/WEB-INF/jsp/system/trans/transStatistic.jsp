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
					<form action="statistic/transStatistic.do" method="post" name="transStatisticForm" id="transStatistic">
						<input type="hidden" name="parentId" id="parentId" value="${pd.parentId }"/>
						<input type="hidden" name="id" id="id" value="${pd.id }"/>
						<!-- 查询条件table -->
						<table>
							<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="depName" value="${pd.depName }" placeholder="公司名称" style="width:156px;"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="acqName" value="${pd.acqName }" placeholder="商户名称" style="width:156px;"/>
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
									<select class="chzn-select" name="txnId" id="txnId" data-placeholder="交易类型" style="vertical-align:top; width:188px;">
									<option value=""></option>
									<option value="0" <c:if test="${pd.txnId==0}">selected</c:if>>全部</option>
									<c:forEach items="${txnList}" var="txn">
										<option value="${txn.txnId }" <c:if test="${pd.txnId==txn.txnId}">selected</c:if>>${txn.txnName}</option>
									</c:forEach>
								  	</select>
								</td>
								<td></td>
						</tr>
						<tr>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="batchNo" value="${pd.batchNo}" placeholder="批次号" style="width:156px;"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="tranStartDate" id="tranStartDate" value="${pd.tranStartDate }" placeholder="开始时间" readonly title="交易起始时间"/>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<td>
									<div class="input-append date form_datetime">
									    <input style="width:120px;" size="16" type="text" name="tranEndDate" id="tranEndDate" value="${pd.tranEndDate }" placeholder="结束时间" readonly title="交易结束时间"/>
									    <span class="add-on"><i class="icon-remove"></i></span>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
								</td>
								<td>
									<span class="input-icon">
										<input autocomplete="off" id="nav-search-input" type="text" name="pan" value="${pd.pan }" placeholder="卡号" style="width:156px;"/>
										<i id="nav-search-icon" class="icon-search"></i>
									</span>
								</td>
								<td style="vertical-align:top">
									<select class="chzn-select" name="bankNo" id="bankNo" data-placeholder="发卡行" style="vertical-align:top; width:188px;">
									<option value=""></option>
									<option value="">全部</option>
									<c:forEach items="${bankList}" var="bank">
										<option value="${bank.value }" <c:if test="${pd.bankNo==bank.value}">selected</c:if>>${bank.name }</option>
									</c:forEach>
								  	</select>
								</td>
								<td style="vertical-align:top;">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
									<c:if test="${pd.showExport}">
										<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a>
									</c:if>
								</td>
							</tr>
						</table>
						<!-- 显示机构导航的table -->
						<table>
							<tr>
								<c:if test="${pd.showAddForPage}">
									<c:choose>
										<c:when test="${not empty listDepForNavigation}">
											<td style="vertical-align:top;"><a href="<%=basePath%>/statistic/transStatistic.do" class="btn btn-mini btn-purple" title="查看">顶级<i class="icon-arrow-right  icon-on-right"></i></a></td>
											<c:forEach items="${listDepForNavigation}" var="var" varStatus="vsd">
												<td style="vertical-align:top;"><a href="<%=basePath%>/statistic/transStatistic.do?parentId=${var.id }&id=${var.id }" class="btn btn-mini btn-purple" title="查看">${var.departmentName }<i class="icon-arrow-right  icon-on-right"></i></a></td>
											</c:forEach>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</c:if>
							</tr>
						</table>
						<!-- 检索  -->
						<table id="table_report" class="table table-striped table-bordered table-hover" style="width:1180px;max-width:1180px;">
							<thead>
								<tr>
									<th style="width:8%;">序号</th>
									<th style="width:20%;">机构</th>
									<th style="width:20%;">商户号</th>
									<th style="width:22%;">商户名称</th>
									<th style="width:15%;">交易笔数</th>
									<th style="width:15%;">交易金额</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty transStatisticList}">
										<c:forEach items="${transStatisticList}" var="transStatistic" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<c:choose>
													<c:when test="${transStatistic.departmentType==3 || pd.showNav==0}">
														<td>${transStatistic.departmentName }</td>
													</c:when>
													<c:otherwise>
														<td><a href="<%=basePath%>/statistic/transStatistic.do?parentId=${transStatistic.departmentId }&id=${transStatistic.departmentId }" title="查看下级"><i class="icon-arrow-right  icon-on-right"></i>&nbsp;${transStatistic.departmentName }</td>
													</c:otherwise>
												</c:choose>
												<td>${transStatistic.mid}</td>
												<td>${transStatistic.acqName}</td>
												<td>${transStatistic.transNum}</td>
												<td>${transStatistic.transAggregate}</td>
											</tr>
										</c:forEach>
								   	</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="5" class="center">没有相关数据</td>
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
	<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script><!-- 日期框 -->
	
	
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	<script type="text/javascript">
	$(top.hangge());
	$(function(){
		//时间框
		$("#tranStartDate").datetimepicker({
			language: 'zh-CN',
			autoclose: true,
	        format: "yyyy-mm-dd hh:ii",
	        todayHighlight: true
	    }).on("click",function(ev){
	        $("#tranStartDate").datetimepicker("setEndDate", $("#tranEndDate").val());
	    });
		$("#tranEndDate").datetimepicker({
			language: 'zh-CN',
			autoclose: true,
	        format: "yyyy-mm-dd hh:ii",
	        todayHighlight: true
	    }).on("click",function(ev){
	        $("#tranEndDate").datetimepicker("setStartDate", $("#tranStartDate").val());
	    });
		//时间清除实现
		$("#tranStartDate").next().find("i").on("click",function(){
			$("#tranStartDate").val("");
		});
		$("#tranEndDate").next().find("i").on("click",function(){
			$("#tranEndDate").val("");
		});
		
		//下拉框
		$(".chzn-select").chosen(); 
		$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
	});
	
	//检索
	function search(){
		top.jzts();
		$("#transStatistic").submit();
	}
	
	//导出excel
	function toExcel(){
		//获取查询条件
		var parentId=$("#parentId").val();
		var id=$("#id").val();
		var depName = $('input[name="depName"]').val();
		var acqName = $('input[name="acqName"]').val();
		var mid = $('input[name="mid"]').val();
		var tid = $('input[name="tid"]').val();
		var txnId=$("#txnId").val();
		var batchNo = $('input[name="batchNo"]').val();
		var tranStartDate = $("#tranStartDate").val();
		var tranEndDate = $("#tranEndDate").val();
		var pan = $('input[name="pan"]').val();
		var bankNo = $("#bankNo").val();
		//请求导出
		window.location.href='<%=basePath%>statistic/excel.do?parentId='+parentId+
															'&id='+id+
															'&depName='+depName+
															'&acqName='+acqName+
															'&mid='+mid+
															'&tid='+tid+
															'&txnId='+txnId+
															'&batchNo='+batchNo+
															'&tranStartDate='+tranStartDate+
															'&tranEndDate='+tranEndDate+
															'&pan='+pan+
															'&bankNo='+bankNo;
	}
	</script>
  </body>
</html>
