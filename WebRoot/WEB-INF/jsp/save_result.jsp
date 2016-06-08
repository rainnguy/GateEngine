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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存结果</title>
<base href="<%=basePath%>">
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
</head>
<body>
	<div id="zhongxin"></div>
	<script type="text/javascript">
		var msg = "${msg}";
		if(msg=="success" || msg=="" || msg=="OK"){
			alert("操作成功！");
			document.getElementById('zhongxin').style.display = 'none';
			top.Dialog.close();
		}else{
			alert("操作失败！");
		}
	</script>
</body>
</html>