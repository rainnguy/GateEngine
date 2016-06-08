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
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/mine.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	$(top.hangge());
	var is_dep_tree_open=false;
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#USERNAME").attr("readonly","readonly");
			$("#USERNAME").css("color","gray");
			$("#departmentName").attr("readonly","readonly");
			$("#departmentName").css("color","gray");
		}
		if($("#fx").val()=="head"){
			$("#NUMBER").attr("readonly","readonly");
			$("#NUMBER").css("color","gray");
			$("#role_name").attr("readonly","readonly");
			$("#role_name").css("color","gray");
			$("#departmentName").removeAttr("onclick");
			$("#departmentName").removeAttr("onfocus");
		}
	});
	
	//保存
	function save(){
		//检查编号
		if($("#NUMBER").val()==""){
			
			$("#NUMBER").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUMBER").focus();
			return false;
		}else{
			$("#NUMBER").val($.trim($("#NUMBER").val()));
			var usernameReg = /^[0-9]+$/;
			if(!usernameReg.test($("#NUMBER").val())){
				$("#NUMBER").tips({
					side:3,
		            msg:'编号只能是数字',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#NUMBER").focus();
				return false;
			}
			if(hasN()){
				return false;
			}
		}
		
		//检查用户名
		if($("#USERNAME").val()=="" || $("#USERNAME").val()=="此用户名已存在!"){
			
			$("#USERNAME").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#USERNAME").focus();
			$("#USERNAME").val('');
			$("#USERNAME").css("background-color","white");
			return false;
		}else{
			$("#USERNAME").val(jQuery.trim($('#USERNAME').val()));
			var usernameReg = /^[A-Za-z0-9]+$/;
			if(!usernameReg.test($("#USERNAME").val())){
				$("#USERNAME").tips({
					side:3,
		            msg:'用户名只能是字母和数字',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#USERNAME").focus();
				return false;
			}
		}
		
		//检查姓名
		if($("#name").val()==""){
			
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		
		//检查密码
		if($("#user_id").val()=="" && $("#password").val()==""){
			
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#password").focus();
			return false;
		}
		//检查确认密码
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			
			$("#chkpwd").focus();
			return false;
		}
		
		//检查角色
		if($("#ROLE_ID").val()==""){
			
			$("#ROLE_ID").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#ROLE_ID").focus();
			return false;
		}
		
		//检查机构
		if($("#departmentId").val()==""){
			
			$("#departmentId").tips({
				side:3,
	            msg:'选择机构',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#departmentId").focus();
			return false;
		}

		//检查手机号
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}
		
		//检查邮箱
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}
	
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#USERNAME").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#USERNAME").css("background-color","#D16E6C");
					setTimeout("$('#USERNAME').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					setTimeout("$('#EMAIL').val('')",2000);
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(){
		var NUMBER = $.trim($("#NUMBER").val());
		var departmentId = $.trim($("#departmentId").val());
		var user_id = $("#user_id").val();
		var result=false;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUMBER:NUMBER,departmentId:departmentId},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				 if("success" != data.result){
					 //新增时找到user_id即为存在
					 if(user_id == "" && data.pd.user_id != ""){
						 result = true;
					 }
					 //更新时，找到与自己user_id不同的即为存在
					 if(user_id != "" && data.pd.user_id != user_id){
						 result = true;
					 }
					 if(result){
						 $("#NUMBER").tips({
								side:3,
					            msg:'编号在机构中已经存在',
					            bg:'#AE81FF',
					            time:3
					        });
						 setTimeout("$('#NUMBER').val('')",2000);
					 }
				 }
			}
		});
		return result;
	}
	
	//展现机构树
	function showDepTree(currUdepId){
		if(is_dep_tree_open){
			return;
		}
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="机构";
		diag.URL = '<%=basePath%>department/showDepartmentTree.do?departmentId='+currUdepId+'&selectedId='+$("#departmentId").val();
		diag.Width = 450;
		diag.Height = 370;
		diag.CancelEvent = function(){ //关闭事件
			var selectedId = diag.innerFrame.contentWindow.document.getElementById("selectedId").value;
			var selectedName = diag.innerFrame.contentWindow.document.getElementById("selectedName").value;
			if (selectedId != null && selectedId.length > 0){
				$("#departmentId").val(selectedId);
			}
			if (selectedName != null && selectedName.length > 0){
				$("#departmentName").val(selectedName);
			}
			diag.close();
			is_dep_tree_open = false;
		};
		diag.show();	
		is_dep_tree_open = true;
	}
	
</script>
	</head>
<body>
	<form action="user/${msg}.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="user_id" id="user_id" value="${pd.user_id}"/>
		<input type="hidden" name="pPASSWORD" id="pPASSWORD" value ="${pd.pPASSWORD}"/>
		<input type="hidden" name="fx" id="fx" value ="${fx}"/>
		<div id="zhongxin">
		<table>
			
			<tr>
				<td>编<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;</span>号：<span style="color:red;">*</span><input type="text" name="NUMBER" id="NUMBER" value="${pd.NUMBER }" maxlength="32" placeholder="输入编号" title="编号" /></td>
			</tr>
			<tr>
				<td>用<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;</span>户：<span style="color:red;">*</span><input type="text" name="USERNAME" id="USERNAME" value="${pd.USERNAME }" maxlength="32" placeholder="输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td>真实姓名：<span style="color:red;">*</span><input type="text" name="NAME" id="name"  value="${pd.NAME }"  maxlength="32" placeholder="输入姓名" title="姓名"/></td>
			</tr>
			<tr>
				<td>输入密码：<span style="color:red;">*</span><input type="password" name="PASSWORD" id="password" value="${pd.PASSWORD }"  maxlength="32" placeholder="输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td>确认密码：<span style="color:red;">*</span><input type="password" name="chkpwd" id="chkpwd"  value="${pd.PASSWORD }" maxlength="32" placeholder="确认密码" title="确认密码" /></td>
			</tr>
			
			<tr class="info">
				<c:if test="${fx != 'head'}">
				<c:if test="${pd.ROLE_ID != '1'}">
				<td>
				<span>角<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;</span>色：</span><span style="color:red;">*</span>
				<select class="chzn-select" name="ROLE_ID" id="ROLE_ID" data-placeholder="请选择角色" style="vertical-align:top;">
				<option value=""></option>
				<c:forEach items="${pd.roleList}" var="role">
					<c:if test="${role.id == pd.role_id }">
						<option value="${role.id }" selected='selected'>${role.name }</option>
					</c:if>
					<c:if test="${role.id != pd.role_id }">
						<option value="${role.id }" >${role.name }</option>
					</c:if>
				</c:forEach>
				</select>
				</td>
				</c:if>
				</c:if>
				<c:if test="${fx == 'head'}">
				<td>
					<span>角<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;</span>色：</span><span style="color:red;">*</span>
					<input name="ROLE_ID" id="ROLE_ID" value="${pd.role_id}" type="hidden"/>
					<input type="text" name="ROLE_NAME" id="role_name" value="${pd.role_name}" />
				</td>
				</c:if>
			</tr>
			<tr>
				<td>所属机构：<span style="color:red;">*</span><input type="text" name="departmentName" id="departmentName" value="${pd.departmentName}" maxlength="32" placeholder="请选择机构" title="机构"  onfocus="showDepTree('${pd.currUdepId}');" onclick="showDepTree('${pd.currUdepId}');"/>
					<input type="hidden" name="departmentId" id="departmentId" value="${pd.departmentId}"/>			
				</td>
			</tr>
			<tr>
				<td>联系电话：<span style="color:red;">*</span><input type="number" name="PHONE" id="PHONE"  value="${pd.PHONE }" maxlength="11" placeholder="输入手机号" title="手机号"/></td>
			</tr>
			<tr>
				<td>邮箱地址：<span style="color:red;">*</span><input type="email" name="EMAIL" id="EMAIL"  value="${pd.EMAIL }" maxlength="32" placeholder="输入邮箱" title="邮箱" <%-- onblur="hasE('${pd.USERNAME }')" --%>/></td>
			</tr>
			<tr>
				<td>备<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;</span>注：<span style="visibility:hidden">*</span><input type="text" name="BZ" id="BZ"value="${pd.BZ }" placeholder="输入备注" maxlength="64" title="备注"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
		});
		
		</script>
	
</body>
</html>