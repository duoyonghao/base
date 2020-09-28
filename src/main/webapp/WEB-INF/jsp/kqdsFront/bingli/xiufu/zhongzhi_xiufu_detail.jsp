<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();

	String seqId = request.getParameter("seqId");
	if(seqId == null){
		seqId = "";
	}
	
	String mtype = request.getParameter("mtype");
	if(mtype == null){
		mtype = "";
	}
	
	String detailFlag = "detailFlag"; // 用于Include table 页面，是否展示 增加行 ，详情页面不需要展示增加行
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>病历详情-种植修复</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/xiufu/xiufu.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/zzbl_Table.css" />
<%@include file="../inc/zhongzhiyiqi_header.jsp" %>
</head>
<body>
<form id="form1" >
<%@include file="inc/xiufu.jsp" %>

<table class="footTable">
	<%@include file="../inc/zhongzhiyiqi_footer.jsp" %>
	<tr>
		<td class="submBtnTd" colspan="8">
			<!-- 多行 -->
			<input type="hidden" name="bridge" id="bridge"  value="">
			<input type="hidden" name="overdenture" id="overdenture"  value="">
			<input type="hidden" name="cemented" id="cemented"  value="">
			<input type="hidden" name="screwed" id="screwed"  value="">
			<!-- 多行 END -->
		
			<input type="hidden" name="seqId" id="seqId" value="<%=seqId %>"> <!-- 病历主表主键 -->
			<input type="hidden" name="subSeqId" id="subSeqId" value="">
			
			<input type="hidden" name="isprint" id="isprint"  value="">
			<input type="hidden" name="status" id="status"  value="">
			
			<!-- 存储到病历库时使用 -->
			<input type="hidden" name="type" id="type"  value="">
			<input type="hidden" name="blname" id="blname"  value="">
			<!-- 存储到病历库时使用  end...-->
			
			<input type="button" class="submBtn" id="save" onclick="closeWin();" value="关闭">
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
var contextPath = '<%=contextPath %>';
var static_seqId = '<%=seqId %>'; // 种植主表主键
var static_mtype = '<%=mtype %>';
var static_usercode = '<%=usercode %>';

var static_userobj = null;
var static_subSeqId = null; // 病历子表主键
var frameindex = parent.layer.getFrameIndex(window.name);

$(function() {
    if (static_seqId == '') {
        layer.alert('请选择病历记录！' );
        return false;
    }

    var returnData = getOneByUsercode(static_usercode);
    static_userobj = returnData.data[0];

    initBaseInfo(); // 初始化基本信息
    getblcontent4HuiFu();

    $("#seqId").val(static_seqId);
});

</script>

</body>
</html>