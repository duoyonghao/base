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
<title>打印设置管理</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/priv/priv.css">
<style type="text/css">
iframe {
	float: left;height: 500px;
}
</style>
</head>
<body>
<h2 class="titleText">打印设置</h2>
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
  </ul>
  <div class="layui-tab-content" >
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<!-- 业务管理 各管理页面的 tabIframe1 自适应高度的js -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/index_ls.js"></script>
<script type="text/javascript">
//Tab的切换功能，切换事件监听等，需要依赖element模块
layui.use('element',
function() {
    var element = layui.element();
});
$('.layui-tab-title').on('click', 'li',
function() {
    $(this).addClass('layui-this').siblings('li').removeClass('layui-this');
    $('#tabIframe1').attr('src', $(this).attr('src'));
});

$(function(){
	var data = getDataFromServer('ChainAct/getHosList.act');
	if(data){
		var tabList = data.list;
		for (var i = 0; i < tabList.length; i++) {
			var selectStr = '';
			if(i == 0){
				selectStr = "layui-this";
			}
			var tabUrl = "<%=contextPath%>/KQDS_Print_SetAct/toList.act?organization=" + tabList[i].deptCode;
			var tabHtml = '<li target="tabIframe" src="' + tabUrl + '" class="' + selectStr + '">' + tabList[i].deptName + '</li>';
			$(".layui-tab-title").append(tabHtml);
			// 默认展示第一个
			if(i == 0){
				$('#tabIframe1').attr('src', tabUrl);
			}
        }
		var tabUrl = "<%=contextPath%>/KQDS_Print_SetAct/toList.act?organization=";
        var tabHtml = '<li target="tabIframe" src="' + tabUrl + '" class="">公用设置</li>';
        $(".layui-tab-title").append(tabHtml);
	}
	setHeight();
	$(window).resize(function(){
		setHeight();
	});
});
</script>
</body>
</html>
