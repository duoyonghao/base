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
	
	String detailFlag = ""; // 用于Include table 页面，是否展示 增加行 ，详情页面不需要展示增加行
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>恢复病历-术后拆线</title>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/fucha/fucha.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/zzbl_Table.css" />
<%@include file="../inc/zhongzhiyiqi_header.jsp" %>
</head>
<body>
<form id="form1" >
<%@include file="inc/chaixian.jsp" %>

<table class="footTable">
	<%@include file="../inc/zhongzhiyiqi_footer.jsp" %>
	<tr>
		<td class="submBtnTd" colspan="8">
			<!-- 多行 -->
			<input type="hidden" name="conditionImplants" id="conditionImplants"  value="">
			<!-- 多行 end -->
			
			<input type="hidden" name="seqId" id="seqId" value="<%=seqId%>"> <!-- 病历主表主键 -->
			<input type="hidden" name="subSeqId" id="subSeqId" value="">
			
			<input type="hidden" name="isprint" id="isprint"  value="">
			<input type="hidden" name="status" id="status"  value="">
			
			<!-- 存储到病历库时使用 -->
			<input type="hidden" name="type" id="type"  value="">
			<input type="hidden" name="blname" id="blname"  value="">
			<!-- 存储到病历库时使用  end...-->
			
			<%
							if(YZUtility.isNullorEmpty(detailFlag)){ // 如果不是详情页
									
									if(YZUtility.isNullorEmpty(editFlag)){ // 如果不是编辑页面
						%>
						<input type="button" id="save" class="submBtn" onclick="submitInfo(1);" value="暂存">
						<%
					}
				%>
					<input type="button" id="save" class="submBtn" onclick="submitInfo(2);" value="提交">
				<%
				}else{
					%>
					
					<input type="button" id="save" class="submBtn" onclick="addblk(1);" value="添加到自用病历库">
					<input type="button" id="save" class="submBtn" onclick="addblk(0);" value="添加到标准病历库">
					<input type="button" class="submBtn" id="print" onclick="printHtmlTable('<%=seqId %>');" value="打    印">
					<input type="button" id="save" class="submBtn" onclick="closeWin();" value="关闭">
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