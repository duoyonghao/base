<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	// 患者编号
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
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
iframe{float: left;}
.layui-tab{
	margin:0;
	overflow:hidden;
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
    <li target="tabIframe" src="<%=contextPath%>/KQDS_UserDocumentAct/toGrxx.act" class="layui-this">个人信息</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Net_OrderAct/toNetorder.act">网电信息</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_UserDocumentAct/toYyzxMz.act">门诊预约</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_ReceiveInfoAct/toReceive.act" >咨询记录</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_CostOrder_DetailAct/toCostDetail.act" >费用详情</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_VisitAct/toVisitList2.act" >回访管理</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_GiveItem_UseRecordAct/toZengSongList.act" >赠送项目</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_UserDocumentAct/toGrxx.act?usercode=<%=usercode%>" width="100%" height="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var usercode = "<%=usercode%>";
layui.use('element', function(){
	  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
	});
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	var src1 = src + "?usercode="+usercode;
	$('#tabIframe1').attr('src',src1);
});
$('#menuUL').find('li').eq('0').trigger('click');  
$(".layui-tab-content").outerHeight($(window).height()-$(".layui-tab-title").outerHeight()-5);
$("#tabIframe1").outerHeight($(window).height()-$(".layui-tab-title").outerHeight()-13);
</script>
</body>
</html>
