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
<title>系统用户管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/adminIndex.css">
<script type="text/javascript">
</script>
</head>
<frameset onload="" rows="40,*"  cols="*" frameborder="no" border="0" framespacing="0" id="frame1">
	<!--.rightFrame 提供下边框 -->
  <frame class="rightFrame" style="border-bottom:1px solid #ddd;" name="user_title" scrolling="no" noresize src="<%=contextPath%>/YZPersonAct/toTop.act" frameborder="NO">
  <frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
 	 <!--.leftFrame 提供 下 右 边框 -->
    <frame class="leftFrame" name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZPersonAct/toLeft.act" frameborder="NO" noresize scrolling="auto"/>
    <frame class="rightFrame"  name="contentFrame" id="contentFrame" src="<%=contextPath%>/YZPersonAct/toList.act" frameborder="NO" scrolling="auto">	
  </frameset>
</frameset>
</html>