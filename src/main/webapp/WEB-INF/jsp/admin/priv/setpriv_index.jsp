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
	
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置权限</title>
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
var static_seqId = "<%=priv_seqId %>";
var static_detailurl = 'YZPrivAct/selectDetail.act?seqId=' + static_seqId;
$(function(){
/* 	var privNameBZ = $("#privNameBZ",window.parent.document);
	if(privNameBZ){
		var serverData = getDataFromServer(static_detailurl);
	    if (serverData) {
	    	$(privNameBZ).html(serverData.retData.privName);
	    }
	} */
});
</script>
</head>
<frameset rows="*"  cols="210,*" frameborder="0" border="0" framespacing="0">	
    <frame name="leftFrame" id="leftFrame" src="<%=contextPath%>/YZPrivAct/toSetPrivLeft.act?priv_seqId=<%=priv_seqId%>&organization=<%=organization%>" frameborder="NO" noresize scrolling="auto"/>
    <frame name="contentFrame" id="contentFrame" style="border-left:none;" src="<%=contextPath%>/YZPrivAct/toSetPrivList.act?priv_seqId=<%=priv_seqId%>&organization=<%=organization%>" frameborder="NO" scrolling="auto">	
 </frameset>
</html>