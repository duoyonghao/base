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
    <title>是否生成路径</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <style type="text/css">
    	.a {
    		margin-left: 146px;
    	}
    	.b {
    		margin-left: 146px;
    		margin-top: -22px;
    	}
    </style>
</head>
<body>
<!-- 是否添加路径弹框 -->
<div class="containerAddEdit"> <!-- "containerAddEdit" 添加、修改回访容器的样式 -->
    <!-- <input type="hidden" class="form-control" name="seqId" id="seqId" > -->
    <div dhxbox="1" style="top: 287px; left: 721px;">
    	<div class="b">
    		<span>是否生成？</span>
    	</div><br>
    	<div>
    		<div class="a" result="true">
    			<div>
    				<button class="kqdsSearchBtn" onclick="createCp()">确定</button>
    			</div>
    		</div><br>
    		<div class="a" result="false">
    			<div>
    				<button class="kqdsSearchBtn" id="quxiao">取消</button>
    			</div>
    		</div>
    	</div>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
	function createCp() {
		$.ajax ({
			 type: 'GET',
			 data: {'start':start,'end':end},
			 url: 'HUDH_LCLJAct/saveLcljOrder.act',
			 dataType: 'json',
			 success: function(data){
			    
			 },
			 error:function(data) {
			     
			 },
		});
	}
</script>
</html>
