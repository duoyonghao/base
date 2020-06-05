<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	  contextPath = "/kqds";
	}
	
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>回访列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bingli.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
</head>
<body>
	<div id="centerWrap">
		<div class="tableBox">
			<table id="table"
				class="table-striped table-condensed table-bordered"
				data-height="490"></table>
		</div>
	</div>
</body>
<script type="text/javascript">

var contextPath = '<%=contextPath%>';
var onclickrowObj = "";
var pageurl = '<%=contextPath%>/KQDS_VisitAct/selectListNotByPersonId.act';
var usercode = "<%=usercode%>";
$(function() {
	if(usercode == ''){
		 layer.alert('请选择患者', {
	           
	     },function(){
	    	 return false;
	     });
	 }else{
		pageurl = pageurl + '?usercode=' + usercode;
		// 加载列表
		inittable();
	 }
});
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-5
	});
}
//加载表格
function inittable() {
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [{
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '回访状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var htmlstr = "";
                if (row.status == "0") {
                    htmlstr = "<span style='color:red'>未回访</span>";
                } else if (row.status == "1") {
                    htmlstr = "<span style='color:green'>已回访</span>";
                }
                return htmlstr;
            }
        },
        {
            title: '回访时间',
            field: 'visittime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '回访人员',
            field: 'visitorname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '患者手机',
            field: 'telphone',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '患者性别',
            field: 'sex',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != null && value != "") {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访分类',
            field: 'hfflname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访要点',
            field: 'hfyd',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="time">' + value + '</span>';
                }
            }
        },
        {
            title: '提交人员',
            field: 'postpersonname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访结果',
            field: 'hfresult',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="time">' + value + '</span>';
                }
            }
        },
        {
            title: '回访结果时间',
            field: 'finishtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '满意度',
            field: 'mydname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}

</script>
</html>
