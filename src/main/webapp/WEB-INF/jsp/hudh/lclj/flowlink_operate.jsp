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
    <title>添加回访</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/layer.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowlink_operate.css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/cp.css">
    
    <!-- 获取modelandview传递过来的跟踪表id -->
    <script type="text/javascript">
           var orderTrackId = '<%=request.getAttribute("orderTrackId")%>';
    </script>
</head>
<body>
<!--添加手术信息弹窗-->
<div class="flowlink-context">
	<div class="widget-box transparent">
    <div class="widget-body">
        <div class="widget-main">
            <div class="col-xs-12 col-sm-12 control-label clo-itsm hr2" id="flowlink-top" title="临床跟踪"></div>
     	</div>
     	<div class="widget-main" id="flowlink-contrain">
     		<span id="current_flow_link" class="hide"></span>
     		<div class="col-xs-12 col-sm-12 control-label clo-itsm hr2 hide"  id="ss" name="operate-item"></div>
     		<div class="col-xs-12 col-sm-12 control-label clo-itsm hr2 hide"  id="shgc"  name="operate-item"></div>
     		<div class="col-xs-12 col-sm-12 control-label clo-itsm hr2 hide"  id="dy"  name="operate-item"></div>
     		<!-- <div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 flowlink-operate text-center" title="临床跟踪">
     			<div class="track-operate-box">
     				<span>化验项目1s</span>
     				<span name="operate-info">已完成</span><br>
     				<div align="center" class="operatebuttons">
    					<a href="javascript:void(0);" class="operate-button"  id="wanchen" title="完成">完成</a>
    					<a href="javascript:void(0);" class="operate-button"  id="tianjia" title="添加">添加</a>
    					<a href="javascript:void(0);" class="operate-button"  id="chakan" title="查看">查看</a>
    				</div>
     			</div>
     		</div>!-->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/lclj/flowlink_operate.js"></script>
</html>
