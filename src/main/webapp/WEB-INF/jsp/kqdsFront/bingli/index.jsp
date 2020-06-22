<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	// 患者编号
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode = "";
	}

	String regno = request.getParameter("regno");
	if(regno == null){
		regno = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>种植病历中心</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">
html{
	height:100%;
}
iframe{}
.layui-tab{
	margin:0;
}
.layui-tab-card{
	box-shadow:none;
	border:none;
}
</style>
</head>
<body>
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li target="tabIframe" src="<%=contextPath%>/KQDS_MedicalRecordAct/toZZBLList.act?usercode=<%=usercode %>&regno=<%=regno %>" class="layui-this">种植病历</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_MedicalRecordAct/toZZBLKList.act?type=1&usercode=<%=usercode %>&regno=<%=regno %>">自用病历库</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_MedicalRecordAct/toZZBLKList.act?type=0&usercode=<%=usercode %>&regno=<%=regno %>" >标准病历库</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_MedicalRecordAct/toZZBLList.act?usercode=<%=usercode %>&regno=<%=regno %>" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var usercode = "<%=usercode%>";
$(function(){
	setHeight();
})
layui.use('element',
function() {
    var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
});
$('.layui-tab-title').on('click', 'li',
function() {
    $(this).addClass('layui-this').siblings('li').removeClass('layui-this');
    var src = $(this).attr('src');
    $('#tabIframe1').attr('src', src);
});
function setHeight(){
	$("#tabIframe1").outerHeight($(window).outerHeight()-$(".layui-tab-title").outerHeight()-30);
}
$('#menuUL').find('li').eq('0').trigger('click');
</script>
</body>
</html>
