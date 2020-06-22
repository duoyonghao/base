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
<title>模板消息管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/dict/dict.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/weixinModule.css" />
<style>

	.fixed-table-toolbar{
		overflow:hidden;
	}
	.bs-bars {
		width:100%;
	}
	
	.fixed-table-container thead th .sortable{
		padding-right:8px;	
	}
	.fixed-table-toolbar .bs-bars, .fixed-table-toolbar .search, .fixed-table-toolbar .columns{
		margin:5px 0;
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
			<span class="title">模板消息</span>
		</div>
		<div style="float: left; width: 100%; margin-right: 1%;">
			<div id="toolbar">
			</div>
			<div class="tableBox">
				<table id="table" data-toolbar="#toolbar"></table>
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
var pageUrl = "WXTemplateItemAct/selectPage4Manage.act?1=1";
$(function() {
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    getPageList();
    $(window).resize(function() {
        setHeight();
    });
});


function newAdd() {
    var index = layer.open({
        type: 2,
        title: '新增模板消息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '90%'],
        content: contextPath + '/WXTemplateItemAct/toIndex2.act'
    });
}

function edit(seqId,templateSeqid,templateId) {
    var index = layer.open({
        type: 2,
        title: '修改模板消息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '90%'],
        content: contextPath + '/WXTemplateItemAct/toNewEdit.act?seqId=' + seqId + '&templateSeqid=' + templateSeqid + '&templateId=' + templateId
    });
}

function del(seqId) {
    //询问框
    layer.confirm('确定要删除该模板消息吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
    	var param = {
   			seqId:seqId
    	};
        var url = "WXTemplateItemAct/deleteObj.act?1=1";
        var rtData = getDataFromServer(url, param);
    	if(rtData){
    		layer.alert('删除成功', {
                  
                end: function() {
                    refresh();
                }
            });
    	}else{
    		layer.alert('删除失败！'  );
    	}
    });
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset
    };
    return temp;
}
function setHeight() {
	$("#table").bootstrapTable("resetView",{
		height:$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()+5
	});
	/* var tableHeight=0;
	tableHeight=$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()-$(".listHd").outerHeight()-45;
	$(".fixed-table-container").outerHeight(tableHeight); */
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
            title: '标题',
            field: 'first',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span style="300px;float:left;text-align:left;position:relative;top:-1px;">'+value+'</span>';
            }
        },
        {
            title: '关键字1',
            field: 'keyword1',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '关键字2',
            field: 'keyword2',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '关键字3',
            field: 'keyword3',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '关键字4',
            field: 'keyword4',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '关键字5',
            field: 'keyword5',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
           
            sortable: true,
            editable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '操作',
            field: ' ',
            align: 'center',
           
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<span><a href="javascript:void(0);" style="color:#00A6C0;curror:point;" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\',\'' + row.templateSeqid + '\',\'' + row.templateId + '\')">编辑</a>';
                menubutton += '<a href="javascript:void(0);" style="color:red;curror:point;" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')">删除</a> </span>';
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
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if(listbutton[i].qxName == "mbxx_add"){
        	menubutton1 += '<button id="btn_export" onclick="newAdd()" type="button" class="kqdsSearchBtn" style="float: left;">新增</button>';
        }else if (listbutton[i].qxName == "mbxx_scbb") {
        	menubutton1 += '<button id="btn_export" onclick="exportTable()" type="button" class="kqdsCommonBtn" style="float: left; margin-left:5px;">生成报表</button>';
        }
    }
    $("#toolbar").append(menubutton1);
   
}
</script>
</body>
</html>
