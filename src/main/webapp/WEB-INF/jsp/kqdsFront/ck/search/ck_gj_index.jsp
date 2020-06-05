<!-- wl整理 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>库存报警</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">
iframe{}
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
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Ck_GoodsAct/toCkGjMinSearch.act" class="layui-this">下限报警</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Ck_GoodsAct/toCkGjMaxSearch.act">上限报警</li>
  </ul>
  <div class="layui-tab-content" style="    padding: 0px 10px;">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_Ck_GoodsAct/toCkGjMinSearch.act" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript">
$(function(){
	resizeIframeWindow();
    function resizeIframeWindow() {
        var winHeight = $(window).height();
        $("#tabIframe1").height(winHeight - 50); //60为 tabiframe1到iframe之间的高度差
    }
    $(window).resize(resizeIframeWindow);
});

layui.use('element', function(){
	  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
	});
$("#tabIframe1").outerHeight($(window).height()-$(".layui-tab-title").outerHeight()-5);
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	$('#tabIframe1').attr('src',src);
});
$('#menuUL').find('li').eq('0').trigger('click');  
</script>
</body>
</html>
