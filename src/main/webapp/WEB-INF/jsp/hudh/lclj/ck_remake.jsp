<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	String regno = request.getParameter("regno");
	String lytype = request.getParameter("lytype");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>查看每个流程具体操作项的备注信息</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <style type="text/css">
    	.text{
    		width: 260px;
    		height: 120px;
    		border-radius: 1em;
    	}
    	.input{
    		vertical-align: middle;
    		margin-bottom: 130px;
    		margin-top: -7px;
    		border-radius: 1em;
    		width: 152px;
    	}
    </style>
</head>
<body>
<!--添加路径录入信息弹窗-->
 <div class="containerAddEdit" id="ck_remake"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
     <!-- <div align="center" id="ck_remake">
    	<input type="text" readonly="readonly" class="input" id="time" value=""> 
    	<textarea class="text" readonly="readonly" id="remake"></textarea>
     </div> --> 
 </div> 
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
window.onload = function(){
    var orderTrackId = '<%=request.getAttribute("orderTrackId")%>';
	var flowLink = '<%=request.getAttribute("flowLink")%>';
	var oprationName = '<%=request.getAttribute("operateName")%>';
	var url = '<%=contextPath%>/HUDH_LCLJAct/findLcljOrderImplemenRemakeByTrackId.act';
	var param = {orderTrackId:orderTrackId, flowLink:flowLink, oprationName:oprationName};
	$.axseSubmit(url, param, function() {}, function(data) {
//		alert(JSON.stringify(data));
		var length = data.length;
//		alert(length);
		if(length >= 1) {
			for(var index in data) {
				$("#ck_remake").append(
						'<div align="center">'+
				    	'<input type="text" readonly="readonly" class="input" id="time" value="' +data[index].time + ' "> ' + '&nbsp;&nbsp;' + '</input>'+
				    	'<textarea class="text" readonly="readonly" id="remake">' + data[index].remake + '</textarea>'+
				    	'</div>'
				);
			}
		}
	}, function() {
		
	});
};
</script>
</html>
