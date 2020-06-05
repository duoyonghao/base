<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String canDelCk = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag18_canDelCk, request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>领料查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
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
</style>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap" >
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="refresh()">查询</a>
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>录单日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>供应商</label>
		                <div class="kv-v">
		                    <select  name="supplier" id="supplier"></select>
		                </div>
	           		</div>
	           		<div class="kv" >
						<label>商品编号</label>
						<div class="kv-v">
							  <input type="text" name="goodscode" id="goodscode"  class="form-control" />
						</div>
					</div>
					<div class="kv">
						<label>领料时间</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="stock_starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="stock_endtime" />
							</span>
						</div>
					</div>
                </div>
            </div>
        </div> 
    <!-- <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>
 	-->
    <div class="tableHd">退还商品列表</div>
    <div class="tableBox1" id="divkdxm" >
        <table id="dykdxm" class="table-striped table-condensed table-bordered" ></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%"> 
       		<tr>
 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
 				<td width="30%"><span style="color:#00A6C0;">金额：<lable id="allmoney">0</lable></span></td>
       		</tr> 
       	</table>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/HUDH_Goods_Pick_Send_BackAct/findGoodsPickSendBackAll.act';
var goodsDetail = "";
var nowday;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
	getHouseSelect("inhouse");
	getSupplierSelect2("supplier");
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    initTableList();
});

function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=(windowHeight-$(".searchWrap").outerHeight()-60)/2;//本页面有两个table
	$("#table").bootstrapTable("resetView",{
		height: tableHeight
	})
	$("#dykdxm").bootstrapTable("resetView",{
		height: tableHeight
	})
}

$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
   		//intype: $('#intype').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        //inhouse: $('#inhouse').val(),
        supplier: $('#supplier').val(),
        goodscode: $('#goodscode').val(),
        stock_starttime: $('#stock_starttime').val(),
        stock_endtime: $('#stock_endtime').val()
    };
    return temp;
}

function refresh(){
	//点击查询时，置空入库明细列表
	//$('#dykdxm').find('tbody').html("");
    $('#dykdxm').bootstrapTable('refresh', {
        'url': pageurl
    });
}

function initTableList() {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    //var detailurl = contextPath + '/GoodsDoctorPickInDetailAct/findDoctorPickInDetailByIncode.act?incode='+incode;
    $("#dykdxm").bootstrapTable({
        url: pageurl,
        dataType: "json",
        cache: false,
        striped: true,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	//alert(JSON.stringify(data));
        	//console.log(JSON.stringify(data));
        	$("#total").html(data.length);
        	var nums1=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].amount);
	        	nums1 += Number(data[i].nums);
	        }
        	$("#allmoney").html(allmoney.toFixed(2));
        	$("#nums").html(nums1);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
			/* {
			    title: '操作',
			    field: ' ',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
			    	html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="goodsEdit(\'' + index + '\')">编辑</a> </span>';
			        return html;
			    }
			}, */
			{
			    title: '退还科室',
			    field: 'deptname',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '商品编号',
			    field: 'goodscode',
			    align: 'center',
			    
			    sortable: true,
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '商品名称',
			    field: 'goodsname',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
			    title: '规格',
			    field: 'goodsnorms',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '单位',
			    field: 'goodsunit',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			/* {
			    title: '单价',
			    field: 'nuitprice',
			    align: 'center',
	            formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			}, */
			{
			    title: '商品批号',
			    field: 'batchnum',
			    align: 'center',
	            formatter:function(value){
	            		return '<span title="'+value+'">'+value+'</span>';
	            }
			},
			{
			    title: '退还数量',
			    field: 'nums',
			    align: 'center',
	            formatter:function(value){
	            	if(Number(value)<0){
	            		return '<span class="money" style="color:red;">'+value+'</span>';
	            	}else{
	            		return '<span class="money">'+value+'</span>';
	            	}
	            }
			},
			/* {
			    title: '退还金额',
			    field: 'amount',
			    align: 'center',
	            formatter:function(value){
	            	if(Number(value)<0){
	            		return '<span class="money" style="color:red;">'+value+'</span>';
	            	}else{
	            		return '<span class="money">'+value+'</span>';
	            	}
	            }
			}, */
			{
			    title: '退还时间',
			    field: 'createtime',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
	            title: '退还人',
	            field: 'userName',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	return '<span>'+value+'</span>';
	            }
	        },
			{
			    title: '退还备注',
			    field: 'remark',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
			        /* html = '<span class="remark" title="' + value + '">' + value + '</span>';
			        return html; */
			    	return '<span>'+value+'</span>';
			    }
			}]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj3 = $('#table').bootstrapTable('getData')[index];
    });
}
</script>
</html>
