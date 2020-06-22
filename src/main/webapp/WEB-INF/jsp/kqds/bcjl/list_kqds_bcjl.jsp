<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
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
	<div>
		<div id="toolbar" style="overflow:hidden;">
			<span class="commonText">患者编号：</span><input type="text" name="usercode" id="usercode" >
			<span class="commonText">患者姓名：</span><input type="text" name="username" id="username" >
			<span class="commonText">操作人：</span>
				<input type="hidden" name="createuser" id="createuser"/>
				<input type="text" class="whiteInp" id="createuserDesc" name="createuserDesc" placeholder=""  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'single',1);"  >
			
			<button id="btn_edit" onclick="refresh()" type="button" class="kqdsSearchBtn bigBtn" style="float: right;">
			 	<span class="glyphicon glyphicon-refresh icon-refresh" aria-hidden="true"></span>查询
			</button>
		</div>
		<div class="tableBox">
			<table id="table" class="table-striped table-condensed table-bordered"  data-height="520"></table>
		</div>
	</div>
</section>
<!--计算table自适应的高度的js-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqds/list_kqds.js"></script>
<script type="text/javascript">
var pageurl = '<%=contextPath%>/KQDS_BCJLBackAct/selectPage.act';
var $table = $('#table');
$(function() {
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
            title: '部门',
            field: 'deptname',
            align: 'center',
            valign: 'middle',
        },
        {
            title: '内容',
            field: 'content',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    var html = '<a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="detail(\'' + row.seqId + '\')">查看</a> ';
                    return html;
                }
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: ' 病人姓名',
            field: 'bname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '挂号编号',
            field: 'regno',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '病程名称',
            field: 'bcmc',
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
        organization: '<%=ChainUtil.getOrganizationFromUrl(request)%>',
        usercode: $('#usercode').val(),
        username: $('#username').val(),
        createuser: $('#createuser').val()
    };
    return temp;
}
function refresh() {

    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

}

function detail(id) {
    var index = layer.open({
        type: 2,
        title: '<%=ChainUtil.getOrganizationNameFromUrl(request)%>：病程记录详情',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: '<%=contextPath%>/KQDS_BCJLBackAct/toBackDetail.act?seqId=' + id
    });
}
</script>



</body>
</html>
