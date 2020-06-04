<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>通用查询</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
<meta charset="utf-8" />
<style type="text/css">

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
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title" id="index">
  </ul>
  <div class="layui-tab-content" style="height: 100%;">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="" width="100%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var menuid = "<%=menuid%>";
$(function() {
    //获取当前页面所有按钮
    getButtonAllCurPage(menuid);

    $("#tabIframe1").outerHeight($(window).outerHeight() - 55);
    
    resizeIframeWindow();
    function resizeIframeWindow() {
        var winHeight = $(window).height();
        $("#tabIframe1").height(winHeight - 50); //60为 tabiframe1到iframe之间的高度差
    }
    $(window).resize(resizeIframeWindow);
    
});
//按钮权限
function getButtonPower() {
    var menubutton = "";   
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "ck_ckmanage_ckmx") { //出库明细
            menubutton += ' <li target="tabIframe" src="' + contextPath + '/KQDS_Ck_Goods_Out_DetailAct/toCkOutGoodsDetailSearch.act">' + listbutton[i].name + '</li>';///KQDS_Ck_GoodsAct/toCkCkManage.act
        } else if (listbutton[i].qxName == "ck_ckmanage_spck") {//商品出库
            menubutton += ' <li target="tabIframe" src="' + contextPath + '/KQDS_Ck_Goods_OutAct/toCkGoodsOut.act">' + listbutton[i].name + '</li>';///KQDS_Ck_GoodsAct/toCkCkManage.act
        } else if (listbutton[i].qxName == "ck_ckmanage_ksll") {//科室领料
            menubutton += ' <li target="tabIframe" src="' + contextPath + '/HUDH_KSllViewAct/toKsllCostColor.act">' + listbutton[i].name + '</li>';///KQDS_Ck_GoodsAct/toCkCkManage.act
        }
    }
    $("#index").html(menubutton);
    $("#index li").eq(0).addClass('layui-this').siblings('li').removeClass('layui-this');
    var src = $("#index li").eq(0).attr('src');
    src += '?menuId=' + menuid;
    if (menubutton) {
        $('#tabIframe1').attr('src', src);
    } else {
        layer.alert('没有权限！');
    }
}
layui.use('element',
function() {
    var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
});
//iframe 的src 设置参数
$('.layui-tab-title').on('click', 'li',
function() {
    $(this).addClass('layui-this').siblings('li').removeClass('layui-this');
    var src = $(this).attr('src');
    src += '?menuId=' + menuid;
    $('#tabIframe1').attr('src', src);
});
</script>
</body>
</html>
