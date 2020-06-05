<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<title>信息库管理</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div id="toolbar">
				<!--dom结构部分-->
				<div class="child-inline-block-middle" id="uploader-demo">
					<!--用来存放item-->
				    <input type="hidden" placeholder="" id="imgtype" name="imgtype" value="information">
				    <div id="fileList" class="uploader-list"></div>
					<button id="parentIframe" class="kqdsSearchBtn bigBtn">
						<i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<button onclick="mbxz()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-circle-arrow-down"></i> 导入模板
					</button>
					<a id="filePicker"  class="kqdsSearchBtn bigBtn" style="color: #fff;vertical-align:middle;top:-3px;"><i class="glyphicon glyphicon-save"></i> 导入</a>
					<span class="commonText">分类：</span><select class="dict" tig="XXKFL" name="type" id="type"></select>
					<span class="commonText">标题：</span><input type="text" name="title" id="title" >
					<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
			</div>
		</div>
	</div>

</section>
<script>
var pageurl = '<%=contextPath%>/KQDS_InformationBackAct/selectPage.act';
$(function() {
	initDictSelectByClassOrg('<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>');
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=evidence&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>");
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered"  data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            title: '标题',
            field: 'title',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
           	 if (value) {
                    return '<span style="float:left; text-align:left;" title="'+value+'">'+value+'</span>';
                }
           }
        },
        {
            title: '信息分类',
            field: 'type',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
            	 if (value) {
                     return value;
                 }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var menubutton = "";
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">修改</a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return menubutton;
            }
        }]
    });
	$(window).resize(function(){
		setHeight(); 
	});
});
function queryParams(params) {
    var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        type: $('#type').val(),
        title: $('#title').val()
    };
    return temp;
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}
function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改信息库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath +'/KQDS_InformationBackAct/toEdit.act?seqId=' + id
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：信息库详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath +'/KQDS_InformationBackAct/toDetail.act?seqId=' + id
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_InformationBackAct/deleteObj.act?seqId=' + id;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('删除成功', {
                      
                });
                refresh();
            }
        },
        function() {
            layer.alert('删除失败！'  );
        });
    });
}

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        offset: ['10px', '200px'],
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增信息库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath +'/KQDS_InformationBackAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
//模板下载
function mbxz() {
    location.href = contextPath + "/KQDS_InformationBackAct/excelStandardTemplateOut.act";
}
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight()-35;
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>
</body>
</html>
