	<title>${pd.SYSNAME}</title>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
	<!-- basic styles -->
	<link rel="stylesheet" href="<%=basePath%>/static/css/bootstrap.min.css"  />
	<!-- page specific plugin styles -->
	<link rel="stylesheet" href="<%=basePath%>static/css/chosen.css" />
	<link rel="stylesheet" href="<%=basePath%>static/css/ace.min.css" />
	<link rel="stylesheet" href="<%=basePath%>static/css/font-awesome.min.css" />

	<script type="text/javascript" src="<%=basePath%>static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/fuelux.wizard.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.validate.min.js"></script>
	<!--  -->
	<script type="text/javascript" src="<%=basePath%>static/js/bootbox.min.js"></script>
	<!--  -->
	<script type="text/javascript" src="<%=basePath%>static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/ace-elements.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/js/ace.min.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="<%=basePath%>static/js/jquery.tips.js"></script>
		
	