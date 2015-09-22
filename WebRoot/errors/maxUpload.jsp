<%@ page contentType="application/json;charset=UTF-8" language="java"%>
<%@page import="com.ricore.framework.utils.json.JsonResult"%>

<%
Exception e = (Exception) request.getAttribute("exception");
long maxUploadSize = 0;
if(e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException){
	maxUploadSize = ((org.springframework.web.multipart.MaxUploadSizeExceededException)e).getMaxUploadSize();
}
float kb  = maxUploadSize / (1024*1024);
pageContext.getOut().print(JsonResult.failureToJson("uploadify超出文件最大上传限制: "+String.valueOf(kb)+"M").toString());
%>
 