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
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/myjs/region.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/mine.css" />
		<script type="text/javascript">
			$(function() {
				//单选框
				$(".chzn-select").chosen(); 
				$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			});
			$(top.hangge());
			var local = "";
			$(document).ready(function(){
				local = '<%=basePath%>';
				//编辑时，机构类型不能修改
				if($("#id").val() != 0){
					$('#departmentType').prop('disabled', true).trigger("liszt:updated");
				}
				
			});
			
			//保存
			function save(){
				if($("#departmentName").val()=="" || $("#departmentName").val() == "此机构名称已存在！"){
					
					$("#departmentName").tips({
						side:3,
			            msg:'请输入机构名称',
			            bg:'#FF0000',
			            time:5
			        });
					
					$("#departmentName").focus();
					$("#departmentName").val('');
					$("#departmentName").css("background-color","white");
					return false;
				}else{
					$("#departmentName").val(jQuery.trim($('#departmentName').val()));
				}
				
				if($("#departmentType").val()==""){
					
					$("#departmentType").tips({
						side:3,
			            msg:'请选择机构类型',
			            bg:'#FF0000',
			            time:2
			        });
					
					$("#departmentType").focus();
					return false;
				}
				
				if($("#merchantNum").val()=="") {
					$("#merchantNum").tips({
						side:3,
			            msg:'请输入商户号',
			            bg:'#FF0000',
			            time:3
			        });
					$("#merchantNum").focus();
					return false;
				}
						
				if($("#concator").val()==""){
					$("#concator").tips({
						side:3,
			            msg:'请输入联系人姓名',
			            bg:'#FF0000',
			            time:3
			        });
					$("#concator").focus();
					return false;
				}
				
				var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
				if($("#phone").val()==""){
					
					$("#phone").tips({
						side:3,
			            msg:'请输入手机号',
			            bg:'#FF0000',
			            time:3
			        });
					$("#phone").focus();
					return false;
				}else if($("#phone").val().length != 11 && !myreg.test($("#phone").val())){
					$("#phone").tips({
						side:3,
			            msg:'手机号格式不正确',
			            bg:'#FF0000',
			            time:3
			        });
					$("#phone").focus();
					return false;
				}
				
				//验证省市区
				if(checkRegion('province','city','area')) {
					if($("#address").val()==""){
						
						$("#address").tips({
							side:3,
				            msg:'请输入详细地址',
				            bg:'#FF0000',
				            time:3
				        });
						$("#address").focus();
						return false;
					}
					$('#departmentType').prop('disabled', false).trigger("liszt:updated");
					$("#departmentForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
			}
			
			//判断用户名是否存在
			function hasDepartment(key){
				var keyValue = $.trim($("#"+key).val());
				var msg = "";
				var msgKey = "";
				if(keyValue == "") {
					return;
				}
				if("departmentName" == key) {
					msgKey = "departmentName";
					key = "department_name";
					msg = "机构名称已存在";
				} else if("merchantNum" == key) {
					msgKey = "merchantNum";
					key = "merchant_num";
					msg = "商户号已存在";
				} else if("merchantName" == key) {
					msgKey = "merchantName";
					key = "merchant_name";
					msg = "商户名称已存在";
				}
				var id = $("#id").val();
				$.ajax({
					type: "POST",
					url: '<%=basePath%>department/hasDepartment.do',
			    	data: {
			    		id : id,
			    		key : key,
			    		keyValue:keyValue,
		    			tm:new Date().getTime()
	    			},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" != data.result){
							$("#"+msgKey).tips({
								side:3,
					            msg:msg,
					            bg:'#FF0000',
					            time:3
					        });
							setTimeout("$('#"+msgKey+"').val('')",2000);
						}
					}
				});
			}
		</script>
	</head>
	<body>
		<form action="department/${msg }.do" name="departmentForm" id="departmentForm" method="post">
			<input type="hidden" name="id" id="id" value="${department.id }"/>
			<input type="hidden" name="parentId" id="parentId" value="${pd.parentId }"/>
			<div id="zhongxin">
				<table>
					<tr>
						<td>机构名称：<span style="color:red;">*</span>
							<input type="text" name="departmentName" id="departmentName" 
								value="${department.departmentName }" maxlength="50" placeholder="这里输入机构名称" 
								title="机构名称" onblur="hasDepartment('departmentName')"/>
						</td>
					</tr>
					<tr>
						<td>机构类型：<span style="color:red;">*</span>
							<select class="chzn-select" name="departmentType" id="departmentType" data-placeholder="请选择机构类型" style="vertical-align:top;">
								<option value=""></option>
								<c:forEach items="${departmentTypeList}" var="departmentType">
									<option value="${departmentType.value }" <c:if test="${department.departmentType == departmentType.value }">selected</c:if>>${departmentType.name }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							商<span class="sp_space_simsun">&nbsp;</span>户<span class="sp_space_simsun">&nbsp;</span>号：<span style="color:red;">*</span>
							<input type="text" name="merchantNum" id="merchantNum"  value="${department.merchantNum }" 
								maxlength="20" placeholder="输入商户号" title="商户号" onblur="hasDepartment('merchantNum')"/>
						</td>
					</tr>
					<tr>
						<td>
							商户名称：<span style="color:red;">*</span>
							<input type="text" name="merchantName" id="merchantName" value="${department.merchantName }"  
								maxlength="50" placeholder="输入商户名称" title="商户名称" onblur="hasDepartment('merchantName')"/>
						</td>
					</tr>
					<tr>
						<td>
							联<span class="sp_space_simsun">&nbsp;</span>系<span class="sp_space_simsun">&nbsp;</span>人：<span style="color:red;">*</span>
							<input type="text" name="concator" id="concator"  value="${department.concator }"  
								maxlength="20" placeholder="这里输入联系人姓名" title="联系人姓名"/>
						</td>
					</tr>
					<tr>
						<td>
							联系电话：<span style="color:red;">*</span>
							<input type="number" name="phone" id="phone"  value="${department.phone }"  
								maxlength="30" placeholder="这里输入手机号" title="手机号"/>
						</td>
					</tr>
					<tr>
						<td>
							机构地址：<span style="color:red;">*</span>
							<select class="chzn-select" name="province" id="province" 
								data-placeholder="请选择省份" style="vertical-align:top;"
								onchange="changeRegion('province','city','area','province')">
								<option value=""></option>
								<c:if test="${not empty provinceList}">
									<c:forEach items="${provinceList}" var="province">
										<option value="${province.regionId }" <c:if test="${department.province == province.regionId }">selected</c:if>>${province.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
							</br>
							<span style="visibility:hidden">机构地址：<span style="color:red;">*</span></span> 
							<select class="chzn-select" name="city" id="city" data-placeholder="请选择城市" 
								style="vertical-align:top;" onchange="changeRegion('city','area','','city')">
								<option value=""></option>
								<c:if test="${not empty cityList}">
									<c:forEach items="${cityList}" var="city">
										<option value="${city.regionId }" <c:if test="${department.city == city.regionId }">selected</c:if>>${city.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
							</br>
							<span style="visibility:hidden">机构地址：<span style="color:red;">*</span></span> 
							<select class="chzn-select" name="area" id="area" data-placeholder="请选择区域" style="vertical-align:top;">
								<option value=""></option>
								<c:if test="${not empty areaList}">
									<c:forEach items="${areaList}" var="area">
										<option value="${area.regionId }" <c:if test="${department.area == area.regionId }">selected</c:if>>${area.regionName }</option>
									</c:forEach>
								</c:if>
							</select>
							</br>
							<span style="visibility:hidden">机构地址：<span style="color:red;">*</span></span> 
							<input type="text" name="address" id="address"  
								value="${department.address }" maxlength="200" 
								placeholder="这里输入详细地址" title="详细地址" />
						</td>
					</tr>
					<tr>
						<td>
							备<span class="sp_space_simsun">&nbsp;&nbsp;&nbsp;&nbsp;</span>注：<span class="sp_space_simsun">&nbsp;</span>
							<textarea name="remark" id="remark" maxlength="200" 
								placeholder="这里输入备注" title="备注" >${department.remark }</textarea>
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