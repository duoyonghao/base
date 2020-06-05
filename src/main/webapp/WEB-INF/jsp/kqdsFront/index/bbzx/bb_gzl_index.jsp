<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	Object isyx = request.getAttribute("isyx");//营销中心进入
    if(isyx == null){
	   isyx = "0";
    }
    String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<title>报表——网电工作量统计</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">
iframe{float: left;}

html,body{
 	height:100%;
}
.layui-tab-content{
	overflow:hidden;
	padding:0;
}
.layui-tab {
    margin: 0 0 0 20px ;
    text-align: left!important;
}
.layui-tab-content{
	padding:0;
	
}
.layui-tab-card{
	box-shadow:none;
	border:none;
}
.layui-tab-title{
	margin-bottom:10px;
}
</style>
</head>
<body>
<div class="layui-tab layui-tab-card" style="">
  <ul class="layui-tab-title">
    <li target="tabIframe" src="<%=contextPath%>/KQDS_ScbbAct/toBb_WdNetPer.act" class="layui-this">咨询统计表1</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_ScbbAct/toBb_WdNetPerItem.act">咨询项目情况统计表1</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_ScbbAct/toBb_WdNetItem.act">咨询项目统计表</li>
  </ul>
  <div class="layui-tab-content" style="">
    <div class="layui-tab-item layui-show" style="height:100%;">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_ScbbAct/toBb_WdNetPer.act?isyx=<%=isyx %>&menuId=<%=menuid %>" width="100%" height="100%" frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var isyx = "<%=isyx%>";
var menuId = "<%=menuid%>";
layui.use('element', function(){
	  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
	});
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	src += '?isyx='+isyx+'&menuId='+menuId;
	$('#tabIframe1').attr('src',src);
});
setHeight();
$('#menuUL').find('li').eq('0').trigger('click'); 
function setHeight(){
	$("#tabIframe1").outerHeight($(window).outerHeight()-60);
}
</script>
</body>
</html>
