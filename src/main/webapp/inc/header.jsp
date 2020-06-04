<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//图片上传地址
	String UPLOAD_URL = YZSysProps.getProp(SysParaUtil.UPLOAD_URL);
	if(UPLOAD_URL == null){
		UPLOAD_URL = "";
	}
	//服务器路径
	String realurl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath + "/";
%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/datepicker/datepicker3.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrap-table/bootstrap-table.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/bootstrapvalidator/dist/bootstrapValidator.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<style type="text/css">
<!--
/* 重定义 bootstrap.css 样式 */
.form-horizontal .form-group {  /* 解决后台可见人员，新增弹框横向滚动条的问题 */
    margin-right: 5px;
    margin-left: 5px;
    margin-top: 10px;
}


.row {	/* 解决后台可见人员列表横向滚动条的问题 */
    margin-right: 5px;
    margin-left: 5px;
}

.box-footer {	/* 解决后台可见人员列表横向滚动条的问题 */
    margin-right: 5px;
    margin-left: 5px;
}

/* 重定义 bootstrap.css 样式  end   yangsen  17-5-4 */
-->
</style>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
//获取页面打开凡是 isapp=1  chrome app的方式打开	 
var isapp = localStorage.getItem("isapp");
if (isapp == "1") {
    // 禁用右键菜单
    document.oncontextmenu = DisableRightClick;
    function DisableRightClick() {
        return false;
    }
}
/** 变量定义 **/
var contextPath = "<%=contextPath%>";
var realurl = "<%=realurl%>";
var UPLOAD_URL = "<%=UPLOAD_URL%>";
/*##################################### 连锁相关代码 ########################################*/

/**
 * 根据id 初始化门诊下拉框，用于core/codeclass 和 kqds/codeclass页面的管理维护
 * @param id
 */
function initHosSelectList4CodeClassManage(selectId, Val) {
    var url = "ChainAct/getHosList.act";
    var rtJson =  getDataFromServer(url);
    var selects = document.getElementById(selectId);
    if (rtJson.retState == "0") {
        list = rtJson.list;
        for (var i = 0; i < list.length; i++) {
            var hos = list[i];
            var option = document.createElement("option");
            option.value = hos.deptCode;
            option.innerHTML = hos.deptName;
            if (Val && Val == hos.deptCode) {
                option.selected = true;
            }
            selects.appendChild(option);
        }
    }
}

/**
 * 根据id 初始化门诊下拉框，用于用户登录
 * @param id
 */
function initHosSelectList4Login(selectId, Val) {
    var url = "ChainAct/getHosList.act?nocheck=nocheck";
    var rtJson = getDataFromServer(url);
    var selects = document.getElementById(selectId);
    if (rtJson.retState == "0") {
        list = rtJson.list;
        for (var i = 0; i < list.length; i++) {
            var hos = list[i];
            var option = document.createElement("option");
            option.value = hos.deptCode;
            option.innerHTML = hos.deptName;
            if (Val && Val == hos.deptCode) {
                option.selected = true;
            }
            selects.appendChild(option);
        }
    }
}
</script>
<link rel="shortcut icon" href="<%=contextPath%>/static/image/image/icon/favicon.ico" mce_href="<%=contextPath%>/static/image/image/icon/favicon.ico" type="image/x-icon">
