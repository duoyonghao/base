<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>加工项目分类列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqds/outProcessingType/outprocessingtype.js"></script>
</head>
<style type="text/css">
	.leftDiv{
		width:150px;
		float:left;
		height:100%;
		/* background:beige; */
		border:1px solid #ddd;
		overflow-y:auto;
	}
	.rightDiv{
		margin-left:160px;
		/* background:deepskyblue; */
		height:100%;
		padding-right:10px;
		overflow-y:auto;
	}
	.kqdsSearchBtn{
		padding:0 10px;
	}
</style>
<body class="hold-transition skin-blue sidebar-mini">
	<section class="content">
		<div>
			<div id="toolbar">
				<button id="parentIframe" class="kqdsSearchBtn bigBtn">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<button onclick="batchDelete()" class="kqdsSearchBtn bigBtn" >
					<i class="glyphicon glyphicon-remove"></i> 批量删除
				</button>
				<span class="commonText" >加工厂：</span> <select name="mrjgc" id="mrjgc"></select>
				<span class="commonText" >加工分类：</span><select name="wjgfl" id="wjgfl"> </select>
				<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="margin:0 0 0 5px;">
				 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
				</button>
			</div>
			<div class="leftDiv">
				<div id="treeDemo" class="ztree" style="width:100%;"></div>
			</div>
   			<div class="rightDiv">
				<div class="tableBox">
					<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
				</div>
			</div>
		</div>
	</section>
<script>
var organization = '<%=organization %>';
var pageurl = '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/selectPage.act';
$(function() {
	initFactorySelect4Back('mrjgc','',organization);
	$('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        toolbar: '#toolbar',
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	setHeight();
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [
		{field: ' ',checkbox: true,formatter: stateFormatter},
        {
            title: '项目分类名称',
            field: 'typename',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '项目分类编号',
            field: 'typeno',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '加工厂',
            field: 'factoryname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '启用/禁用',
            field: 'useflag',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value == 0) {
                    return '<span style="color:green">已启用</span>';
                } else {
                    return '<span style="color:red">已禁用</span>';
                }
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
            	if (row.useflag == '1') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING_TYPE\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING_TYPE\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
                }
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\');"><span style="color:red">删除</span></a>';
                
            	return menubutton;
            }
        }]
    });
	zTreeInit();
	/* 页面重置，重新设置table的高度 */
    $(window).resize(function() {
        setHeight();
    });
});
//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    $('.leftDiv').height(baseHeight - 40);
    $("#treeDemo").height(baseHeight - 50);
}
function queryParams(params) {
    var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: organization,
        mrjgc: $('#mrjgc').val(),
        wjgfl: $('#wjgfl').val()
    };
    return temp;
}
$('#mrjgc').change(function(){
	getTypeList("wjgfl",this.value);
});
function refresh() {
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改加工项目分类',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '280px'],
        content: contextPath+'/KQDS_OutProcessing_TypeBackAct/toEdit.act?seqId=' + id + '&organization='+organization
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：加工项目分类详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '240px'],
        content: contextPath+'/KQDS_OutProcessing_TypeBackAct/toDetail.act?seqId=' + id + '&organization='+organization
    });
}

/**
 * 批量删除
 */
function batchDelete() {
	var selectRows = getIdSelections();
	var seqId = "";
	$.each(selectRows,function(index){
		seqId += selectRows[index].seqId + ",";
	});
	
	if(!seqId){
		layer.alert('请选择要删除的加工分类' );
		return false;
	}
	
	del(seqId);
}

function del(id) {
    //询问框
	layer.confirm('<span style="color:red;">将同时删除分类下的加工项目</span>，确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_OutProcessing_TypeBackAct/deleteObj.act?seqId=' + id;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, null,
        function(data) {
        	if (data.retState == "0") {
            	var msg = '删除成功';
            	if(data.retMsrg){
            		msg = data.retMsrg;
            	}
                layer.alert(msg, {
                      
                });
                refresh();
            }else{
            	layer.alert('删除失败：' + data.retMsrg  );
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增加工项目分类',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '280px'],
        content: contextPath+'/KQDS_OutProcessing_TypeBackAct/toNewAdd.act?organization='+organization
    });
});

//复选框
function stateFormatter(value, row, index) {
    return value;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
function zTreeInit() {
    //异步加载
    var url = contextPath + '/YZDictJGAct/getJgTreeAsync.act';

    var setting = {
        view: {
            showIcon: false // 去掉图标
        },
        async: {
            enable: true,
            url: url,
            autoParam: ["id", "name=n", "level=lv"],
            otherParam: {
                "otherParam": "zTreeAsyncTest",
                "organization": organization
            },
            dataFilter: ajaxDataFilter,
            type: "get"
        },
        callback:{  
        	onClick: onclick4Tree,
        	onAsyncSuccess : zTreeOnAsyncSuccess//异步加载成功的fun    
        }  
    };
    $.fn.zTree.init($("#treeDemo"), setting);
}
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
	setHeight();
}
function onclick4Tree(e, treeId, treeNode) {
    var url = "";
    if (treeNode.level == 0) { // 一级分类
    	$("#mrjgc").val(treeNode.id);
    	$("#mrjgc").trigger("change");
    	$("#wjgfl").val("");
    } else { // 二级分类
    	$("#mrjgc").val(treeNode.pId);
    	$("#mrjgc").trigger("change");
    	$("#wjgfl").val(treeNode.id);
	}

    refresh(); // 执行查询

}
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight();
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>



</body>
</html>
