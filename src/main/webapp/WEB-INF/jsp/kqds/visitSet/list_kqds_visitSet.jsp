<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>回访设置</title>
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
					<span class="commonText">回访分类：</span><select class="dict" tig="HFFL" name="hffl" id="hffl"></select>
					<span class="commonText">回访角色：</span><select class="select2 priv" name="userpriv" id="userpriv" org="<%=ChainUtil.getOrganizationFromUrl(request)%>"></select>
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
var pageurl = '<%=contextPath%>/KQDS_visitSetAct/selectPage.act';
var $table = $('#table');
$(function() {
	initDictSelectByClassOrg('<%=ChainUtil.getOrganizationFromUrl(request)%>');
	intPrivSelectByClassWithCommon();
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
            title: '回访分类 ',
            field: 'hhflname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '回访角色',
            field: 'userprivname',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '回访备注',
            field: 'remark',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '回访目的',
            field: 'purpose',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '间隔天数',
            field: 'jgday',
            align: 'center',
            valign: 'middle',
            sortable: true,
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
            sortable: true
        },
        {
            title: '操作',
            field: 'pkcode',
            align: 'center',
            formatter: function(value, row, index) {
            	var menubutton = "";
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="edit(\'' + row.seqId + '\')">编辑</a> ';
            	menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="del(\'' + row.seqId + '\')"><span style="color:red">删除</span></a> ';
                menubutton += '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">详情</a> ';
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
        organization: '<%=ChainUtil.getOrganizationFromUrl(request)%>',
        hffl: $('#hffl').val(),
        userpriv: $('#userpriv').val()
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：修改回访设置',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/KQDS_visitSetAct/toEdit.act?seqId=' + id+'&organization=<%=ChainUtil.getOrganizationFromUrl(request)%>'
    });
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：回访设置详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/KQDS_visitSetAct/toDetail.act?seqId=' + id+'&organization=<%=ChainUtil.getOrganizationFromUrl(request)%>'
    });
}
function del(id) {
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var url = '<%=contextPath%>/KQDS_visitSetAct/deleteObj.act?seqId=' + id;
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
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：新增回访设置',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath+'/KQDS_visitSetAct/toNewAdd.act?organization=<%=ChainUtil.getOrganizationFromUrl(request)%>'
    });
});
  
function setHeight(){
	var tableHeight=0;
	
	tableHeight=$(window).outerHeight();
	/*$(".fixed-table-container").outerHeight(tableHeight);*/
	$("#table").bootstrapTable("resetView",{
		height:tableHeight-40
	})
}
</script>



</body>
</html>
