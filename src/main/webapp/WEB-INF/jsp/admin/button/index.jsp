<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson loginUser = SessionUtil.getLoginPerson(request);

	String priv_seqId = request.getParameter("priv_seqId");
	if (priv_seqId == null) {
		priv_seqId = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>按钮管理</title>
<!-- 各管理页面的首页布局 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/adminIndex.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
</script>
</head>
<frameset onload="" rows="40,*"  cols="*" frameborder="no" border="0" framespacing="0">
  	<frame class="rightFrame" name="user_title" scrolling="no" noresize src="<%=contextPath%>/YZButtonAct/toTop.act" frameborder="NO">
	<frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
	    <frame class="leftFrame" name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZButtonAct/toLeft.act" frameborder="NO" noresize scrolling="auto"/>
	    <frame class="rightFrame" name="contentFrame" id="contentFrame" src="<%=contextPath%>/YZButtonAct/toList.act" frameborder="NO" scrolling="auto">	
	</frameset>
</frameset>
</html>