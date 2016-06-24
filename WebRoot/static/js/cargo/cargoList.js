var locat = (window.location + '').split('/');
$(function() {
	if ('tool' == locat[3]) {
		locat = locat[0] + '//' + locat[2];
	} else {
		locat = locat[0] + '//' + locat[2] + '/' + locat[3];
	}
	;
});

$(top.hangge());


//检索
function search(){
	top.jzts();
	$("#userForm").submit();
}

//新增
function add(){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增";
	 diag.URL = '<%=basePath%>user/goAddU.do?currUdepId='+$("#currUdepId").val();
	 diag.Width = 360;
	 diag.Height = 460;
	 
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 if('${page.currentPage}' == '0'){
				 top.jzts();
				 setTimeout("self.location=self.location",100);
			 }else{
//				 nextPage(${page.currentPage});
			 }
		}
		diag.close();
	 };
	 diag.show();
}
/*
//修改
function editUser(user_id){
	 top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="编辑";
	 diag.URL = '<%=basePath%>user/goEditU.do?user_id='+user_id+'&currUdepId='+$("#currUdepId").val();
	 diag.Width = 360;
	 diag.Height = 480;
	 diag.CancelEvent = function(){ //关闭事件
		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			nextPage(${page.currentPage});
		}
		diag.close();
	 };
	 diag.show();
}

//删除
function delUser(userId,USERNAME){
	bootbox.confirm("确定要删除["+USERNAME+"]吗?", function(result) {
		if(result) {
			top.jzts();
			var url = "<%=basePath%>user/deleteU.do?userId="+userId+"&tm="+new Date().getTime()+"&USRNAME="+USERNAME;
			$.get(url,function(data){
				nextPage(${page.currentPage});
			});
		}
	});
}

//批量操作
function makeAll(msg){
	bootbox.confirm(msg, function(result) {
		if(result) {
			var str = '';
			var USERNAMEstr = '';
			var phones = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				  if(document.getElementsByName('ids')[i].checked){
				  	if(str=='') str += document.getElementsByName('ids')[i].value;
				  	else str += ',' + document.getElementsByName('ids')[i].value;
				  	
				  	if(USERNAMEstr=='') USERNAMEstr += document.getElementsByName('ids')[i].id;
				  	else USERNAMEstr += ';' + document.getElementsByName('ids')[i].id;
				  	
				  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
				  	else phones += ';' + document.getElementsByName('ids')[i].alt;
				  }
			}
			if(str==''){
				bootbox.dialog("您没有选择任何内容!", 
					[
					  {
						"label" : "关闭",
						"class" : "btn-small btn-success",
						"callback": function() {
							//Example.show("great success");
							}
						}
					 ]
				);
				
				$("#zcheckbox").tips({
					side:3,
		            msg:'点这里全选',
		            bg:'#AE81FF',
		            time:8
		        });
				
				return;
			}else{
				if(msg == '确定要删除选中的数据吗?'){
					top.jzts();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>user/deleteAllU.do?tm='+new Date().getTime(),
				    	data: {USER_IDS:str,USERNAMES:USERNAMEstr},
						dataType:'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data){
							 $.each(data.list, function(i, list){
									nextPage(${page.currentPage});
							 });
						}
					});
				}
				
			}
		}
	});
}
*/