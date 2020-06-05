<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>注册用户管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div id="toolbar">
					<span class="commonText">单位名称：</span><input type="text" name="dwmc" id="dwmc"  />
					<span class="commonText">联系人：</span><input type="text" name="lxr" id="lxr"  />
					<span class="commonText">手机号码：</span><input type="text" name="sjhm" id="sjhm"  />
					<span class="commonText">注册时间：</span>
                    <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
                    <span class="commonText">到：</span>
                    <input type="text" id="endtime" name="endtime" placeholder="截止日期" readonly class="birthDate">
					<button id="btn_edit" onclick="refresh()" type="button" class="btn btn-primary" style="float: right;margin-left: 5px;">
				 		<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
					<button onclick="exportTable()" class="btn btn-primary" style="float: right;">
						<i class="glyphicon glyphicon-plus"></i> 导出
					</button>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
var pageurl = '<%=contextPath%>/KQDS_RegisterAct/selectPage.act';
var $table = $('#table');
function setHeight(){
	var tableHeight=getTableHeight()-20;
	$(".fixed-table-container").outerHeight(tableHeight);
}
/*  */
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".clearfix").outerHeight()-$("#toolbar").outerHeight()-10;
	return tableHeight;
}
$(function() {
	//时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "buttom-right"
    });
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $('#table').bootstrapTable({
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
            title: '单位名称',
            field: 'dwmc',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '联系人',
            field: 'lxr',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
        {
            title: '手机号码',
            field: 'sjhm',
            align: 'center',
            valign: 'middle',
        },
        {
            title: '注册时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
        },{
            title: '操作',
            field: ' ',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                var html = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.organization + '\')">查看</a> ';
                return html;
            }
        }
        ]
    });
	$(window).resize(function(){
		setHeight();
	});
});
function queryParams(params) {
    var temp = {
        offset: params.offset,
        limit: params.limit,
        dwmc: $("#dwmc").val(),
        lxr: $('#lxr').val(),
        sjhm: $('#sjhm').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val()
    };
    return temp;
}
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

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
    var dwmc = $('#dwmc').val();
    var lxr= $('#lxr').val();
    var sjhm= $('#sjhm').val();
    var starttime=$('#starttime').val();
    var endtime=$('#endtime').val();
    var params = "&dwmc="+dwmc+"&lxr="+lxr+"&sjhm="+sjhm+"&starttime="+starttime+"&endtime="+endtime;
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr)+params;
}
function detail(id) {
    var index = layer.open({
        type: 2,
        title: '注册记录详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: '<%=contextPath%>/KQDS_RegisterAct/toDetailRegister.act?organization=' + id +'&starttime='+$('#starttime').val()+'&endtime='+$('#endtime').val()
    });
}
</script>



</body>
</html>
