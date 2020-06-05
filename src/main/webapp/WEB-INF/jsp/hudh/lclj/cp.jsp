<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>临床路径</title>
<!-- 系统中只有一个页面 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/yxzx/active.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/cp.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

</head>
<body>
	<div class="top-info">
		 <div class="widget-body">
        	<div class="row widget-main ">
            	<div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" title="编号">
            		<span>编号:</span>
                	<span class id="bh">无</span>
            	</div>
            	<div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" title="时间">
            		<span>创建时间:</span>
                	<span class id="time">无</span>
                </div>
                <div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" title="完成情况">
                	<span>完成情况:</span>
                	<span class id="comsit">无</span>
                </div>
                <div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" title="牙齿总数">
                	<span>牙齿总数:</span>
                	<span class id="ycAll"></span>&nbsp;
                	<span>颗</span>
                </div>
                <div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" title="未做牙齿数">
                	<span>未做牙齿数:</span>
                	<span class id="uncomNum"></span>&nbsp;
                	<span>颗</span>
                </div>
            </div>
            <div class="row widget-main ">
            	<div class="col-xs-12 col-sm-4 control-label clo-itsm hr2 text-left" style="margin-top:10px;" title="挂号时间">
                	<button class="kqdsCommonBtn" onclick="buttonFun.ss();">手术</button>
                	<button class="kqdsCommonBtn" onclick="buttonFun.sclj();">生成路径</button>
            	</div>
            </div>
		</div>
	</div>
	<!--右侧信息浏览
	<div class="lookInfoWrap">
		<div class="columnWrap" style="height: 100%; width: 796px;">
			<div id="div" class="columnHd" style="padding: 0 11px; line-height: 40px; font-size: 14px; color: #FFFFFF; background: #00A6C0;">
					<span class="commonText">编号</span>    <input type="text" id="bh"/>&nbsp;
					<span class="commonText">牙齿总数</span>  <input type="text" id="ycAll"/>&nbsp;
					<span class="commonText">完成情况</span>  <input type="text" id="comsit"/>&nbsp;
					<span class="commonText">未做牙齿数</span> <input type="text" id="uncomNum"/>&nbsp;
					<span class="commonText">时间</span>     <input type="text" id="time"/>&nbsp;
					<button class="kqdsCommonBtn" onclick="goOperation()">手术</button>
			</div>
		</div>
	</div>-->
	<!--手术临床跟踪信息--> 
	<div class="context" id="context">
		<div class="no-sstrack">暂无临床跟踪信息</div>
		
	</div>
	
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/trcc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/yxzx/upload.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/lclj/cp.js"></script>
<script type="text/javascript"></script>

</html>
