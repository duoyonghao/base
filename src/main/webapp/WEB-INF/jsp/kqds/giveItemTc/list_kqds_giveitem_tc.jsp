<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<title>赠送套餐管理</title>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<section class="content">
		<div class="row">
			<div class="col-sm-12">
				<div id="toolbar">
					<button id="parentIframe" class="kqdsSearchBtn bigBtn">
						<i class="glyphicon glyphicon-plus"></i> 新增
					</button>
					<span class="commonText">套餐名称：</span><input type="text" name="name" id="name" >
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
<script type="text/javascript">
var pageurl = '<%=contextPath%>/KQDS_GiveItem_TCBackAct/selectPage.act';
var $table = $('#table');
var tcnme = "";
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
        sidePagination: "server",
        //服务端处理分页
        columns: [
        {
            title: '套餐名称',
            field: 'name',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
        },
        {
            title: '创建人',
            field: 'createuser',
            align: 'center',
            sortable: true,
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
            	menubutton +=  '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\',\'' + row.name + '\',\'' + row.useflag + '\')">编辑</a> ';
            	
            	if (row.useflag == '1') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_GIVEITEM_TC\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_GIVEITEM_TC\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
                }
            	menubutton += ' <a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\');"><span style="color:red">删除</span></a>';
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
        name: $('#name').val()
    };
    return temp;
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });
}

function edit(id, name,useflag) {
	
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改赠送套餐',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['798px', '400px'],
        content:  contextPath+'/KQDS_GiveItem_TCBackAct/toEdit.act?seqId=' + id + '&name=' + name + '&useflag=' + useflag + '&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        end: function() {
            refresh();
        }
    });
}
function del(id) {

    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_GiveItem_TCBackAct/deleteObj.act?seqId=' + id;
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增预赠送套餐',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['798px', '400px'],
        content: contextPath+'/KQDS_GiveItem_TCBackAct/toAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>',
        end: function() {
            refresh();
        }
    });
});
  
 
</script>



</body>
</html>
