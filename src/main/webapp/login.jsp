<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	// 通过设置，让浏览器不缓存 
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1  cache-control用于控制网的缓存
	response.addHeader("Cache-Control", "no-store"); //Firefox  
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0    和cache-control作用相同，通常两者合用
	response.setDateHeader("Expires", -1);
	response.setDateHeader("max-age", 0);

	// 判断是否开启连锁 0 未开启  1 开启
	String is_open_chain_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_FUNC);
	if (is_open_chain_func == null) {
		is_open_chain_func = "";
	}
	//判断是否开启试用 0 未开启  1 开启
	String is_open_chain_select = YZSysProps.getProp(SysParaUtil.IS_OPEN_CHAIN_SELECT);
	if (is_open_chain_select == null) {
		is_open_chain_select = "";
	}

	// 如果已经登录，直接跳转到主页
	if (session.getAttribute("LOGIN_USER") != null) {
		// request.getRequestDispatcher("YZSystemAct/loginIndexFront.act").forward(request, response);  
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand"/>  
<!-- 
	若页面需默认用极速核，增加标签：<meta name="renderer" content="webkit"> 
	若页面需默认用ie兼容内核，增加标签：<meta name="renderer" content="ie-comp"> 
	若页面需默认用ie标准内核，增加标签：<meta name="renderer" content="ie-stand">
 -->
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/login/loginB.css" />
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/login/login.js" ></script>
<script type="text/javascript">
var is_open_chain_func = "<%=is_open_chain_func%>";
</script>
<title>UNIC大数据管理系统</title>
<style>

.closeBtn{/* 通用关闭按钮 */
	height:14px;
	width:14px;
	position:absolute;
	top:15px;
	right:15px;
	cursor:pointer;
}

.kqdsInformationDiv4Login{
	position:absolute;
	top:16px;
	left:780px;
	width:375px;
	height:445px;
	background:#fff;
	border:1px solid #ddd;
	z-index:2;
    box-shadow: 0px 0px 2px #ddd;
    padding-top:20px;
}
</style>
</head>
<body id="body" onload="doInit()">
 
<div id="bg">
	<div id="loginB">
		<div id="loginB_title">
			<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_logo.png" />
			<p>重新定义德国种植牙标准<span>THE PURE GERMANY THE NEW STANDARD</span></p>
			<a style="display: none;" href="#" id="kqdsOntrial">试用版</a>
			<a style="display: none;" href="javascript:void(0);" id="kqdsWebSite">官方网站</a>
			<%
    			if ("1".equals(is_open_chain_select)) {
    		%>
			<a class="register_btn" href="#" onclick="register();">注册账号</a>
			<%
				}
			%>
		</div>
		<div id="loginB_banner">
			<div class="banner_continer" style="background: no-repeat url(<%=contextPath%>/static/image/kqdsFront/img/loginB_banner0.jpg);">
				<div style="display: none;">
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner0.jpg" />
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner1.jpg" />
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner2.jpg" />
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner3.jpg" />
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner4.jpg" />
					<img src="<%=contextPath%>/static/image/kqdsFront/img/loginB_banner5.jpg" />
				</div>
				<!--表单-->
				<form method="post" id='loginForm'>
					<!--标题-->
					<div id="form_title">
						<hr />
						<span>用户登录</span>
					</div>
					<%
						if ("1".equals(is_open_chain_func)) {
					%>
					<div class="hospitalSelect" style="padding:0 30px;">
						<select style="width:100%;" name="organization" id="organization" class="BigSelect"></select>
					</div>
					<%
						}
					%>
					<input type="text" placeholder="用户名" name="userName" id="userName"/>
					<input class="password" type="password" placeholder="密码" name="pwd" id="pwd"/>
					<input id="login_button" type="button" value="登录" onclick="doLogin(2)"/>
				</form>
				<span></span>
				<p>我为中国而来<span>I'm here for the Chinese</span></p>
			</div>
		</div>
	</div>
			
			
			<!-- 口腔大师信息  弹出框 -->
			<div id="kqdsInformationDiv4Login" class="kqdsInformationDiv4Login" style="display:none;height:510px;">
				<img style="width:20px;height:20px; position:absolute;top:-16px;left:60px;" src="<%=contextPath%>/static/image/kqdsFront/img/icon/white_arrow.png"/>
				<img src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" class="closeBtn"></i>
				<h2 style="margin:0;height:40px;padding:0 0 20px 20px;border-bottom:1px solid #ddd;font-size:20px;">关于UNIC大数据管理系统</h2>
				<div style="padding:20px;border-bottom:1px solid #ddd;">
					<h3 style="font-size:18px;margin:0 0 10px 0;">更专注于研发医疗服务管理</h3>
					<h3 style="font-size:18px;margin:0;">国内领先的口腔医疗管理系统</h3>
					<div style="padding-top:20px;color:#7C7C7C;text-align: justify;">
						UNIC管理系统是一款便捷使用、功能强大、管理智能、数据安全的口腔医疗管理软件，通过“智能化管理，数据化经营”，帮助口腔医疗机构的管理者成为口腔大师，适用于单店、连锁、公立口腔专科医院等多种经营模式，是口腔机构管理者的得力助手，使口腔igou赚钱更轻松。
					</div>
					<div style="padding:30px 0 20px;">
						<p style="margin-bottom:5px;color:#00A6C0;">客服电话：400-025-6668</p>
						<p style="margin:0;color:#00A6C0;">官方网址：www.china-kqds.com<a style="color:#00A6C0;" href="http://www.china-kqds.com"></a></p>
					</div>
				</div>
				<div style="padding:20px;">
					<p style="margin:0 0 5px 0;height:20px;line-height:20px;">版权所有@北京海德堡联合口腔医院管理有限公司</p>
					<p style="margin:0;height:20px;line-height:20px;">当前版本2.0</p>
				</div>
			</div>
			<!-- 口腔大师信息  弹出框 END-->
			
			<!-- 试用地址  弹出框 -->
			<div id="kqdsOntrialDiv" class="kqdsInformationDiv4Login" style="display:none;height:150px;width:450px;">
				<img style="width:20px;height:20px; position:absolute;top:-16px;left:155px;" src="<%=contextPath%>/static/image/kqdsFront/img/icon/white_arrow.png"/>
				<img src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" class="closeBtn"></i>
				<div style="padding:20px;">
					<div style="padding:0 0 10px;border-bottom:1px solid #ddd;">
						<p style="margin:0;color:#00A6C0;"><a style="color:#00A6C0;" href="http://211.149.175.160:7510/kqds_majors/">试用地址：http://211.149.175.160:7510/kqds_majors/</a></p>
					</div>
					<h3 style="font-size:14px;margin:0 0 10px 0;padding-top:10px;">请使用Google浏览器，谢谢！</h3>
					<h3 style="font-size:14px;margin:0;">期待与您的合作！！！</h3>
					<!-- <div style="padding-top:20px;color:#7C7C7C;text-align: justify;">
						UNIC管理系统是一款便捷使用、功能强大、管理智能、数据安全的口腔医疗管理软件，通过“智能化管理，数据化经营”，帮助口腔医疗机构的管理者成为口腔大师，适用于单店、连锁、公立口腔专科医院等多种经营模式，是口腔机构管理者的得力助手，使口腔igou赚钱更轻松。
					</div> -->
					
				</div>
				
			</div>
			<!-- 口腔大师信息  弹出框 END-->
				
			</div>
		</div>
    </div>
</div>
<div id="bottom_login">版权所有@北京海德堡联合口腔医院管理有限公司</div> 


<script>

$(function(){
	//是否可以关闭右侧模块的
	localStorageInit();/*初始化 收缩页面的对应参数  */
	setHeight();
	setInterval(toggleBg,4000);
});

$("#kqdsWebSite").on("click",function(){
	var isapp = reqParamArr['isapp'];
	if(isapp == '1'){ // 如果是客户端
		$("#kqdsInformationDiv4Login").toggle();
	}else{
		window.open("http://www.china-kqds.com", "_blank");
	}
});

$("#kqdsOntrial").on("click",function(){
	var isapp = reqParamArr['isapp'];
	if(isapp == '1'){ // 如果是客户端
		$("#kqdsOntrialDiv").toggle();
	}else{
		window.open("http://211.149.175.160:7510/kqds_majors/", "_blank");
	}
});
//鼠标移出口腔大师信息弹框  在线人员弹框   修改密码弹框时 关闭
$("#login_bg_2").on("mouseleave",".kqdsInformationDiv4Login",function(){
	$(this).find(".closeBtn").trigger("click");
});
//所有弹框中的关闭按钮    右上角的×号
$(".closeBtn").on("click",function(){
	//退出弹框的按钮×
	//关闭当前弹框
	var parent=$(this).parent();//获得父元素   即弹框
	parent.hide();//关闭弹框
});

function localStorageInit(){/*右侧模块收缩状态 */
	localStorage.setItem("bctx","1");/*病程提醒 */
	localStorage.setItem("bb_zhcx", "1");/*综合查询 */
	localStorage.setItem("fycx", "1");/*费用查询*/
	localStorage.setItem("jdzx", "1");/* 接待中心 */
	localStorage.setItem("jrhz", "1");/*今日患者 */
	localStorage.setItem("jzqk","1");/*就诊情况*/
	localStorage.setItem("jqcx", "1");/*网电中心 */
	localStorage.setItem("jrgz", "1");/*今日工作 */
	localStorage.setItem("jzcx", "1");/*接诊查询*/	
	localStorage.setItem("khbb", "1");/*客户报备 */
	localStorage.setItem("kfzx", "1");/*客服中心 */
	localStorage.setItem("mxcx","1");/*明细查询*/
	localStorage.setItem("qfcx", "1");/*欠费查询 */
	localStorage.setItem("wdzx", "1");/*网电中心 */
	localStorage.setItem("xxcx", "1");/*信息查询*/
	localStorage.setItem("ylzx","1");/*医疗中心  */
	localStorage.setItem("yxzx", "1");/*营销中心 */
	localStorage.setItem("zxzx","1");/*咨询中心*/	
	localStorage.setItem("ht_qfxx","1");/*后台 群发消息 */
	localStorage.setItem("jfzx","1");/*积分中心 */
	
}
function toggleBg(){
	$(".bgImg").toggleClass("active");
}
function setHeight(){
	/* 页面能一屏展示 */
	var height=$(window).outerHeight();
	if(height>=720){
		height=(height-720)/2-2;
		/* $("#body").css("paddingTop",height); */
	}else{
		$("#login_bg_2").outerHeight(600-(720-height));
	}
	//根据屏幕的宽度 调整背景图的位置
	var width=$(window).outerWidth();
	if(width<=1920){
		width=(1920-width)/2;
		$(".bgImg").css("backgroundPosition","-"+width+"px 0");
	}
}

var i=1;
setInterval(function(){
	if(i>5){
		i=0;
	}
	//console.log(i);
	$(".banner_continer").css("background","no-repeat url(<%=contextPath%>/static/image/kqdsFront/img/loginB_banner"+i+".jpg)"); 
	i++;
},4000);
</script>
</body>
</html>