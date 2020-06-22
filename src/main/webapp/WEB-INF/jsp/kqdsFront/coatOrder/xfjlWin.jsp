<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
   String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>消费记录</title><!-- 由 挂号页面   消费记录 按钮 进入  -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/register_common.css" />
	<!-- 数据表中数据的样式 -->
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
	<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
	<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
	</style>
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
var contextPath = "<%=contextPath %>";
var usercode = "<%=usercode %>";
var pageurl = contextPath + '/KQDS_CostOrderAct/getAll.act?querytype=notFilterVisual'; // 不做门诊条件过滤，不做可见人员过滤
$(function() {
    getlzjl();
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,
    	offset:params.offset,
    	pageIndex : params.offset/params.limit + 1,
    };
    return temp;
}

function getlzjl() {
    //流转记录
    var url = pageurl + '&usercode=' + usercode;
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 50,
        pageList : [5, 25, 50, 100],//可以选择每页大小
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess:function(data){
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [{
            title: '收费时间',
            field: 'sftime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value.indexOf("null") > -1) {
                    return "<span></span>";
                } else {
                    var sftime = value.substring(0, 16);
                    return '<span class="time">' + sftime + '</span>';
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter:function(value,row,index){
            	return "<span>"+value+"</span>";
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已成交") {
                    return '<span style="color:green">已成交</span>';
                } else {
                    return '<span style="color:red">未成交</span>';
                }
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '医生',
            field: 'doctorname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '合计',
            field: 'totalcost',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '免除金额',
            field: 'voidmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '应收金额',
            field: 'shouldmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '欠费金额',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '缴费金额',
            field: 'actualmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">￥' + value + '</span>';
            }
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span class="time" >' + value.substring(5) + '</span>';
                return html;
            }
        },
        {
            title: '开单人',
            field: 'createuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '收费人',
            field: 'sfuser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '建档人',
            field: 'jduser',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '建档时间',
            field: 'jdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="time">' + value + '</span>';
                    return html;
                }
            }
        }]
    });
}
</script>

</html>
