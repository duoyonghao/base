<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqId = request.getParameter("seqId");
	if (seqId == null) {
		seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>修改记录</title><!-- 挂号页面 点击 咨询记录按钮进入  -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<body>
    <div class="tableBox">
       <table id="table" class="table-striped table-condensed table-bordered" data-height="318"></table>
    </div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var seqId = "<%=seqId %>";
var pageurl = '<%=contextPath%>/KQDS_BCJLBackAct/methodModifyList.act?seqId=' + seqId;
$(function() {
    gethisbl();
});
function gethisbl() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	 //付款方式赋值
	        getFkfsField();
        },
        columns: [{
            title: '修改时间',
            field: 'createtime',
            align: 'center',
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },{
            title: '修改人',
            field: 'createuser',
            align: 'center',
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '现金',
            field: 'xjmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '支付宝',
            field: 'zfbmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '微信',
            field: 'wxmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '银行卡',
            field: 'yhkmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '么么贷',
            field: 'mmdmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '百度分期',
            field: 'bdfqmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '其他',
            field: 'qtmoney',
            align: 'center',
            formatter: function(value, row, index) {
            	return '<span title="' + value + '">' + value + '</span>';
            }
        }]
    });
}
</script>

</html>
