<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath;
%>
<script type="text/javascript">
var Kqfrm = Kqfrm || {};
Kqfrm.context = {
	contextPath: '<%=contextPath%>',
	basePath: '<%=basePath%>'
};
</script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jslib/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jslib/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=contextPath%>/jslib/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/jslib/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/jslib/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/jslib/json2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/frame.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/Utils.js"></script>
 

