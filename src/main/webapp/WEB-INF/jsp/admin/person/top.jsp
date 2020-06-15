<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/Javascript"> 
</script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/adminIndex.css">
</head>
<body>
<h2 class="userTitle">用户管理</h2>
<!-- <table border="0" width="100%" cellspacing="0" cellpadding="3">
  <tr>
    <td>
    	
    </td>
  </tr>
</table> -->
</body>
</html>