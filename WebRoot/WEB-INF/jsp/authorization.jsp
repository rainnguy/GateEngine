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
			footer{height:35px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
		</style>
		<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
		<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<link rel="stylesheet" href="static/css/mine.css" />
	</head>
<body>
	<div id="zhongxin">
		<input type="hidden" name="id" id="id" value="${roleId }" />
		<table>
			<tr>
				<td>
					角色名称：<span style="color:red;">*</span>
					<input type="text" name="name" id="name" maxLength="20" value="${role.name}" onblur="checkRoleName();"/>
				</td>
			</tr>
			<tr>
				<td>
					角色级别：<span style="color:red;">*</span>
					<select name="level" id="level" data-placeholder="角色级别">
						<option value="1" <c:if test="${role.level!=2 && role.level != 3}">selected</c:if>>系统级用户</option>
						<option value="2" <c:if test="${role.level==2}">selected</c:if>>公司级用户</option>
						<option value="3" <c:if test="${role.level==3}">selected</c:if>>站级用户</option>
				  	</select>
				</td>
			</tr>
			<tr>
				<td>
					备<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;</span>注：
					<input type="text" name="remark" maxLength="100" id="remark" value="${role.remark}" />
				</td>
			</tr>
		</table>	
		权限设置
		<ul id="tree" class="ztree" style="overflow:auto;height:208px;"></ul>
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
					check: { //为节点添加checkbox
						enable  : true,
						chkStyle: "checkbox"
					},
					callback:{
						beforeCheck: onCusTreeNodeCheckEvent //树节点勾选事件
					}
			};
			var zn = '${zTreeNodes}';
			var zTreeData = eval(zn);
			$.fn.zTree.init($("#tree"), setting, zTreeData);
		});
		//节点被勾选响应事件(业务权限树)
		function onCusTreeNodeCheckEvent(treeId, treeNode) {
		    var zTree = $.fn.zTree.getZTreeObj("tree");
		    //倒数第二层，即叶子节点的父节点时
		    if(treeNode.resLevel == 2)
		    {
		    	// 叶子节点的父节点选中时，叶子节点不选中
		    	zTree.setting.check.chkboxType = { "Y" : "p", "N" : "ps" };
		    }
		    else
		    {
		    	// 非叶子节点父节点选中时，其子节点全部选中
		    	zTree.setting.check.chkboxType = { "Y" : "ps", "N" : "ps" };
		    }
		};
	</script>
	<script type="text/javascript">
		//验证角色名称是否存在
		function checkRoleName() {
			var id = $("#id").val();
			var roleName = $.trim($("#name").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>role/hasRoleName.do',
		    	data: {
		    		id : id,
		    		roleName : roleName,
	    			tm:new Date().getTime()
    			},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						$("#name").tips({
							side:3,
				            msg:"角色名称已存在！",
				            bg:'#FF0000',
				            time:1.5
				        });
						$('#name').val('');
						$("#name").focus();
					}
				}
			});
		}
	 	//保存角色信息
	 	function save(){
			var name = $.trim($("#name").val());
			if(name == '') {
				$("#name").tips({
					side:3,
		            msg:"请输入角色名称！",
		            bg:'#FF0000',
		            time:1.5
		        });
				$("#name").focus();
				return;
			}
		 	var nodes = $.fn.zTree.getZTreeObj("tree").getCheckedNodes(true);
		 	if(nodes.length == 0) {
		 		alert("请给角色赋予权限");
		 		return;
		 	}
		 	var ids = "";
		 	$.each(nodes, function(i, node){
				ids += nodes[i].id+",";
			});
			var roleId = "${roleId}";
			var name = $("#name").val();
			var remark = $("#remark").val();
			var level = $("#level").val();
			var url = "<%=basePath%>role/auth/save.do";
			var postData;
			postData = {"id":roleId,"menuIds":ids,"name":name,"remark":remark,"level":level};
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$.post(url,postData,function(data){
				if(arguments[1]=="success"){
					alert("保存成功！");
					top.Dialog.close();
					
				}
			});
			 
		}
	
	</script>
	<footer>
	<div style="width: 100%;" class="center">
		<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
		<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
	</div>
	</footer>
</body>
</html>