<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>患者档案中心</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">
iframe{float: left;height: 520px;}
.layui-tab {
    margin: 0px 0;
    text-align: left!important;
}

div.layui-tab-card{/* 去掉原本加的边框阴影和边框 */
	box-shadow:none;
	border:none;
}

</style>
</head>
<body>
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li target="tabIframe" src="<%=contextPath%>/KQDS_PushAct/toPushList.act?iscs=0" class="layui-this">工作提醒</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_PushAct/toPushList.act?iscs=1">超时提醒</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_PushAct/toPushList.act?iscs=0" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
layui.use('element', function(){
	  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
	});
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	$('#tabIframe1').attr('src',src);
});
$('#menuUL').find('li').eq('0').trigger('click');  
function setIframeHeight(){
	var iframeHeight=$(window).height()-$(".layui-tab").outerHeight();
	$("#tabIframe1").outerHeight(iframeHeight);
}
$(function(){
	setIframeHeight();
	
});
</script>
</body>
</html>
