<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="/common/common.jsp"%>
</head>

<body>
	<div class="error-container">
		<div class="well">
			<h1 class="blue" style= "text-align:center">
				支付失败！
			</h1>
			<hr />
			<h3 class="lighter smaller" id="content" style= "text-align:center">${errorCode}</h3>
			<h3 class="lighter smaller" id="content" style= "text-align:center">${errorInfo}</h3>
			
			<div>
		
				<div class="space"></div>
				
				<h4 class="smaller">请尝试以下操作:</h4>
				<ul class="unstyled spaced inline bigger-110">
					<li><i class="icon-hand-right blue"></i> 检查账号是不是有误</li>
					<li><i class="icon-hand-right blue"></i> 检查密码是不是有误</li>
					<li><i class="icon-hand-right blue"></i> 请联系相关管理员</li>
				</ul>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>