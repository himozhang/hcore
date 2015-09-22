<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<%
String msg = "";
String contextPath = request.getContextPath();
Exception e = (Exception)request.getAttribute("ex");
if(e != null){
	java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	e.printStackTrace(new java.io.PrintStream(baos));
	msg = new String(baos.toByteArray());
%>


 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta charset="UTF-8">
  <meta name="Keywords" content="该页面不存在">
  <meta name="Description" content="">
  <title>RiCore</title>
  <style type="text/css">
  	body{
  		background: #f0f0f0;
  		color: #9f9f9f;
  		font-family:"Microsoft YaHei",Arial,Helvetica,sans-serif,"宋体";
  	}
  	.body-img{
  		margin-top: 10%;
  	}
  	.body-text{
  		font-size: 48px;
  		color: 9f9f9f;
  		padding: 20px 0px;
  	}
  	.body-btn{
  		background:none;
		color:#00a2c9;
		border:1px #33b5d4 solid;
		display:inline-block;
		*display:inline;
		zoom:1;
		text-align: center;
		font-size: 26px;
		cursor: pointer;
		padding: 5px 20px;
		font-weight: bold;
		margin-top: 10px;
  	}
  	.body-btn:HOVER {
		transition: all .5s ease;
		-webkit-transition: all .5s ease;
		-moz-transition: all .5s ease;
		-ms-transition: all .5s ease;
		-o-transition: all .5s ease;
		background:#33b5d4;
		color: #FFFFFF;
	}
}
  </style>
 </head>
 <body class="navbar-fixed breadcrumbs-fixed">
  	<div align="center">
  		<img src="<%=contextPath%>/static/images/500.png" class="body-img"/>
  		<div class="body-text">对不起...出错了...</div>
  		<div><input type="button" class="body-btn" value="返回首页 >>" onclick="gohome()" /> </div>
  	</div>
 </body>
 
 
<%
}else{
%>


 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta charset="UTF-8">
  <meta name="Keywords" content="该页面不存在,页面不见了,找不到了">
  <meta name="Description" content="">
  <title>Ricore</title>
  <style type="text/css">
  	body{
  		background: #f0f0f0;
  		color: #9f9f9f;
  		font-family:"Microsoft YaHei",Arial,Helvetica,sans-serif,"宋体";
  	}
  	.body-img{
  		margin-top: 10%;
  	}
  	.body-text{
  		font-size: 48px;
  		color: 9f9f9f;
  		padding: 20px 0px;
  	}
  	.body-btn{
  		background:none;
		color:#00a2c9;
		border:1px #33b5d4 solid;
		display:inline-block;
		*display:inline;
		zoom:1;
		text-align: center;
		font-size: 26px;
		cursor: pointer;
		padding: 5px 20px;
		font-weight: bold;
		margin-top: 10px;
  	}
  	.body-btn:HOVER {
		transition: all .5s ease;
		-webkit-transition: all .5s ease;
		-moz-transition: all .5s ease;
		-ms-transition: all .5s ease;
		-o-transition: all .5s ease;
		background:#33b5d4;
		color: #FFFFFF;
	}
}
  </style>
 </head>
 <body class="navbar-fixed breadcrumbs-fixed">
  	<div align="center">
  		<img src="<%=contextPath%>/static/images/500.png" class="body-img"/>
  		<div class="body-text">系统错误，请联系管理员...</div>
  		<div><input type="button" class="body-btn" value="我要回首页 >>" onclick="gohome()" /> </div>
  	</div>
<%
}
%>

<script>
function gohome(){
	var w = window;
	
	w.location='<%=contextPath %>/';
}
</script>

</body>

</html>