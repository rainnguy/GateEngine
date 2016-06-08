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
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript">
			$(top.hangge());
			var local = "";
			$(document).ready(function(){
				local = '<%=basePath%>';
				$('.date-picker').datepicker({
					language: 'zh-CN',
					autoclose: true,
			        format: "yyyy-mm-dd",
			        todayHighlight: true
			    });
			});
			
			//保存
			function save(){
				if($("#posNumber").val()=="" || $("#posNumber").val() == "此终端号已存在！"){
					$("#posNumber").tips({
						side:3,
			            msg:'请输入终端号',
			            bg:'#FF0000',
			            time:5
			        });
					
					$("#posNumber").focus();
					$("#posNumber").val('');
					$("#posNumber").css("background-color","white");
					return false;
				}else{
					$("#posNumber").val(jQuery.trim($('#posNumber').val()));
				}
				$("#posMachineForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
			
			//判断终端号是否存在
			function hasPOSMachine(){
				var posNumber = $.trim($("#posNumber").val());
				var id = $("#id").val();
				$.ajax({
					type: "POST",
					url: '<%=basePath%>department/hasPOSMachine.do',
			    	data: {
			    		id:id,
			    		posNumber:posNumber,
		    			tm:new Date().getTime()
	    			},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" != data.result){
							$("#posNumber").tips({
								side:3,
					            msg:"此终端号已存在！",
					            bg:'#FF0000',
					            time:3
					        });
							setTimeout("$('#posNumber').val('')",500);
						}
					}
				});
			}
		</script>
	</head>
	<body>
		<form action="department/savePosMachine.do" name="posMachineForm" id="posMachineForm" method="post">
			<input type="hidden" name="id" id="id" value="${posMachine.id }"/>
			<input type="hidden" name="departmentId" id="departmentId" value="${posMachine.departmentId }"/>
			<div id="zhongxin">
				<table>
					<tr>
						<td>终<span class="sp_space_simsun">&nbsp;&nbsp;</span>端<span class="sp_space_simsun">&nbsp;&nbsp;</span>号：<span style="color:red;">*</span>
							<input type="text" name="posNumber" id="posNumber" 
								value="${posMachine.posNumber }" maxlength="255" placeholder="这里输入终端号" 
								title="POS机终端号" onblur="hasPOSMachine()"/>
						</td>
					</tr>
					<tr>
						<td>机具产权：<span class="sp_space_simsun">&nbsp;&nbsp;</span>
							<input type="text" name="machineProperty" id="machineProperty" 
								value="${posMachine.machineProperty }" maxlength="255" placeholder="这里输入机具产权" 
								title="机具产权"/>
						</td>
					</tr>
					<tr>
						<td>机具型号：<span class="sp_space_simsun">&nbsp;&nbsp;</span>
							<input type="text" name="machineModel" id="machineModel" 
								value="${posMachine.machineModel }" maxlength="255" placeholder="这里输入机具型号" 
								title="POS机终端号"/>
						</td>
					</tr>
					<tr>
						<td>序<span class="sp_space_simsun">&nbsp;&nbsp;</span>列<span class="sp_space_simsun">&nbsp;&nbsp;</span>号：<span class="sp_space_simsun">&nbsp;&nbsp;</span>
							<input type="text" name="machineSerialNumber" id="machineSerialNumber" 
								value="${posMachine.machineSerialNumber }" maxlength="255" placeholder="这里输入序列号" 
								title="序列号"/>
						</td>
					</tr>
					<tr>
						<td>寄出日期：<span class="sp_space_simsun">&nbsp;&nbsp;</span>
							<input class="span10 date-picker" name="mailedDate" name="mailedDate" 
							value="${posMachine.mailedDate }" type="text" data-date-format="yyyy-mm-dd" 
							readonly="readonly" style="width:206px;" placeholder="寄出日期" />
						</td>
					</tr>
					<tr>
						<td>
							备<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>注：
							<textarea name="remark" id="remark" maxlength="255" 
								placeholder="这里输入备注" title="备注" >${posMachine.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</table>
			</div>
			<div id="zhongxin2" class="center" style="display:none">
				<br/><br/><br/><br/>
				<img src="static/images/jiazai.gif" /><br/>
				<h4 class="lighter block green"></h4>
			</div>
		</form>
	</body>
</html>