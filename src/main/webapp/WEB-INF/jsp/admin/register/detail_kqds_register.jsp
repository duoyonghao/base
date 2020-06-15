<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
String starttime = request.getParameter("starttime");
String endtime = request.getParameter("endtime");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>业务日志管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqds/listKqdsTreatitem.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
<section class="content">
	<div class="row">
		<div class="col-sm-12">
			<div id="toolbar" style="overflow:hidden;">
					<span class="commonText">开始日期：</span><input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
					<span class="commonText">截止日期：</span><input type="text" id="endtime" name="endtime" placeholder="截止日期" readonly class="birthDate">
					<button id="btn_edit" onclick="refresh()" type="button" class="btn btn-primary" style="float: right;">
					 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>刷新
					</button>
			</div>
			<div class="tableBox">
				<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
			</div>
			
		</div>
	</div>
</section> 
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<script type="text/javascript">
var pageurl = '<%=contextPath%>/KQDS_BCJLBackAct/selectPageLogin.act';
var $table = $('#table');
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
	$("#starttime").val('<%=starttime%>');
	$("#endtime").val('<%=endtime%>');
	/*初始化计算table的高度 */
    var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" table-striped table-condensed table-bordered data-height="'+tableHeight+'"></table>');
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
            title: '记录名称',
            field: 'jlname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '记录时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '操作人',
            field: 'createuser',
            align: 'center'
        }]
    });
	$(window).resize(function(){
		setHeight();
	});
});
function queryParams(params) {
    var temp = {
        offset: params.offset,
        limit: params.limit,
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        organization: '<%=ChainUtil.getOrganizationFromUrl(request)%>'
    };
    return temp;
}
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}
</script>



</body>
</html>
