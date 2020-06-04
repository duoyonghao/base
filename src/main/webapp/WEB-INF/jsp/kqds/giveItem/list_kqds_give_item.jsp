<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>赠送项目管理</title>
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
					<button onclick="batchDelete()" class="kqdsSearchBtn bigBtn" >
						<i class="glyphicon glyphicon-remove"></i> 批量删除
					</button>
					<span class="commonText">项目编号：</span><input type="text" name="itemno" id="itemno" >
					<span class="commonText">项目名称：</span><input type="text" name="itemname" id="itemname" >
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
var pageurl = '<%=contextPath%>/KQDS_Give_ItemBackAct/selectPage.act';
var $table = $('#table');
$(function() {
	
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess:function(){
        	setHeight();
        },
        //服务端处理分页
        columns: [
		{field: ' ',checkbox: true,formatter: stateFormatter},
        {
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '项目名称',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '排序号',
            field: 'sortno',
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
            sortable: true
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
            	
            	menubutton +=  '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
                
                if (row.useflag == '1') {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_GIVEITEM\',\'' + row.seqId + '\',0);"><span style="color:green">启用</span></a>';
                } else {
                    menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="changeUseFlag(\'KQDS_GIVEITEM\',\'' + row.seqId + '\',1);"><span style="color:red">禁用</span></a>';
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
        itemno: $('#itemno').val(),
        itemname: $('#itemname').val()
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改赠送项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/KQDS_Give_ItemBackAct/toEdit.act?seqId=' + id
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：赠送项目详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '280px'],
        content: contextPath+'/KQDS_Give_ItemBackAct/toDetail.act?seqId=' + id
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
		layer.alert('请选择要删除的赠送项目' );
		return false;
	}
	
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_Give_ItemBackAct/deleteObj.act?seqId=' + seqId;
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
function del(id) {
    // 询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_Give_ItemBackAct/deleteObj.act?seqId=' + id;
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

// 弹出一个iframe层
$('#parentIframe').on('click',
function() {
    layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增赠送项目',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '480px'],
        content:  contextPath+'/KQDS_Give_ItemBackAct/toAdd.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>'
    });
});
  
//复选框
function stateFormatter(value, row, index) {
    <%-- if ("admin" != "<%=person.getUserId()%>") {
        return {
            disabled: true,
            checked: false
        };
    } --%>
   
    return value;
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
} 
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight()-40;
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	})
	$(".leftDiv").outerHeight(tableHeight);
}
</script>



</body>
</html>
