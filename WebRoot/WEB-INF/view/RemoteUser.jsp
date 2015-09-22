<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>index</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../page/frame/resource.jsp"></jsp:include>
	<script type="text/javascript">
		function initgrid(){
			$('#usergrid').datagrid({
					url:'/icore/User/getremoteUserData',
					 columns:[[    
				        {field:'us_ident',title:'ID',width:100},    
				        {field:'us_name',title:'用户名',width:200},    
				        {field:'us_pword',title:'密码',width:200,align:'right'}    
				    ]],
				    pageSize:10,
				    pagination:true
				    
			});
			
		}
	
	</script>
  </head>
  
  <body onload="initgrid()">
 
    <div id="usergrid"></div>
  </body>
</html>
