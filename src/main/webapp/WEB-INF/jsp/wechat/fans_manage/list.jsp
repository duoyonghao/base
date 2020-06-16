<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>粉丝管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dict.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<style>

	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin:5px 0;
	}
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
	.fixed-table-header{
		border-top-left-radius:6px;
		border-top-right-radius:6px;
	}
	.fixed-table-body{
		border-bottom-left-radius:6px;
		border-bottom-right-radius:6px;
		border-bottom:1px solid #ddd;
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
	}
</style>
</head>
<body>
<div class="main">
	<div class="content listWrap">
	<div class="listHd">
		<span class="title">粉丝管理</span>
	</div>
	<div style="float: left; width: 100%; margin-right: 1%;">
		<div id="toolbar">
		</div>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-height="500"></table>
		</div>
	</div>
</div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script>
var pageUrl = "WXFansAct/selectPage4Manage.act?1=1";
$(function() {
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    getPageList();
    $(window).resize(function() {
        setHeight();
    });
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset
    };
    return temp;
}
function setHeight() {
	var tableHeight=0;
	tableHeight=$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()-$(".listHd").outerHeight()-55;
	$(".fixed-table-container").outerHeight(tableHeight);
}

function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var params = "";
    location.href = contextPath + "/" + pageUrl + "&flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr)+params;
}

function getPageList() {
   
    $("#table").bootstrapTable({
        url: contextPath + "/" + pageUrl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        sidePagination: "server",
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [
        //服务端处理分页
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
           
            visible: false,
            switchable: false
        },
        {
            title: '昵称',
            field: 'nickname',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '省份',
            field: 'province',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '城市',
            field: 'city',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '当前状态',
            field: 'carestatus',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	if("0" == value){
            		return "正常";
            	}
            	if("1" == value){
            		return "<span style='color:red;'>取消关注</span>";
            	}
            }
        },
        {
            title: '关注时间',
            field: 'bindtime',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '取消关注时间',
            field: 'unbindtime',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	return "<span style='color:red;'>"+value+"</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '电话',
            field: ' ',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	if(row.phonenumber1){
            		return '<span>'+row.phonenumber1+'</span>';
            	}else{
            		return '<span>'+row.phonenumber2+'</span>';
            	}
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
           
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="updateBaseinfo(\'' + row.openid + '\')">资料更新</a> ';
                
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="talkhislist(\'' + row.openid + '\')">聊天记录</a></span> ';
                return menubutton;
            }
        }]
    });
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': contextPath + "/" + pageUrl
    });
}


function updateBaseinfo(openid){
	var param = {
		openid:openid
	};
	var json = getDataFromServer("WXFansAct/updateBaseInfoByOpenid.act",param);
	if(json && json.retState == 0){
		layer.alert('信息更新成功', {
              
            end: function(){
            	refresh();
            }
        });
	}else{
		layer.alert('信息更新失败' );
	}
}

/**
 * 聊天记录
 */
function talkhislist(openid) {
	layer.open({
        type: 2,
        title: '微信聊天记录',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['80%', '80%'],
        content: contextPath + '/wechat/chat/talkhislist.html?openid=' + openid
    });
}

function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "fans_scbb") {
        	menubutton1 += '<button id="btn_export" onclick="exportTable()" type="button" class="kqdsSearchBtn" style="float: right;">生成报表</button>';
        }
    }
    $("#toolbar").append(menubutton1);
  
}
</script>
</body>
</html>
