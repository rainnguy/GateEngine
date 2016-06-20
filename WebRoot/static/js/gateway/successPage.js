var i = 10;

function shownum() { 
	i = i - 1;
	document.getElementById("content").innerHTML = i + "秒后自动跳转到首页";
	setTimeout('shownum()', 1000); 
}