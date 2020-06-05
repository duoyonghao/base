<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();

	/**下面两个参数，是通过病历库创建病历时使用*/
	String seqId_blk = request.getParameter("seqId_blk");
	if(seqId_blk == null){
		seqId_blk = "";
	}
	String mtype_blk = request.getParameter("mtype_blk");
	if(mtype_blk == null){
		mtype_blk = "";
	}
	/**下面两个参数，是通过病历库创建病历时使用 end */
	
	String detailFlag = ""; // 用于Include table 页面，是否展示 增加行 ，详情页面不需要展示增加行
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>种植一期病历</title>
<%@include file="../inc/zhongzhiyiqi_header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/bingli/zhongzhi1/zhongzhi1.js"></script>
<link  type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/zzbl_Table.css" />
</head>
<body>
<form id="form1" >
<%@include file="inc/zhongzhiyiqi_table1.jsp" %>
<%@include file="inc/zhongzhiyiqi_table2.jsp" %>
<%@include file="inc/zhongzhiyiqi_table3.jsp" %>
<%@include file="inc/zhongzhiyiqi_table4.jsp" %>

<table class="footTable">
	<tr>
		<%@include file="../inc/zhongzhiyiqi_footer.jsp" %>
		<td class="submBtnTd" colspan="8">
			<!-- 多行 -->
			<input type="hidden" name="dentition" id="dentition" value="">
			<input type="hidden" name="detailsOfImpants" id="detailsOfImpants"  value="">
			<!-- 多行 end -->
			<input type="hidden" name="usercode" value="<%=usercode%>">
			<input type="hidden" name="regno" id="regno"  value="">
			<input type="hidden" name="isprint" id="isprint"  value="">
			<input type="hidden" name="status" id="status"  value="">
			
			
			<input type="button" class="submBtn" id="save" onclick="submitInfo(1);" value="暂存">
			<input type="button" class="submBtn" id="save" onclick="submitInfo(2);" value="提交">
		</td>
	</tr>
</table>
</form>

<script type="text/javascript">
var contextPath = '<%=contextPath %>';
var static_usercode = '<%=usercode %>';
var static_userobj = null;
var frameindex = parent.layer.getFrameIndex(window.name);
var static_regno = '<%=regno %>';
$(function() {
    if (static_usercode == '') {
        layer.alert('请选择患者！' );
        return false;
    }

    if (static_regno == '') {
        layer.alert('挂号主键为空，系统错误！' );
        return false;
    }
    $("#regno").val(static_regno);

    var returnData = getOneByUsercode(static_usercode);
    static_userobj = returnData.data[0];
    // alert(static_userobj);
    initBaseInfo(); // 初始化基本信息
    getblcontent4copy('<%=seqId_blk %>','<%=mtype_blk %>'); // 从病历库 点击新建病历
});


</script>
</body>
</html>