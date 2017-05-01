
<%@ page language="java"  pageEncoding="UTF-8"%>

<html>
<head>
<title>time</title>

</head>
<body onload = "showTimer()">

<div style="color:red;font-size:20pt;text-align:center"><font color="red"><b>非法操作！系统将在5秒中内跳转到登录页面</b></font></div><br>
<div id ="timer" style="color:#999;font-size:20pt;text-align:center"></div>
</body>
</html>
<script>
var i=6;
var t;
function showTimer(){
 if(i==0){//如果秒数为0的话,清除t,防止一直调用函数,对于反应慢的机器可能实现不了跳转到的效果，所以要清除掉 setInterval()
  parent.location.href="${pageContext.request.contextPath }/system/elecMenuAction_logouterror.do";
  window.clearInterval(t);

  }else{
  i = i - 1 ;
  // 秒数减少并插入 timer 层中
  document.getElementById("timer").innerHTML= i;
  }
}
// 每隔一秒钟调用一次函数 showTimer()
t = window.setInterval(showTimer,1000);
</script>
