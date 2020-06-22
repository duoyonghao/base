<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>排班类型管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div id="toolbar">
				<button id="parentIframe" class="btn btn-primary">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button> 
			</div>
			<div class="tableBox">
				<table id="table" data-toolbar="#toolbar" data-show-toggle="false"
				data-show-columns="false" data-show-export="true" data-height="550"></table>
			</div>
		</div>
	</div>
</section>
<script>
var pageurl = '<%=contextPath%>/KQDS_Paiban_TypeBackAct/selectPage.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var $table = $('#table');
$(function() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess:function(){
        	setHeight();
        },
        //服务端处理分页
        columns: [{
            title: '排班类型',
            field: 'typename',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '上班时间',
            field: 'startdate',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '下班时间',
            field: 'enddate',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var e = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                // var d = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                var x = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
                return e + x; // + d 
            }
        }]
    });
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function(){
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改排班类型',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '240px'],
        content: contextPath+'/KQDS_Paiban_TypeAct/toPaibanTypeEdit.act?seqId=' + id
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：排班类型详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '240px'],
        content: contextPath+'/KQDS_Paiban_TypeAct/toPaibanTypeDetail.act?seqId=' + id
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_Paiban_TypeBackAct/deleteObj.act?seqId=' + id;
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增排班类型',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '240px'],
        content: contextPath+'/KQDS_Paiban_TypeAct/toPaibanTypeAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight();
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
}
</script>



</body>
</html>
