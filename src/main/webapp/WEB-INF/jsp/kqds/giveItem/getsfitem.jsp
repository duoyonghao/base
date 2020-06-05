<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>选择赠送项目</title><!-- 查询收费项目，以列表方式展现，供选择 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
</head>
<body>
<div class="">
	<div class="register-bottom" style="float: left;">
		<div class="kv " style="margin-left: 100px;">
			<input type="text" placeholder="请输入项目编号、名称、类型" id="querydata" style="width: 250px;"	>
		</div>
		<div class="kv">
			<a id="searchUser" href="javascript:void(0);" class="refreshA">查 询</a>
		</div>
	</div>
    <!--表格-->
    <div class="tableBox">
      	<table id="table" class="table-striped table-condensed table-bordered" data-height="auto">
        </table>
	</div>
</div>
</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var $table = $('#table');
//初始化表头，返回空的数据
var nullUrl = '<%=contextPath%>/UtilAct/intTableHeader.act';
var pageurl = contextPath + '/KQDS_TreatItemBackAct/getItemList.act?organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var number = 1;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
$(function() {
	querySfItem(nullUrl);
});
$('#searchUser').on('click',
   	function() {
      $('#table').bootstrapTable('refresh', {
          'url': pageurl + '&querydata=' + $("#querydata").val()
      });
});
function querySfItem(requrl) {
    $table.bootstrapTable({
        url: requrl,
        dataType: "json",
        singleSelect: false,
        striped: true,
        columns: [{
            title: '项目编号',
            field: 'treatitemno',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '项目名称',
            field: 'treatitemname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '折扣',
            field: 'discount',
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
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        //给父页面属性赋值 
        parent.itemno = row.treatitemno;
        parent.itemname = row.treatitemname;
        parent.unit = row.unit;
        parent.unitprice = row.unitprice;
        //关闭layer
        parent.layer.close(frameindex);
    });
}
</script>
</html>
