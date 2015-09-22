<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
 
<%
String contextPath = request.getContextPath();
%>

 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta charset="UTF-8">
  <meta name="Keywords" content="没有访问该页面的权限">
  <meta name="Description" content="">
  <title>403 - 缺少权限</title>
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
	a{
		text-decoration: none;
	}
}
  </style>
 </head>
 <body class="navbar-fixed breadcrumbs-fixed">
  	<div align="center">
  		<img src="<%=contextPath%>/static/images/500.png" class="body-img"/>
  		<div class="body-text">对不起...你没有访问该页面的权限！</div>
  		<div><a class="body-btn" href="<%=contextPath%>/" >返回首页</a>&nbsp;<a class="body-btn" href="<%=contextPath%>/j_spring_security_logout">退出登录</a> </div>
  	</div>
 </body>
 
  
<script>
 
</script>

</body>

</html>