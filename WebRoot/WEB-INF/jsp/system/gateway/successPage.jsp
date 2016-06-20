<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@include file="/common/common.jsp"%>
		<script type="text/javascript" src="<%=basePath%>/static/js/gateway/successPage.js"></script>
		 
		<meta name="description" content="404 Error Page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="refresh" content ="10;url=<%=basePath%>gateway/defaultPage.do">
		
<body onload="shownum()">
	<div class="error-container">
		<div class="well">
			<h1 class="blue" style= "text-align:center">
				支付成功！
			</h1>
			<hr />
			<h3 class="lighter smaller" id="content" style= "text-align:center"></h3>
			
		</div>
	</div>

	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>