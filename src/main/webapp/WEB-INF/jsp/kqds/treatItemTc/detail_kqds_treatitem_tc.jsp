<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%

	String tcnameid = request.getParameter("tcnameid");

	String costno = request.getParameter("costno");
	String RegNo = request.getParameter("RegNo");
	String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>收费套餐详情</title>
<!-- 由费用添加页面  新增套餐， 后台提供编辑 查询 功能 -->
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>

<style>
	.keep-open button{
		height:26px;
		
	}
	input[type="text"]{/*首行输入框与同行按钮等高  */
		height:26px;
		line-height:26px;
		font-size:13px;
	}
	.keep-open i{/* 按钮中的图标 */
		top:1px;
	}
	.keep-open .caret{/* 按钮中的图标 */
		top:-4px;
		position:relative;
	}
	.btn-group.dropup button{	/* 显示行数按钮 */
		height:19px;
	}
	.btn-group.dropup button span{/* 显示行中的数字 */
		position:relative;
		top:-6px;
	}
	.fixed-table-pagination>div{/* 去掉分页区 的外边距 */
		margin:0;
	}
	footer{
		padding:0 15px;
		clear:both;
		
	}
	footer .commonText{
		font-size:14px;
		display:inline-block;
		text-align:right;
		position:relative;
		padding-right:5px;
		color:#333;
	}
	.keep-open.btn-group>button{/*第三个按钮  数据项显示控制按钮的样式  */
		width:40px;
		height:34px;
	}
	.keep-open.btn-group span.caret{	/* 第三个按钮中的向下三角 */
		top:0px;
	}
	.table-striped > tbody > tr:nth-child(odd){	/* 单行为蓝色背景 */
		background:#f0ffff;
	}
</style>
</head>
<body>
<section class="content-header" id="se1"></section>
<section class="content">
<div class="row">
	<div class="col-sm-12">
		<div id="toolbar">
			<!--   <button id="parentIframe" class="btn btn-danger" >
		            <i class="glyphicon glyphicon-plus"></i> 开单
		     </button> -->
		</div>
		<table id="table" data-toolbar="#toolbar" data-search="true"
			data-show-refresh="true" data-show-toggle="true"
			data-show-columns="true" data-show-export="true" data-height="430"></table>
	</div>
	
	<footer>
		<span class="commonText">原价：<span id="totalcost"></span>
				
		<span class="commonText">折扣额：<span id="discountmoney"></span>
		
		<span class="commonText">小计：<span id="xiaojimoney"></span>
		
	</footer>
</div>
</section>
<script>
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var tcnameid = "<%=tcnameid%>";
var costno = "<%=costno%>";
var usercode = "<%=usercode%>";
var RegNo = "<%=RegNo%>";
var pageurl = '<%=contextPath%>/KQDS_TreatItem_TcBackAct/selectPageByTctypeAndTcname.act?tcnameid=' + tcnameid;
var $table = $('#table');
$(function() {
    OrderDetail();
});
function OrderDetail() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        striped: true,
        queryParams: function queryParams(params) { //设置查询参数
            var param = {
                //这里是在ajax发送请求的时候设置一些参数
                limit: params.limit,
                search: costno
            };
            return param;
        },
        onLoadSuccess: function(data) { //加载成功时执行
        	var totalcost=0,discountmoney=0;
	        for(var i=0;i<data.rows.length;i++){
	        	 // 合计
		   		 totalcost += Number(data.rows[i].subtotal);
		   		 // 折扣额
		   		 var num = Number(data.rows[i].num);
		   		 var unitprice = Number(data.rows[i].unitprice);
		   		 var dismoney = Number(data.rows[i].discount);
		   		 discountmoney +=  num * unitprice/100 * (100-dismoney);
	        }
	        
	        $("#xiaojimoney").html(Number(totalcost).toFixed(2));
	        $("#discountmoney").html(Number(discountmoney).toFixed(2));
	        $("#totalcost").html(Number(Number(totalcost) + Number(discountmoney)).toFixed(2));
        },
        //参数
        sidePagination: "server",
        //服务端处理分页
        columns: [{
            title: '项目编号',
            field: 'itemno',
            align: 'center',
            valign: 'middle',
            sortable: true
        },

        {
            title: '治疗项目',
            field: 'itemname',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '单价',
            field: 'unitprice',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.unitprice;
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '折扣%',
            field: 'discount',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                return "￥" + row.subtotal;
            }
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        }]
    });
}
function refresh() {
    $table.bootstrapTable('refresh', {
        'url': pageurl
    });
}
</script>
</body>
</html>
