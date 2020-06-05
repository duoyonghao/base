<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>加工厂列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<section class="content">
		<div class="row">
			<div class="col-sm-12">
				<div id="toolbar">
					<button id="parentIframe" class="kqdsSearchBtn bigBtn">
						<i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					加工厂编码：<input type="text" name="code" id="code" >
					加工厂名称：<input type="text" name="name" id="name" >
					<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
				</div>
				<div class="tableBox">
					<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
				</div>
					
			</div>
		</div>
	</section>
<script>
var pageurl = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/selectPage.act';
var $table = $('#table');
$(function() {
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        },
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            title: '加工厂编码',
            field: 'code',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '名称',
            field: 'name',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '地址',
            field: 'address',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '联系电话',
            field: 'phone',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
            	if (row.useflag == '1') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING_UNIT\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_OUTPROCESSING_UNIT\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
                }
            	
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\');"><span style="color:red">删除</span></a>';
            	return menubutton;
            }
        }]
    });
    /* 页面重置，重新设置table的高度 */
    $(window).resize(function(){
		setHeight(); 
	});
});
function queryParams(params) {
var temp = { 
   		offset : params.offset,
       	limit :	 params.limit,
        organization: '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        code: $('#code').val(),
        name: $('#name').val()
    };
    return temp;
}
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}


function del(seqId) {
    //询问框
    layer.confirm('<span style="color:red;">将同时删除加工分类及加工项目</span>，确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_OutProcessing_UnitBackAct/deleteObj.act?seqId=' + seqId;
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

function edit(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改加工厂',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '320px'],
        content: contextPath+'/KQDS_OutProcessing_UnitBackAct/toEdit.act?seqId=' + id
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：加工厂详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '260px'],
        content: contextPath+'/KQDS_OutProcessing_UnitBackAct/toDetail.act?seqId=' + id
    });
}

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增加工厂',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['600px', '320px'],
        content: contextPath+'/KQDS_OutProcessing_UnitBackAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
  
 
</script>



</body>
</html>
