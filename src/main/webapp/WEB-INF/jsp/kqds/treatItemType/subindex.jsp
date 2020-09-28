<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);
	
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费分类管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/adminIndex.css">
<script type="text/javascript">
var organization = '<%=organization %>';
</script>
</head>
<frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame class="leftFrame" name="leftFrame" id="leftFrame" src="<%=contextPath%>/KQDS_TreatItem_TcBackAct/toTypeLeft.act?organization=<%=organization %>" frameborder="NO" noresize scrolling="auto"/>
    <frame class="rightFrame" name="contentFrame" id="contentFrame" src="<%=contextPath%>/KQDS_TreatItem_TcBackAct/toTypeList.act?parentCode=COSTITEM_SORT&organization=<%=organization %>" frameborder="NO" scrolling="auto">	
  </frameset>
</html>