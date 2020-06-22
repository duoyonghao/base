<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="shortcut icon" href="<%=contextPath%>/static/image/image/icon/favicon.ico" mce_href="<%=contextPath%>/static/image/image/icon/favicon.ico" type="image/x-icon">
<title>UNIC管理系统后台管理系统</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrap/dist/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/Ionicons/ionicons.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/dict/AdminLTE.min.css">
<!-- AdminLTE Skins. Choose a skin from the css/skins
     folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/dict/skins/_all-skins.min.css">
<!-- Morris chart -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/morris.js/morris.css">
<!-- jvectormap -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/jvectormap/jquery-jvectormap.css">
<!-- Date Picker -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrap-datepicker/dist/bootstrap-datepicker.min.css">
<!-- Daterange picker -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrap-daterangepicker/daterangepicker.css">
<!-- bootstrap wysihtml5 - text editor -->
<link rel="stylesheet" href="<%=contextPath%>/static/css/admin/index/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
<style type="text/css">
  .nohc-scroll-webkit::-webkit-scrollbar
	{
	    width: 0px;
	    height: 6px;
	}
  .content-wrapper{
  	background:#fff;
  }
  .contentDiv{
  	margin-left:230px;
  }
  .navbar-nav>.user-menu>.dropdown-menu>li.user-header{
  	height:118px;
  }
  #roleUl{
  	
  }
  .main-sidebar{
  	width:148px;
  }
  .contentDiv{
  	margin-left:148px;
  }
  .main-header .logo{
  
    padding: 5px 15px 0 8px;
    text-align: left;
  }
  .skin-blue-light .main-header .navbar{
  	background:#00a6c0;
  }
  .skin-blue-light .main-header .logo{
    background:#00a6c0;
  }
  .skin-blue-light .main-header .logo-lg{
	display: block;
    float: left;
    width: 145px;
    height: 34px;
    background: url(<%=contextPath%>/static/image/kqdsFront/img/iconLvUp.png) no-repeat -135px -22px;
    cursor: pointer;
    margin-top: 3px;
  }
</style>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>
<body class="hold-transition skin-blue-light sidebar-mini">
<div class="wrapperDiv">

  <header class="main-header">
    <!-- Logo -->
    <a href="javascript:void(0);" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>KQDS</b></span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b></b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <!-- <a href="javascript:void(0);" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a> -->

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
        	<li>
	        	<a href="javascript:void(0);" onclick="goFrontIndex();" title="返回前台首页">
	              <i class="fa fa-arrow-left"></i>
	            </a>
            </li>
  
          <!-- User Account: style can be found in dropdown.less -->
          <li id="roleDiv" class="dropdown user user-menu">
            <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
              <img src="<%=contextPath%>/static/image/index/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><%=person.getUserName()%></span>
            </a>
            <ul id="roleUl" style="right:-78px;" class="dropdown-menu">
              <!-- User image -->
              <li style="width:200px;" class="user-header">
                <img src="<%=contextPath%>/static/image/index/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
				<!-- 
                <p>
                  Alexander Pierce - Web Developer
                  <small>Member since Nov. 2012</small>
                </p> -->
              </li>
              <!-- 
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="javascript:void(0);">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="javascript:void(0);">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="javascript:void(0);">Friends</a>
                  </div>
                </div>
              </li>-->
              <!-- Menu Footer-->
              <li style="width:200px;" class="user-footer">
                <div class="pull-left">
                  <a href="javascript:void(0);" onclick="setPassword();" class="btn btn-default btn-flat">修改密码</a>
                </div>
                <div class="pull-right">
                  <a href="javascript:void(0);" onclick="doLogoutMsg();" class="btn btn-default btn-flat">注销登录</a>
                </div>
              </li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
<!--      <li>
            <a href="javascript:void(0);" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li> -->
        </ul>
      </div>
    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul id="treeItem" class="sidebar-menu nohc-scroll-webkit" data-widget="tree" style="overflow-y: auto;"><!-- 使左侧菜单在自己的Url中滚动 -->
      	<c:forEach items="${menuList}" var="menu1" varStatus="status1"> 
			<c:choose>
				<c:when test="${fn:contains(menu1.menuname,'#')}">
				</c:when>
				<c:otherwise>
					<li class="<c:if test="${status1.index == 1}">active</c:if> treeview">
			          <a href="javascript:void(0);">
			            <i class="fa fa-dashboard"></i> <span>${menu1.menuname}</span><!-- 二级菜单 -->
			            <span class="pull-right-container">
			              <i class="fa fa-angle-left pull-right"></i>
			            </span>
			          </a>
			          <ul class="treeview-menu">
			          	<c:forEach items="${menu1.hasmenu}" var="menu2" varStatus="status">
			          		 <li><a target="menuFrame" href="<%=contextPath %>${menu2.url}?menuId=${menu2.menuid}"><i class="fa fa-circle-o"></i>${menu2.menuname}</a></li><!-- 三级菜单 -->
			          	</c:forEach>
			          </ul>
			        </li>
				</c:otherwise>  
			</c:choose>
      	</c:forEach>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content --><!-- 右侧内容区域 -->
  <div class="contentDiv" >
    <iframe id="menuFrame" name="menuFrame" src="<%=contextPath%>/YZMenuAct/toWelcome.act" style="overflow:visible;width:100%;" scrolling="yes" frameborder="no"></iframe>
  </div>
  <!-- /.content-wrapper -->

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content"  style="overflow-y: auto;">
      <!-- Home tab content -->
      <div class="tab-pane" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-user bg-yellow"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                <p>New phone +1(800)555-1234</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                <p>nora@example.com</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-file-code-o bg-green"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                <p>Execution time 5 seconds</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="label label-danger pull-right">70%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Update Resume
                <span class="label label-success pull-right">95%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Laravel Integration
                <span class="label label-warning pull-right">50%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Back End Framework
                <span class="label label-primary pull-right">68%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Allow mail redirect
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Other sets of options are available
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Expose author name in posts
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Allow the user to show his name in blog posts
            </p>
          </div>
          <!-- /.form-group -->

          <h3 class="control-sidebar-heading">Chat Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Show me as online
              <input type="checkbox" class="pull-right" checked>
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Turn off notifications
              <input type="checkbox" class="pull-right">
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Delete chat history
              <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
            </label>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/jquery/dist/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/bootstrap/bootstrap.min.js"></script>
<!-- Morris.js charts -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/raphael/raphael.min.js"></script>
<script src="<%=contextPath%>/static/js/admin/index/bower_components/morris.js/morris.min.js"></script>
<!-- Sparkline -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
<!-- jvectormap -->
<script src="<%=contextPath%>/static/js/admin/index/plugins/jvectormap/jquery-jvectormap-2.0.3.min.js"></script>
<script src="<%=contextPath%>/static/js/admin/index/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
<!-- jQuery Knob Chart -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-knob/dist/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/moment/min/moment.min.js"></script>
<script src="<%=contextPath%>/static/js/admin/index/bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/bootstrap-datepicker/dist/bootstrap-datepicker.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<%=contextPath%>/static/js/admin/index/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
<!-- Slimscroll -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=contextPath%>/static/js/admin/index/bower_components/fastclick/fastclick.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>

<!-- AdminLTE App -->
<script src="<%=contextPath%>/static/js/admin/index/dict/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="<%=contextPath%>/static/js/admin/index/dict/demo.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
$(function(){
	
	// 检查用户是否超时
	checkUserOnlineStatus();
	
	//获取页面打开凡是 isapp=1  chrome app的方式打开	 
	var isapp = localStorage.getItem("isapp")
	if(isapp=="1"){
		 //禁用右键菜单
		document.oncontextmenu = DisableRightClick;
	    function DisableRightClick() { 
	    	return false; 
	    }
	}
	
	//计算页面高度
    setHeight();
    $(window).resize(function () {
        setHeight();
    });
    /* 鼠标离开个人面板时，关闭个人面板 */
	$("#roleUl").on("mouseleave",closeRoleDiv);
});

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

/*鼠标离开的时候关闭右上角的个人面板  */
function closeRoleDiv(){
	$("#roleDiv").removeClass("open");
}
function setPassword(){
	layer.open({
		  type: 2,
		  title: '修改密码',
		  maxmin: true,
		  shadeClose: true, //点击遮罩关闭层
		  area : ['800px' , '520px'],
		  content: '<%=contextPath%>/YZPersonAct/toEditPwd.act'
	 });
}
/**
 * 弹出询问窗口的注销
 */
function doLogoutMsg() {
  if (confirm("确定退出系统吗？")) {
	  logout();
  }
}

/**
 * 无询问窗口直接注销
 * @return
 */
function logout() {
	var url = '<%=contextPath%>/YZSystemAct/doLogout.act';
	$.ajax({
		type: "GET",
		dataType: "text",
		url: url,
		success: function(text){
			var isapp = localStorage.getItem("isapp");
			// 要清空才行
			localStorage.setItem("isapp", '0');
			// 跳转
			if(isapp == "1"){
				// 处理回退按钮
				window.location.replace("<%=contextPath%>/login.jsp?isapp=1");
			}else{
				// 处理回退按钮
				window.location.replace("<%=contextPath%>/login.jsp");
			}
		}
	});
}


function setHeight() {
    var baseH1 = $(window).height()- $('.main-header').outerHeight() - 135;
    $('.sidebar-menu').height(baseH1);
    
    var baseH2 = $(window).height()- $('.main-header').outerHeight() - 115;
    $('.tab-content').height(baseH2);
    
    /* var baseH3 = $(window).height()- $('.main-header').outerHeight() - $('.main-footer').outerHeight() - 10;
	$("#menuFrame").height(baseH3); */
	/* var menuFrameHeight=$(window).height()-81;  *//* 减去头50 减去footer 31 */
	/* $("#menuFrame").height(menuFrameHeight); */	/* 设置内容 menuFrame与他的父元素 .content-wrapper一样高 */
	/* $(".content-wrapper").height(menuFrameHeight); */
	
	var baseH3 = $(window).height()- $('.main-header').outerHeight()-4;
	$("#treeItem").outerHeight('100%');
	$("#menuFrame").outerHeight(baseH3);
	$(".contentDiv").outerHeight(baseH3);
	
}

//返回前端使用界面
function goFrontIndex(){
	//获取页面打开凡是 isapp=1  chrome app的方式打开	 
	var isapp = localStorage.getItem("isapp");
	
	if(isapp == "1"){
		window.open("<%=contextPath%>/YZSystemAct/loginIndexFront.act", "_self");
	}else{
		window.open("<%=contextPath%>/YZSystemAct/loginIndexFront.act", "_blank");
	}
}

</script>
</body>
</html>
