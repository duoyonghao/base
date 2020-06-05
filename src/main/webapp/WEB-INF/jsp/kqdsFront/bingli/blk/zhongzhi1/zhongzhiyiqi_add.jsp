<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	String type = request.getParameter("type");
	
	if(type == null){ //0 标准 1自用 
		type = "";
	}
	
	String detailFlag = ""; // 用于Include table 页面，是否展示 增加行 ，详情页面不需要展示增加行
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新建病历模板_种植一期</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/blk/zhongzhi1/zhongzhi1.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/zzbl_Table.css" />
<%@include file="../../inc/zhongzhiyiqi_header.jsp" %>
</head>
<body>
<form id="form1" >

<table>
<tr >
	<td style="width:14%;">病历名称：</td>
	<td colspan="7"><input type="text"  name="blname" ></td>
</tr>
</table>

<%@include file="../../zhongzhi1/inc/zhongzhiyiqi_table2.jsp" %>
<%@include file="../../zhongzhi1/inc/zhongzhiyiqi_table3.jsp" %>
<%@include file="../../zhongzhi1/inc/zhongzhiyiqi_table4.jsp" %>

<table class="footTable">
	<tr>
		<td class="submBtnTd" colspan="8">
			<!-- 多行 -->
			<input type="hidden" name="dentition" id="dentition" value="">
			<input type="hidden" name="detailsOfImpants" id="detailsOfImpants"  value="">
			<!-- 多行 end -->
			<input type="hidden" class="submBtn" name="type" id="type"  value="<%=type %>">
			
			<input type="button" class="submBtn" id="save" onclick="submitInfo();" value="保存">
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
var contextPath = '<%=contextPath %>';
var frameindex = parent.layer.getFrameIndex(window.name);
$(function() {

});
</script>
</body>
</html>