<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>打印设置管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">

<section class="content">
	<div>
		<div id="toolbar" >
			<button id="parentIframe" class="kqdsSearchBtn bigBtn">
				<i class="glyphicon glyphicon-plus"></i> 新增
			</button>
		</div>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-show-toggle="true"
				data-show-columns="true" data-show-export="true" data-height="550">
			</table>
		</div>
		
	</div>
</section>
<script>
var organization = '<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var pageurl = '<%=contextPath%>/KQDS_Print_SetBackAct/selectPage.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var $table = $('#table');
$(function() {
	/*初始化计算table的高度 */
    var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-show-toggle="true" data-show-columns="true" data-show-export="true" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            title: '排序号',
            field: 'sortno',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '打印名称',
            field: 'printname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '打印类型',
            field: 'printtype',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value == "1") {
                    return 'A4';
                } else {
                    return 'A5';
                }
            }
        },
        {
            title: '打印地址',
            field: 'printurl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
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
            title: '创建人',
            field: 'createusername',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
                var e = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                var d = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                if(organization){
                	return e + d; 
                }else{
                	return e;  // 公共的不允许删除
                }
                // var x = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\''+ row.seqId +'\')">详情</a> ';
                
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改打印设置',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '260px'],
        content: contextPath+'/KQDS_Print_SetAct/toEdit.act?seqId=' + id
    });
}

function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：打印设置详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '260px'],
        content: contextPath+'/KQDS_Print_SetAct/toDetail.act?seqId=' + id
    });
}

function del(id) {
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_Print_SetBackAct/deleteObj.act?seqId=' + id;
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

//弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增打印设置',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['500px', '260px'],
        content: contextPath+'/KQDS_Print_SetAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
 
</script>

</body>
</html>
