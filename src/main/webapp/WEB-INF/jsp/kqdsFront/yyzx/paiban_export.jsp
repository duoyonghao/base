<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
    String regdept = request.getParameter("regdept");
    if(regdept == null){
    	regdept = "";
    }
    String personid = request.getParameter("personid");
    if(personid == null){
    	personid = "";
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>排班模板下载</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.css"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlx.css"  title="no title" charset="utf-8">
<style>
	table.tableLayout{/* table布局内容距离页面顶端30px */
		margin-top:30px;
	}
	.commonText{	/*数据项的样式  */
		padding: 0 10px;
	}
	table.tableLayout select,table.tableLayout input[type="text"]{
		width:140px;
	}
</style>
</head>
<body>
<div id="container">
    <div class="infoBd">
            <table class="tableLayout">
         	<tbody>
         		<tr>
         			<td>
         				<span class="commonText">开始日期</span>
         			</td>
         			<td>
         				<input type="text"  placeholder="开始日期" id="mbstartdate" class="dhtmlxcalendar_label_hours" style="width: 120px;">
         			</td>
         			<td>
         				<span class="commonText">结束日期</span>
         			</td>
         			<td>
         				<input type="text"  placeholder="结束日期" id="mbenddate"  style="width: 120px;">
         			</td>
         		</tr>
         	</tbody>
         </table>   
         <div class="position-bottom" >
			<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="submitu()">下载</a>
			<a href="javascript:void(0);" class="kqdsCommonBtn" id="closeBtn" onclick="closepage()">关闭</a>
		</div>
     </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlx.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_minical.js"></script> <!-- 选择日期 -->

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var regdept = '<%=regdept%>';
var personid = '<%=personid%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	var myCalendar = new dhtmlXCalendarObject(["mbstartdate","mbenddate"]);
	myCalendar.hideTime();
});

//提交
function submitu() {
	var mbstartdate = $("#mbstartdate").val();
	var mbenddate = $("#mbenddate").val();
	
	if(mbstartdate == null  || mbstartdate == ''){
		layer.alert("请选择排班开始日期");
		return;
	}
	
	if(mbenddate == null  || mbenddate == ''){
		layer.alert("请选择排班结束日期");
		return;
	}
	parent.downLoadEx(regdept,personid,mbstartdate,mbenddate);   
}
function closepage(){
	parent.layer.close(frameindex);
}
</script>
</html>
