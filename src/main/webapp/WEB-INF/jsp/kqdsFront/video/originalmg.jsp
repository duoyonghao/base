<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	  String url = request.getParameter("url");
%>
<!DOCTYPE html>
<html >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>影像资料</title>
<body >
 <img  src="<%=url%>">
</body>



</html>
