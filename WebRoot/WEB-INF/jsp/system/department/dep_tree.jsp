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
		<meta charset="utf-8" />
		<title></title>
		
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<style type="text/css">
			footer{height:50px;position:fixed;left:0px;width:100%;text-align: center;margin-top:13px;}
		</style>
		<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
		<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
	</head>
<body>
	<div id="zhongxin">
		<input type="hidden" name="selectedId" id="selectedId" />
		<input type="hidden" name="selectedName" id="selectedName" />
		请选择机构：
		<ul id="tree" class="ztree" style="overflow:auto;height:285px;"></ul>
	</div>
	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
	<script type="text/javascript">
		$(top.hangge());
		var zTree;
		
		$(document).ready(function(){
			var setting = {
					data: {
						simpleData: {
							enable: true
						}
					},
					check: { //为节点添加radio
						enable  : true,
						chkStyle: "radio",
						radioType: "all"
					}
			};
			var zn = '${zTreeNodes}';
			var zTreeData = eval(zn);
			$.fn.zTree.init($("#tree"), setting, zTreeData);
		});
		
	
		 function save(){
			 var node = $.fn.zTree.getZTreeObj("tree").getCheckedNodes(true);
			 if(node != null && node.length > 0){
			 	$("#selectedId").val(node[0].id);
			 	$("#selectedName").val(node[0].name);
		 	 }else{
		 		$("#selectedId").val("");
				$("#selectedName").val(""); 
		 	 }
			 top.Dialog.close();
		 }
		 
		 function cancel(){
			 $("#selectedId").val("");
			 $("#selectedName").val("");
			 top.Dialog.close();
		 }
	
	</script>
	<footer>
	<div style="width: 100%;" class="center">
		<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
		<a class="btn btn-mini btn-danger" onclick="cancel();">取消</a>
	</div>
	</footer>
</body>
</html>