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
<title>新增模板消息</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
</script>
<style>
	frame{
		background:#fff;
		border:1px solid #ddd;
	}
	
</style>
<script type="text/javascript">
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	$("#leftFrame").attr("src","<%=contextPath%>/WXTemplateItemAct/toBatchSendLeft.act");
	$("#contentFrame").attr("src","<%=contextPath%>/WXTemplateItemAct/toBatchSend.act");
});

function refreshWindow(tempObj) {
	parent.layer.close(frameindex);
}
</script>
</head>
<frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame name="leftFrame" id="leftFrame" src="" frameborder="NO" noresize scrolling="auto"/>
    <frame name="contentFrame" id="contentFrame" style="border-left:none;" src="" frameborder="NO" scrolling="auto">	
 </frameset>
</html>