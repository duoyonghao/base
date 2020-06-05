<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>图文管理</title>
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
	#table td span.newstitle{
		display:inline-block;
		width: 150px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow:ellipsis;
	}
	
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
	<section class="content listWrap">
		<div class="listHd">
			<span class="title">图文编辑</span>
		</div>
		<div style="float: left; width: 100%; margin-right: 1%;">
			<div id="toolbar">
			</div>
			<div class="tableBox">
				<table id="table" data-toolbar="#toolbar"></table>
			</div>
		</div>
	</section>
</div>

<script>
var pageurl = '<%=contextPath%>/WXNewsAct/selectPage.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var row_add=0,row_guanli=0,row_edit=0,row_del=0;
$(function() {
	//获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        sidePagination: "server",
        onLoadSuccess:function(data){
        	 setHeight();
        },
        columns: [{
            title: '排序号',
            field: 'sortno',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '图文分类',
            field: 'newstypename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '图文名称',
            field: 'newsname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '文章数量',
            field: 'itemcount',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '文章列表',
            field: 'itemcount',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	var itemListHtml = "";
            	$.each(row.itemlist,function(index){
            		if(index > 0){
            			itemListHtml += '</br>';
            		}
            		var itemObj = row.itemlist[index];
            		itemListHtml += '<a class="newstitle" style="cursor:pointer;" title="' + itemObj.title + '" onclick="showNewsItem(\''+itemObj.seqId+'\')">' + (index + 1) + '.' + itemObj.title + '</a>';
            	});
                return itemListHtml;
            }
        },
        {
            title: '文章管理',
            field: 'pkcode',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	var content = "";
            	if(row_add == 1){
            		content += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="addItem(\'' + row.seqId + '\')" style="color:red">新增</a> ';
            	}
            	if(row_guanli == 1){
            		content += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="showMessage(\'' + row.seqId + '\' )" style="color:red">管理</a> ';
            	}
                return content;
            }
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span  title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '图文管理',
            field: 'pkcode',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
            	var content = "";
            	if(row_edit == 1){
            		content += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')"><span style="color:red">编辑</span></a> ';
            	}
            	if(row_del == 1){
            		content += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
            	}
                return content;
            }
        }]
    });
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function() {
        setHeight();
    });
});

function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '350px'],
        content: '<%=contextPath%>/WXNewsAct/toEditNew.act?seqId=' + id
    });
}

function del(id) {
    //询问框
    layer.confirm('删除的同时，会将图文一并删除，确定要删除吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/WXNewsAct/deleteObj.act?seqId=' + id;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                    end: function() {
                        refresh();
                    }
                });
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}


function newAdd(){
	layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '350px'],
        content: '<%=contextPath%>/WXNewsAct/toAddNew.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
}

//弹出一个iframe层
function addItem(newsid) {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增图文信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['95%', '95%'],
        content: '<%=contextPath %>/WXNewsItemAct/toAddNew.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>&newsid=' + newsid
    });
}

//弹出一个iframe层
function showMessage(newsid) {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：图文信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['95%', '95%'],
        content: '<%=contextPath %>/WXNewsItemAct/toShowMessage.act?newsid=' + newsid
    });
}


function showNewsItem(newsItemSeqId) {
    var url = contextPath + '/WXNewsItemAct/toDetail.act?seqId=' + newsItemSeqId;
    layer.open({
        type: 2,
        title: '图文详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['80%', '80%'],
        content: url
    });
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "twbj_add") {
        	menubutton1 += '<button id="parentIframe" class="kqdsSearchBtn" onclick="newAdd()"><i class="glyphicon glyphicon-plus"></i> 新增</button>';
        }else if (listbutton[i].qxName == "row_add") {
        	row_add = 1;
        }else if (listbutton[i].qxName == "row_guanli") {
        	row_guanli = 1;
        }else if (listbutton[i].qxName == "row_edit") {
        	row_edit = 1;
        }else if (listbutton[i].qxName == "row_del") {
        	row_del = 1;
        }
    }
    $("#toolbar").append(menubutton1);
   
}
function setHeight(){
	$("#table").bootstrapTable('resetView',{
		height:$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()+40
	});
	/* var tableHeight=0;
	tableHeight=$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()-$(".listHd").outerHeight()-15;
	$(".fixed-table-container").outerHeight(tableHeight); */
}

</script>

</body>
</html>
