<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();

	String seqId = request.getParameter("seqId");
	if(seqId == null){
		seqId = "";
	}
	
	String type = request.getParameter("type"); // 0 标准 1 自用
	if(type == null){
		type = "";
	}
	
	String detailFlag = ""; // 用于Include table 页面，是否展示 增加行 ，详情页面不需要展示增加行
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>编辑病历-种植二期病历</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/blk/zhongzhi2/zhongzhi2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/zzbl_Table.css" />
<%@include file="../../inc/zhongzhiyiqi_header.jsp" %>
</head>
<body>
<form id="form1" >
<table>
<tr>
	<td style="width:14%;">病历名称：</td>
	<td colspan="7"><input type="text"  name="blname" id="blname" ></td>
</tr>
</table>
<%@include file="../../zhongzhi2/inc/zhongzhi2.jsp" %>
<table class="footTable">
	<tr>
		<td class="submBtnTd" colspan="8">
			<!-- 多行 -->
			<input type="hidden" name="preoperativeExamination" id="preoperativeExamination" value="">
			<input type="hidden" name="teethCondition" id="teethCondition"  value="">
			<input type="hidden" name="implantgumCondition" id="implantgumCondition"  value="">
			<input type="hidden" name="implantExposure" id="implantExposure"  value="">
			<input type="hidden" name="xrayExamination" id="xrayExamination"  value="">
			<!-- 多行 end -->
		
			<input type="hidden" name="seqId" id="seqId" value="<%=seqId%>"> <!-- 病历主表主键 -->
			<input type="hidden" name="subSeqId" id="subSeqId" value="">
			
			<input type="hidden" name="type" id="type"  value="<%=type%>">
			
			<%
							if(YZUtility.isNullorEmpty(detailFlag)){
						%>
					<input type="button" class="submBtn" id="save" onclick="submitInfo();" value="保存">
					<%
				}else{
					%>
					<input type="button" class="submBtn" id="save" onclick="closeWin();" value="关闭">
					<%
				}
			%>
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
var contextPath = '<%=contextPath %>';
var static_seqId = '<%=seqId %>'; // 种植主表主键
var static_mtype = '4';

var static_subSeqId = null; // 病历子表主键
var frameindex = parent.layer.getFrameIndex(window.name);

$(function() {
    getblcontent4HuiFu();
    $("#seqId").val(static_seqId);
});


</script>
</body>
</html>