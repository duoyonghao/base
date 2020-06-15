<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<title>预约查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">
iframe{float: left;}
.layui-tab {
    margin: 0 0 0 19px;
    text-align: left!important;
}
.layui-tab-title{
	margin-bottom:10px;
}
div.layui-tab-card{/* 去掉原本加的边框阴影和边框 */
	box-shadow:none;
	border:none;
}
.layui-tab-content{
	padding:0;
}
</style>
</head>
<body>
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Net_OrderAct/toYyzxSearchMz.act" class="layui-this">门诊预约</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Net_OrderAct/toYyzxSearchWd.act">网电预约</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_Net_OrderAct/toYyzxSearchMz.act?menuId=<%=menuid%>" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
$(function() {
	
	//$(".layui-tab-content").height(baseHeight-50);
	setHeight();/* 初始化时 计算并设置 tabIframe1的高度 */
	$(window).resize(function(){
		setHeight();/* 页面重置时 计算并设置 tabIframe1的高度 */
	});
});
layui.use('element', function(){
	  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
	});
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	src += "?menuId="+<%=menuid%>
	$('#tabIframe1').attr('src',src);
});
$('#menuUL').find('li').eq('0').trigger('click');  
function setHeight(){
	var baseHeight = $(window).height()-1;
	$("#tabIframe1").height(baseHeight-55);
}
</script>
</body>
</html>
