<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String deptOrgName = "";
	String userSeqId = "";
	String userId = "";
	String userName = "";
	String personRole = "";
	String recordaccount = "";
	String recordpwd = "";
	if (person != null) {
		userName = person.getUserName();
		userId = person.getUserId();
		userSeqId = person.getSeqId();
		deptOrgName = ChainUtil.getOrganizationNameByPerson(person, request) + "-" + ChainUtil.getDeptNameByPerson(person, request);
		// 录音账号、密码
		recordaccount = person.getRecordaccount();
		recordpwd = person.getRecordpwd();
		// MD5 加密
		if(recordpwd != null)
		recordpwd = YZUtility.getMd5Str(recordpwd);
	}

	/**
	 * 是否开启微信 0 不开启 1开启
	 */
	String is_open_wechat = YZSysProps.getProp(SysParaUtil.IS_OPEN_WECHAT);
	if(is_open_wechat == null){
		is_open_wechat = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta charset="utf-8" />
<link rel="shortcut icon" href="<%=contextPath%>/static/image/image/icon/favicon.ico" mce_href="<%=contextPath%>/static/image/image/icon/favicon.ico" type="image/x-icon">
<title>UNIC大数据管理系统</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/common.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

</head>
<style type="text/css" rel="stylesheet">
	.slogin{
		display: block;
		float: left;
		font-size: 15px;
		line-height: 24px;
 		font-weight: bold; 
		color: white;
		padding-left: 15px;
		border-left: 2px solid white;
		letter-spacing: 5px;
	}
	.slogin>span{
		display: block;
		font-size: 12px;
		line-height: 12px;
		font-weight: normal;
		letter-spacing: 0px;
	}
	#container{
		position: relative;
	}
	#header {
	    width: 100%;
	    box-sizing: border-box;
	    position: static;
        min-width: auto;
/*         overflow: hidden;  */
	}	
</style>
<body>
	<div id="container">
		<!--头部-->
		<div id="header">
			<div class="infoBox">
			<!--用户名字  -->
				<span id="uname" style="cursor:pointer;"><%=userName%></span>
				<div class="nowstate" style="margin-right:10px;">
					<span id="nowstate" class="nowstate" data-value="0" style="display:inline-block;margin-right:0;cursor:pointer;margin-left: 3px;">空闲</span>
					<ul id="choiceState" class="choiceState">
						<li class="stateValue" data-value="0">
							<span class="textItem">空闲</span>
							<!-- <span class="choiceItem icon-leftArrow"></span> -->
						</li>
						<li class="stateValue" data-value="1">
							<span class="textItem">忙碌</span>
							<!-- <span class="choiceItem icon-busy"></span> -->
						</li>
						<li class="stateValue" data-value="2">
							<span class="textItem">休息</span></span>
							<!-- <span class="choiceItem icon-leisure "> -->
						</li>
						<li class="stateValue" data-value="3">
							<span class="textItem">离开</span>
							<!-- <span class="choiceItem icon-leave"></span> -->
						</li>
					</ul>
				</div>
				 <!--<span style="margin-right:5px; cursor: pointer;" onclick="openOnline()">
					在线人数： 
					<span id="userCountInput" style="text-decoration: underline;"> </span>
				</span>  -->
			<!-- 切换在线状态的下拉 -->
				<ul id="userul" style="margin:0px;">
				    <%-- <li>当前门诊：<%=ChainUtil.getCurrentOrganizationName(request)%> &nbsp;&nbsp;门诊编号：<%=ChainUtil.getCurrentOrganization(request)%></li> --%>
					<li onclick="todaywork();">
						<span class="LvUpTopIcon icon20 todaywork-tip-icon" style="margin-right:0;position:relative;top:4px;"></span>今日工作</li>
					<li onclick="openPushList();">
						<span class="LvUpTopIcon icon20 system-tip-icon" style="margin-right:0;position:relative;top:4px;"></span>
						<span id="PusgCount" class="notRead" >0</span>
						系统提示
					</li>
					<%
						if("1".equals(is_open_wechat)){
							%>
							<script type="text/javascript">
								/************************************************** websocket 相关 */
								var websocket = null;
								//判断当前浏览器是否支持WebSocket
								if ('WebSocket' in window) {
								    websocket = new WebSocket("ws://" + localhostPahtless + projectName + "/checkNotReadWeChatMsg/" + wechat_userid);
								} else {
								    alert('当前浏览器 Not support websocket')
								}
		
								//连接发生错误的回调方法
								websocket.onerror = function() {
								    // console.log("WebSocket连接发生错误");
								};
		
								//连接成功建立的回调方法
								websocket.onopen = function() {
								    // console.log("WebSocket连接成功");
								}
		
								//接收到消息的回调方法
								websocket.onmessage = function(event) {
									updateWdCount(event.data);
								}
		
								//连接关闭的回调方法
								websocket.onclose = function() {
								    // console.log("WebSocket连接关闭");
								}
		
								//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
								window.onbeforeunload = function() {
								    closeWebSocket();
								}
		
								//强制关闭浏览器  调用websocket.close（）,进行正常关闭
								window.onunload = function() {
								    closeWebSocket();
								}
		
								//关闭WebSocket连接
								function closeWebSocket() {
								    websocket.close();
								}

								/************************************************** websocket 相关 */
								var wechat_userid = "<%=userSeqId %>";
								var socketParam = {
								    uri: "/checkNotReadWeChatMsg/" + wechat_userid,
								    onMessage: function(event) {
								    	updateWdCount(event.data);
								    }
								};
								// 创建对象
								// var websocket4WeChat = new KqdsWebSocket(socketParam);  // 这种写法客户端不支持
								/************************************************** websocket 相关 end... */
							
								function updateWdCount(data){
									if (!isJSON(data)) {
									    return false;
									}
									data = JSON.parse(data);
									$("#weixinWdCount").html(data.wdcount);
								}
								
								function getWeixinWdCount(){
									var reqUrl = "WXReceiveTextAct/selectWdMsgCount.act";
									var rtData = getDataFromServer(reqUrl);
									if(rtData){
										$("#weixinWdCount").html(rtData.wdcount);
									}
								}
								
								$(function(){
									getWeixinWdCount(); 
								});
								
							</script>
							<li onclick="showKFChat();">
								<span class="icon20 wxTopIcon" style="margin-right:0;position:relative;top:6px;right:3px;"></span>
								<span id="weixinWdCount" class="notRead" style="left:4px">0</span>
								微信客服
							</li>
							<%
						}
					%>
					<li onclick="showOnlinePersonDiv();" style="position:relative;">
						<span class="icon20 onlinePersonIcon" style="margin-right:0;position:relative;top:6px;right:3px;"></span>
						<span id="userCountInput" class="notRead">12</span>
						在线人数
						
					</li>
					<li onclick="doLogout();">
						<span class="LvUpTopIcon icon20 exit-icon" style="margin-right:0;position:relative;top:6px;right:3px;"></span>退出
					</li>
				</ul>
				
				<span id="userImg" class="userImg"></span>
			<!--用户信息及 密码修改   弹框  -->
				<div id="userMeassageDiv" class="userMeassageDiv" style="display:none;font-size:14px;">
					<img style="width:20px;height:20px; position:absolute;top:-16px;right:240px;" src="<%=contextPath%>/static/image/kqdsFront/img/icon/white_arrow.png"/>
					<img src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" class="closeBtn"></i>
					<div class="H42Div">
						<div class="left" style="text-align:right;line-height:22px;height:22px;font-size:22px;padding-right: 12px;">
							<%=userName%> 
						</div>
						<div class="right" style="height:22px;padding-left:25px;padding-top:4px;">
							<%=deptOrgName%>
						</div>
					</div>
					<div style="padding:10px 0; border-bottom:1px solid #ddd;">
						<div>
							<div class="left" style="text-align:right;line-height:40px;height:40px;">
								当前门诊：
							</div>	
							<div class="right" style="height:40px;line-height:40px;">
								<%=ChainUtil.getCurrentOrganizationName(request)%> 
							</div>
						</div>
						<div>
							<div class="left" style="text-align:right;line-height:40px;height:40px;">
								门诊编号：
							</div>	
							<div class="right" style="height:40px;line-height:40px;">
								<%=ChainUtil.getCurrentOrganization(request)%> 
							</div>
						</div>
					</div>
					<form id="updataPasswordForm" class="updataPasswordForm" style="padding:20px 0;">
						<input type="hidden" name="seqId" id="seqId">
						<div style="padding:5px 0px;">
							<div class="left" style="text-align:right;line-height:30px;height:30px;">
								原始密码：
							</div>	
							<div class="right" style="height:30px;line-height:30px;">
								<input type="password" name="oldpwd" id="oldpwd">
							</div>
						</div>
						<div style="padding:5px 0px;">
							<div class="left" style="text-align:right;line-height:30px;height:30px;color:#00A6C0 ;">
								新密码：
							</div>	
							<div class="right" style="height:30px;line-height:30px;">
								<input type="password" name="newpwd1" id="newpwd1" >
							</div>
						</div>
						<div style="padding:5px 0px;">
							<div class="left" style="text-align:right;line-height:30px;height:30px;color:#00A6C0 ;">
								确认新密码：
							</div>	
							<div class="right" style="height:30px;line-height:30px;">
								<input type="password" name="newpwd2" id="newpwd2" >
							</div>
						</div>
						<div style="padding-top:15px;text-align:center;">
							<div class="right" style="height:30px;line-height:30px;">
								<button id="submitUpdataPasswordBtn" type="button" class="kqdsSearchBtn" style="height:30px;line-height:30px;font-size:16px;width:100px;">确 定</a>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="header-main">
<!-- 口腔大师 Logo  -->
				<span class="logoText"></span>
				<p class="slogin">重新定义德国种植牙标准<span>THE PURE GERMANY THE NEW STANDARD</span></p>
<!-- 口腔大师信息  弹出框 -->
				<div id="kqdsInformationDiv" class="kqdsInformationDiv" style="display:none;">
					<img style="width:20px;height:20px; position:absolute;top:-16px;left:60px;" src="<%=contextPath%>/static/image/kqdsFront/img/icon/white_arrow.png"/>
					<img src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" class="closeBtn"></i>
					<h2 style="margin:0;height:40px;padding:0 0 20px 20px;border-bottom:1px solid #ddd;font-size:20px;">关于UNIC大数据管理系统</h2>
					<div style="padding:20px;border-bottom:1px solid #ddd;">
						<h3 style="font-size:18px;margin:0 0 10px 0;">更专注于研发医疗服务管理</h3>
						<h3 style="font-size:18px;margin:0;">国内领先的口腔医疗管理系统</h3>
						<div style="padding-top:20px;color:#7C7C7C;text-align: justify;">
							UNIC大数据管理系统是一款便捷使用、功能强大、管理智能、数据安全的口腔医疗管理软件，通过“智能化管理，数据化经营”，帮助口腔医疗机构的管理者成为口腔大师，适用于单店、连锁、公立口腔专科医院等多种经营模式，是口腔机构管理者的得力助手，使口腔igou赚钱更轻松。
						</div>
						<div style="padding:30px 0 20px;">
							<p style="margin-bottom:5px;color:#00A6C0;">客服电话：010-84240873</p>
							<p style="margin:0;color:#00A6C0;">官方网址：http://www.hdbkq.cn<a style="color:#00A6C0;" href="http://www.hdbkq.cn"></a></p>
						</div>
					</div>
					<div style="padding:20px;">
						<p style="margin:0 0 5px 0;height:20px;line-height:20px;">版权所有@北京海德堡联合口腔医院管理有限公司</p>
						<p style="margin:0;height:20px;line-height:20px;">当前版本2.0</p>
					</div>
				</div>
				
			</div>
<!-- 页头 搜索框 -->
			<!-- <div class="searchWrap" style="overflow:hidden;">
				<input type="text" placeholder="信息搜索框" class="searchInput">
				<a href="javascript:void(0);" id="infosearch" class=" icon16  searchblueBtn"></a>
			</div> -->
<!-- 在线人员展示弹框 -->			
			<div id="onlinePersonDiv" class="onlinePersonDiv" style="display:none;">
				<img style="width:20px;height:20px; position:absolute;top:-16px;right:123px;" src="<%=contextPath%>/static/image/kqdsFront/img/icon/white_arrow.png"/>
				<img src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" class="closeBtn"></i>		
				<div class="onlinePersonDiv_header" onclick="togglefold(this)">
					在线人员 <span class="icon20 blueArrow"></span>
				</div>
				<div class="onlinePersonDiv_body">
					<ul class="depateUl">
						<!-- <li>
							<p>院办</p>
							<ul class="personStateUl">
								<li>
									<span style="width:83px;">空闲</span>
									<span style="width:100px;">刘医生</span>
									<span style="">2018-01-30 20:20:20</span>
								</li>
							</ul>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
		<!--侧边栏-->
			<div class="sideBar nohc-scroll-webkit">
				<ul class="slideMenu">
					<c:forEach items="${menuList}" var="menu1">
						<c:if test="${fn:contains(menu1.menuname,'#')}">
							<c:forEach items="${menu1.hasmenu}" var="menu2" varStatus="status">
								<li>
									<c:choose>  
									   <c:when test="${'[]' != menu2.hasmenu}"> 
									   <div class="item">
									   	 <img class="image-icon ${menu2.icon}" src="<%=contextPath %>/static/image/kqdsFront/img/icon/${menu2.icon}.png"/>${menu2.menuname}
									     <%--  <span class="lvUp-icon icon20 ${menu2.icon}"></span>${menu2.menuname} --%>
								      </div> 
										    <div class="subChild">
												<ul>
													 <c:forEach items="${menu2.hasmenu}" var="menu3" varStatus="status2">
														<li target="iframe" url='<%=contextPath %>${menu3.url}?menuId=${menu3.menuid}'>
															<a href="javascript:void(0);">${menu3.menuname}</a></li>
													</c:forEach> 
												</ul>
											</div>
									   </c:when>  
									   <c:otherwise>
								         <c:choose>  
								           <c:when  test="${fn:contains(menu2.menuname,'首页')}">
									           <div class="item" style="display:none;" id="index_sy" onclick="getmenubuttonParent('${menu2.url}?menuId=${menu2.menuid}')" >
										        <span id="sy_menuid">${menu2.menuid}</span>
										        <img class="image-icon ${menu2.icon}" src="<%=contextPath %>/static/image/kqdsFront/img/icon/costDetailIcon.png"/><!-- 这儿随便写个存在的图片就行 -->
										        <%-- <span class="lvUp-icon icon20 ${menu2.icon}"></span>${menu2.menuname} --%>
										       </div> 
								           </c:when>
							           	   <c:otherwise>
						           	  		   <!-- 首页隐藏 菜单 -->	
						           	  		   <c:choose>  
							           	  		   <c:when  test="${fn:contains(menu2.url, 'loginAdmin.act')}">
											           <div class="item" onclick="openSlideMenu('${menu2.url}')">
											           	<img class="image-icon ${menu2.icon}" src="<%=contextPath %>/static/image/kqdsFront/img/icon/${menu2.icon}.png"/>${menu2.menuname}
												       <%--  <span class="lvUp-icon icon20 ${menu2.icon}"></span>${menu2.menuname} --%>
												       </div> 
										           </c:when>
							           	   		   <c:otherwise>
											           <div class="item"   onclick="getmenubuttonParent('${menu2.url}?menuId=${menu2.menuid}')" >
												       <img class="image-icon ${menu2.icon}" src="<%=contextPath %>/static/image/kqdsFront/img/icon/${menu2.icon}.png"/>${menu2.menuname}
												        <%--   <span class="lvUp-icon icon20 ${menu2.icon}"></span>${menu2.menuname} --%>
												       </div> 
										           </c:otherwise>
								               </c:choose>
							              </c:otherwise>
								         </c:choose>
									   </c:otherwise>  
									</c:choose>  
								</li>
							</c:forEach>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		<div id="mainWrap">
			<!--主内容区-->
			<div class="mainContainer">
				<iframe id="iframe" name="iframe" src="" frameborder="0"
					width="100%" height="100%" class="mainFrame" allowfullscreen="true" allowtransparency="true"></iframe>
			</div> 
		</div>
	</div>
	<!--下拉面板-->
	<div class="searchPanle" style="display: none; padding-top:25px;">
		<img alt="关闭" src="<%=contextPath%>/static/image/kqdsFront/img/closeGray.png" onclick="closeInfoDiv();" id="closeInfoDiv">
		<div style="text-align:center;width:100%;">
			
		</div> 
		<ul id="infoul" style="overflow: auto;">
		</ul>
	</div>
</body>
<script type="text/javascript">
var userName = '<%=userName %>';
var contextPath = "<%=contextPath%>";
var personrole = "<%=personRole%>";
var menubutton = "";
var userSeqId = "<%=userSeqId%>";
var userId = "<%=userId%>";
organization = "<%=ChainUtil.getCurrentOrganization(request)%>";

//定时2小时内无任何操作自动下线
//Format("yyyy-MM-dd hh:mm:ss");转换日期的格式
var lastTime = new Date().getTime();
var currentTime = new Date().getTime();

$(function(){
  /* 鼠标移动事件 */
  $(document).mouseover(function(){
      lastTime = new Date().getTime(); //更新操作时间
      //console.log("操作时间==--==="+lastTime);
  });
  var userAgent = navigator.userAgent; 
  if (userAgent.indexOf("iPad") > -1){
	  $("#userImg").css("display","none");
	  $("#nowstate").css("width","30px");
	  $("#header .header-main").css("width","470px");
	  $("#header .infoBox").css("margin","0").css("width","340px");
	  $("#userul").find("li").eq(2).css("display","none");
	  $("#userul").find("li").eq(3).css("display","none")
	}
});

function testTime(){
  currentTime = new Date().getTime(); //更新当前时间
  //console.log("现在时间===--=="+currentTime);
  var cha = currentTime-lastTime;
  //console.log("时间差=="+parseInt(cha));
  //设置时间为2个小时，如无操作强制下线
  var timeOut = 3600000; //设置超时时间： 10分= 10 * 60 * 1000 两小时： 7200000
  //console.log("超时时间==--==="+timeOut);
  if(parseInt(cha) > parseInt(timeOut) ){ //判断是否超时
  	//去掉定时器的方法  
      window.clearInterval(timer);
       //console.log("超时");
		layer.open({
	        title: '提示',
	        closeBtn: 0,
	        content:'登录已超时，请重新登录！',
	        yes: function(index, layero){
	        	//按钮【按钮一】的回调,点击确认销毁session并返回登录页
	        	location.href = "/base/YZSystemAct/doLogout.act";
	        	}
	    });
  }
}
/* 定时器  间隔1秒检测是否长时间未操作页面  */
var timer = window.setInterval(testTime, 1000);
/**
 *首次登录修改密码
 */
$(function() {
	$.ajax({
		type: "GET",
      url: contextPath +"/YZSystemAct/QueryFirstLog.act",
      data: {userName:userName,
      	organization:organization},
      dataType: "json",
      success: function(data){     
      //alert("返回数据==="+data.retMsrg);
      if(data.retMsrg=='true'){
      		pwdset();
      		}
         }
	})
})
//首次登录密码修改
function pwdset() {
	layer.open({
      type: 2,
      title: '修改密码',
      closeBtn: 0,
      shadeClose: true,
      //点击遮罩关闭层
      area: ['400px', '260px'],
      content: contextPath + '/YZPersonAct/toEditPwd.act'
  });
}
function showKFChat(){
	layer.open({
        type: 2,
        title: '微信客服【' + userName + '】',
        maxmin: false,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['80%', '80%'],
        content: contextPath + '/wechat/chat/list4chat.html'
    });
}

function closeInfoDiv() {
    $('.searchPanle').slideUp(300);
}

//打开首页的一级菜单
function openSlideMenu(url) {
    //获取页面打开凡是 isapp=1  chrome app的方式打开	 
    var isapp = localStorage.getItem("isapp");
    if (isapp == "1") {
        window.open(contextPath + url, "_self");
    } else {
        window.open(contextPath + url, "_blank");
    }
}
//父级菜单  针对只有一级菜单的，并且一级菜单配置了url
function getmenubuttonParent(url) {
    $("#iframe").attr({
        "src": contextPath + url
    });
}
//　今日工作，功能类似于返回桌面
function todaywork() {
    $("#index_sy").click();
}
function closeModule(){
	$(".searchPanle").slideUp(300);
}
$(function() {
	var timeOutOne;//定义一个定时器变量
	imgremoveStr("a-.png");
	/* 鼠标移出搜索框后1秒后如果不移入 百科展示框 就关闭百科展示框 */
	$(".searchWrap").on("mouseleave",function(){
		clearTimeout(timeOutOne);
		timeOutOne=setTimeout(closeModule,1000);
	}); 
	/* 鼠标移入百科展示框时  清除'1秒后关闭百科展示框的任务' */
	$(".searchPanle").on("mouseenter",function(){
		clearTimeout(timeOutOne);
	}); 
	/* 鼠标移出百科展示框时关闭 */
	$(".searchPanle").on("mouseleave",function(){
		$(this).slideUp(300);
	});
	//鼠标移出口腔大师信息弹框  在线人员弹框   修改密码弹框时 关闭
	$("#header").on("mouseleave","#userMeassageDiv,#kqdsInformationDiv,#onlinePersonDiv",function(){
		$(this).find(".closeBtn").trigger("click");
	});
	/* 用户头像点击弹出信息及密码修改框 */
	$("#userImg,#uname").on("click",function(){
		$("#userMeassageDiv").toggle();
	});
	/* 左上角logo被点击后 展示/关闭 口腔大师信息弹框 */
	$(".logoText").on("click",function(){
		$("#kqdsInformationDiv").toggle();
	});
	//所有弹框中的关闭按钮    右上角的×号
	$(".closeBtn").on("click",function(){
		//退出弹框的按钮×
		//关闭当前弹框
		var parent=$(this).parent();//获得父元素   即弹框
		parent.hide();//关闭弹框
		var id=parent.attr("id");//获得弹框的id
		//查找弹框内是否有输入框等表单控件    并获得数量
		var inputCount=$("#"+id).find("input[type=text],input[type=password]").length;
		//如果输入框数量>0即存在输入框，则传入id 清空id下所有的表单数据
		if(inputCount>0){
			clearInputValue(id);
		}
	});
	//人员展示界面  "部门名"被点击时关闭下面的成员列表
	$(".depateUl").on("click","p",function(){
		$(this).next().toggle();
	});
	//修改密码页面  确认按钮被点击后 提交数据修改密码
	$('#submitUpdataPasswordBtn').on('click',function() {
	    submitForm();
	});
	// 检查用户是否超时
	checkUserOnlineStatus();
	getUserStatus();
	//初始化在线人员
	doInit();
    $("#iframe").attr({
        "src": contextPath + '/KQDS_UserDocumentAct/toFirst_Center.act?menuId=' + $("#sy_menuid").html()
    });

    //1、侧边栏切换事件
    $('.sideBar').on('click', '.item,.subChild li',
    function(e) {
        var url = "";
        if ($(e.target).hasClass('item')) {
            url = $(this).attr('url');
          	//修改其他icon为灰色 
        	$(this).parent('li').siblings('li').each(function(i,elem){
        		if($(elem).hasClass("unfold")){
        			var img2=$(elem).find("img");
	           		var str2=imgremoveStr(img2.attr("src"));
	           		img2.attr("src",str2);
        		} 
        	});
            $(this).parent('li').toggleClass('unfold').siblings('li').removeClass('unfold');
        	//根据当前状态 设置icon的颜色
        	var img=$(this).parent("li").find("img");
        	var str=img.attr("src");
        	if($(this).parent("li").hasClass("unfold")){
        		str=imgaddStr(str);
        	}else{
        		str=imgremoveStr(str);
        	}
        	img.attr("src",str);
        	
        } else {
            $(this).parents('.slideMenu').find('li').removeClass('current');
            $(this).addClass('current');
            var arr = $(this).attr('url').split("?");
            for (var i = 0; i < arr.length; i++) {
                if (i == 0) {
                    url += arr[i] + "?";
                } else {
                    url += arr[i] + "&";
                }
            }
            url = url.substring(0, url.length - 1);
        }
        $('.mainFrame').attr('src', url);
    });
	function imgaddStr(str){//设置蓝色icon的文件名
		if(!str){ // 如果没有配置图标，则不处理
			return "";
		}
		var arr=str.split(".");
		str=arr[0]+"-"+".png";
		
		return str
	}
	function imgremoveStr(str){//设置灰色icon的文件名
		var arr=str.split("-");
		str=arr.join("");
		
		return str
	}
    $('#infosearch').click(function(e) {
        var leftV = $('.searchInput').offset().left,
        topV = $('.searchInput').offset().top + 32;
        
        var searchinput = $(".searchInput").val();
        /* 先下拉显示框 */
        $('.searchPanle').css({
            left: leftV-10 + 'px',
            top: topV-7 + 'px'
        }).slideDown(300);
    
        //查询并填充信息到下拉中
        var searchInputhtml = '';
        var infolisturl = '<%=contextPath%>/KQDS_InformationAct/getInfoList.act?searchinput=' + searchinput;
        $.axse(infolisturl, null,
        function(data) {
            if (data.data.length > 0) {
                for (var i = 0; i < data.data.length; i++) {
                    var typestr = data.data[i].typename; //消息类型
                    var titleval = data.data[i].title; //标题
                    var contentval = stripHTML(data.data[i].content); //内容
                    if (typestr == "介绍") {
                        searchInputhtml = searchInputhtml + '<li style="border:1px solid #DDDDDD;border-top:none;" onclick="showinfo(this)" value="' + data.data[i].seqId + '"><label class="describeLabel">' + typestr + '</label><span class="text span1" style="font-size:16px;color:orange;">' + titleval + '</span><span class="text span2">' + contentval + '</span></div></li>';
                    } else if (typestr == "话语") {
                        searchInputhtml = searchInputhtml + '<li style="border:1px solid #DDDDDD;border-top:none;" onclick="showinfo(this)" value="' + data.data[i].seqId + '"><label class="languageLabel">' + typestr + '</label><span class="text span1" style="font-size:16px;color:orange;">' + titleval + '</span><span class="text span2">' + contentval + '</span></div></li>';
                    } else if (typestr == "其他") {
                        searchInputhtml = searchInputhtml + '<li style="border:1px solid #DDDDDD;border-top:none;" onclick="showinfo(this)" value="' + data.data[i].seqId + '"><label class="otherLable">' + typestr + '</label><span class="text span1" style="font-size:16px;color:orange;">' + titleval + '</span><span class="text span2">' + contentval + '</span></li>';
                    } else {
                        searchInputhtml = searchInputhtml + '<li style="border:1px solid #DDDDDD;border-top:none;" onclick="showinfo(this)" value="' + data.data[i].seqId + '"><label class="otherLable">' + typestr + '</label><span class="text span1" style="font-size:16px;color:orange;">' + titleval + '</span><span class="text span2">' + contentval + '</span></li>';
                    }
                }
            } else {
                searchInputhtml = '<span style="font-size:16px;">没有找到匹配的信息</span>';
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

        $('#infoul').html(searchInputhtml);
        /*调整span2的宽度 设置内容一行显示 */
        $("#infoul>li").each(function(index, li) {
            var liWidth = $(li).width();
            var span2Width = $(li).width() - $(li).find(".span1").width()-$(li).find(".otherLable").width();
            $(li).find(".span2").width(span2Width);
        });

        //搜索内容高亮显示
        if (searchinput.length > 0) {
            document.getElementById("infoul").innerHTML = document.getElementById("infoul").innerHTML.replace(new RegExp(searchinput, 'gm'), '<span style="font-size:16px;color:red;">' + searchinput + '</span>');
        }

    });
    function stripHTML(html) {
        var a = html.replace(/<\/?.+?>/g, "");
        var dds = a.replace(/ /g, ""); //dds为得到后的内容
        return dds;
    }
    //关闭面板
    $(document).on('click',
    function(e) {
    	
        if (e.target.id == 'closeInfoDiv') {
            $('.searchPanle').slideUp(300);
            return;
        }

        if ($(e.target).parents().hasClass('searchPanle') || $(e.target).hasClass('searchPanle') || $(e.target).hasClass('searchInput') || $(e.target).hasClass('searchblueBtn')) {
        	clearTimeout(timeOutOne);
        	//执行相应的li点击回调事件
            var leftV = $('.searchInput').offset().left,
            topV = $('.searchInput').offset().top + 32;
            $('.searchPanle').css({
                left: leftV-10 + 'px',
                top: topV-7 + 'px'
            }).slideDown(300);
            
            /* $('.searchPanle').slideDown(300); */
        } else {
            $('.searchPanle').slideUp(300);
        }
    });

    //计算页面高度
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
	
});

function encode(s) {
    return s.replace(/&/g, "&").replace(/</g, "<").replace(/>/g, ">").replace(/([\\\.\*\$\^])/g, "\\$1");
}
//根据id查询信息详情 ，用于信息搜索框
function showinfo(obj) {
    var id = $(obj).attr("value");
    layer.open({
        type: 2,
        title: '信息详情',
        shadeClose: true,
        shade: 0.6,
        area: ['720px', '80%'],
        content: '<%=contextPath%>/KQDS_InformationAct/toDetail.act?seqId=' + id
    });
}
//弹出在线人数
function openOnline() {
    layer.open({
        type: 2,
        title: '在线人员列表',
        shadeClose: true,
        shade: 0.6,
        offset: 'rt',
        area: ['400px', '500px'],
        content: '<%=contextPath%>/YZPersonAct/toParallelTree.act'
    });
}
//计算页面高度
function setHeight() {
    var baseH = $(window).outerHeight() - $('#header').outerHeight()-2;
    $('#mainWrap,.sideBar,.mainContainer').height(baseH);

    // 设置信息搜索框高度为自动计算，这样避免小屏幕显示框超出， -75是根据屏幕粗略设置的
    /* $('#infoul').height(baseH - 75); */
    $('#infoul').css("min-height", 400);
    $('#infoul').css("max-height", baseH - 75);

}


//修改密码
function editpass() {
    layer.open({
        type: 2,
        title: '修改密码',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath + '/YZPersonAct/toEditPwd.act'
    });
}

function doLogout() {
    layer.confirm('确定退出吗？', {
        btn: ['确定', '放弃'] //按钮
    },
    function() {
    	exitLogin();
    });
}

/**********************超出设定时间，自动退出功能代码 start *********************************************/

var onlineMsrgTimer = null;
var checkTimeout = 30; // 一分钟检查一次
/**
 * 检查用户在线状态，如果超时则退出
 */
function checkUserOnlineStatus() {
    if (onlineMsrgTimer) {
        clearTimeout(onlineMsrgTimer);
    }
    if (!checkTimeout) {
        return;
    }
    var rtJson = null; //处理服务器故障或者断网情况
    var url = contextPath + "/YZOnlineAct/checkOnline.act";
    $.axse(url, null,
    function(data) {
        if (data.retState == "0") {
        	rtJson = data;
        } else {
        	layer.alert("检测用户超时，请求失败"  );
        }
    },
    function() {
    	layer.alert("连接服务器失败，请联系管理员！", {
    		   
    		end : function(){
    			logout();
    	}});
    });
	// 轮训调用下一次检查
    onlineMsrgTimer = setTimeout(checkUserOnlineStatus, checkTimeout * 1000);
}

//退出登录
function exitLogin(){
	 var url = '<%=contextPath%>/YZSystemAct/doLogout.act';
     $.ajax({
         type: "GET",
         dataType: "text",
         url: url,
         success: function(text) {
        	 logout();
         }
     });
}

//退出系统
function logout(){
	var isapp = localStorage.getItem("isapp");
    var tmpParam = "";
    if (isapp == '1') {
        tmpParam = "?isapp=1"
    }
    localStorage.setItem("isapp", '0'); // 要清空才行
    window.location.replace(contextPath + "/login.jsp" + tmpParam);
}
/**********************超出设定时间，自动退出功能代码 end *********************************************/
//弹出/关闭用户当前状态选择框
$("#nowstate").click(function(e){
	$("#choiceState").toggle();
});
//选择状态后保存用户当前状态到数据库
$("#choiceState").on("click","li",function(e){
	var li=$(e.target).parent(".stateValue");
	var stateNumber=li.data("value");/*获得值  */
	
	$("#choiceState").toggle();
	changeOnlineState(stateNumber);//修改当前显示的图标
	setState(stateNumber);//向数据库写入当前的状态值
})
/* 设置当前状态对应的图标 */
function changeOnlineState(stateNumber){
	switch(Number(stateNumber)){
		case 0:
			$("#nowstate").html("空闲");
			$("#nowstate").data("value",0);
			break;
		case 1:
			$("#nowstate").html("忙碌");
			$("#nowstate").data("value",1);
			break;
		case 2:
			$("#nowstate").html("休息");
			$("#nowstate").data("value",2);
			break;
		case 3:
			$("#nowstate").html("离开");
			$("#nowstate").data("value",3);
			break;
	}
}
/*鼠标离开选择'在线状态区域'就关闭当前区域 */
$("#choiceState").on("mouseleave",function(){
	$("#choiceState").css("display","none"); 
});
/* 读取目前用户状态 */
function getUserStatus(){
	var param = "userid="+userId;
	var userline = getDataFromServer("YZOnlineAct/getUserStatus.act?"+param);
	/* $("#userstatus").val(userline.userState); */
	changeOnlineState(userline.userState); /* 修改页面当前状态对应的 图标 */
}
/* 向数据库里写入当前状态 */
function setState(stateNumber){
	var param = "userid="+userId+"&userstatus="+stateNumber;
	getDataFromServer("YZOnlineAct/changeStatus.act?"+param);
}
//密码修改的表单提交函数
function submitForm() {
    var newpwd1 = $("#newpwd1").val();
    var newpwd2 = $("#newpwd2").val();

    if(newpwd1 == ""){
    	layer.alert('新密码不能为空！' );
        return false;
    }
    if(newpwd1 == oldpwd){
    	layer.alert('新密码不能与旧密码一致！' );
        return false;
    }
    if(newpwd1 == "123456"){
    	 layer.alert('新密码不能与默认密码一致！' );
         return false;
    }
    if (newpwd1 != newpwd2) {
        layer.alert('两次输入的新密码不一致！' );
        return false;
    }
	
    // 赋值
    var param = $('#updataPasswordForm').serialize();
    console.log(param);
    var url = 'YZPersonAct/setPassword.act?' + param;
    var serverData = getDataFromServer(url);
    console.log(serverData);
    if (serverData) {
        layer.alert('密码设置成功', {
              
            end: function() {
               // if (parent.layer) {
                //    parent.layer.close(frameindex); //再执行关闭 */
               // }
               //清空修改密码弹框中的所有表单数据
            	clearInputValue("userMeassageDiv");
               //关闭修改密码的弹框
                $("#userMeassageDiv").toggle();
            }
        });
        
    }
}
//清空当前元素下 文本框和密码框     关闭密码修改框时 会用到这个函数
function clearInputValue(id){
	if(id!=""&id!=null){
		$("#"+id).find("input[type=text],input[type=password]").val("");
	}else{
		alert("请传入需要的清空的表单id值");
	}
}
function showOnlinePersonDiv(){/*在线人员展示   弹框的开关*/
	//更新在线人数状态值
	getOnlinePerson();
	//在线人员 
	$("#onlinePersonDiv").toggle();
}
function togglefold(obj){/* 在线人员展示弹框中    标题 折叠功能 */
	
	//蓝色按钮被点击或者“在线人员”标题被点击
	$(obj).toggleClass("active");/* 蓝色箭头的切换样式 */
	if($(obj).hasClass("active")){
		$(".personStateUl").hide();/* 关闭所有的部门 */
	}else{
		$(".personStateUl").show();/* 打开所有部门的展示 */
	}
	
}
function doInit() {  //初始化在线人员
	/* console.log("请求数据"); */
	$(".depateUl").html("");
    var onlineRef = 120;
    getOnlinePerson();
    setInterval(getOnlinePerson, onlineRef * 1000);
}
function getOnlinePerson(){//发送请求  请求在线人员的数据并展示
	 var data = getDataFromServer('YZOnlineAct/getOnlineTree.act');
	   /*  console.log(data); */
    if (data) {
    	deptStr = data.deptStr;
        $(".depateUl").html(data.retData);
    } else {
        alert(rtJson.rtMsrg);
    }
}

function Catalogue(){	
	 $(".sideBar .item").each(function(){
	       if($(this).text().trim()=="医疗中心"&&$(this).parent('li').hasClass("unfold")){
	       }else if($(this).text().trim()=="医疗中心"){
	    	 	//修改其他icon为灰色 
		       	$(this).parent('li').siblings('li').each(function(i,elem){
		       		if($(elem).hasClass("unfold")){
		       			var img2=$(elem).find("img");
			           		var str2=removeImgStr(img2.attr("src")); 
			           		img2.attr("src",str2);
		       		} 
		       	});
		        $(this).parent('li').toggleClass('unfold').siblings('li').removeClass('unfold');
		       	//根据当前状态 设置icon的颜色
		       	var img=$(this).parent("li").find("img");
		       	var str=img.attr("src");
		       	if($(this).parent("li").hasClass("unfold")){
		       		str=addImgStr(str);
		       	}else{
		       		str=removeImgStr(str);
		       	}
		       	img.attr("src",str);
	       }else{}
	    })
}
function secondLevelDirectory(){
	  var url = "";	    
    $(".sideBar .subChild li").each(function(){
     if($(this).attr("url")=="/base/ClinicPathControllerAct/toLcljInformation.act?menuId=600309"){
   	  $(this).parents('.slideMenu').find('li').removeClass('current');
         $(this).addClass('current');
         var arr = $(this).attr('url').split("?");
         for (var i = 0; i < arr.length; i++) {
             if (i == 0) {
                 url += arr[i] + "?";
             } else {
                 url += arr[i] + "&";
             }
         }
         url = url.substring(0, url.length - 1);
     }
    
     
   })
   $('.mainFrame').attr('src', url);
}
function removeImgStr(str){//设置灰色icon的文件名   
	var arr=str.split("-");
	str=arr.join("");
	
	return str
}
function addImgStr(str){//设置蓝色icon的文件名  
	if(!str){ // 如果没有配置图标，则不处理
		return "";
	}
	var arr=str.split(".");
	str=arr[0]+"-"+".png";
	
	return str
}
</script>
<%@ include file="push/pushBox.jsp"%>
<%
	String is_open_record_func = YZSysProps.getProp(SysParaUtil.IS_OPEN_RECORD_FUNC);
	if("1".equals(is_open_record_func)){ // 1开启  0 关闭
		%>
		<%@ include file="/telrecord/record4inc.jsp"%>
		<%
	}
%>
</html>
