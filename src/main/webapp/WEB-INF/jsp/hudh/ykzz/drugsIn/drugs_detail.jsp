<%@ page import="java.util.List,net.sf.json.JSONObject" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	List<JSONObject> list=(List<JSONObject>)request.getAttribute("list");
	List<JSONObject> batchlist=(List<JSONObject>)request.getAttribute("batchlist");
	String outnums=(String)request.getAttribute("outnums");
	String batchnums=(String)request.getAttribute("batchnums");
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>药品详情</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
.searchWrap{
	padding-top:35px;
}
.fixed-table-container thead th .sortable {
    padding-right: 0px;
}
#table td>span{
	width:100%;
}
#table{
	table-layout:fixed;
}
.fixedHeaderDiv{
	overflow:hidden;
	background:#00A6C0;
	position:relative;
	z-index:2;
}
.fixedHeaderDiv table{
	table-layout:fixed;
}
.table-bordered,.table-bordered > thead > tr > th, .table-bordered > thead > tr > td{
	border:none;
}
#table td, #table1 td, #table2 td, #listTable td, #dykdxm td, #chufang td{
	border:none;
}
/* 2019/8/16 lutian 关闭按钮 */
#closeBtn{
	display: inline-block;
    box-sizing: border-box;
    height: 26px;
    line-height: 26px;
    background: #00a6c0;
    color: #fff;
    outline: none;
    padding: 0 15px;
    border: 1px solid #00a6c0;
    border-radius: 3px;
    margin-right: 3px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
    margin: 20px auto 0px;
}
#ckTotal{
	display: inline-block;
    font-size: 16px;
    margin-top: 20px;
    margin-left: 20%;
    margin-right: 20px;
    font-weight: bold;
}
</style>
<body>
<!-- 2019-08-14 lwg 查看药品批号情况 -->
<div class="fixedHeaderDiv" style="overflow: scroll;border-top: 1px solid #ddd;">
<table id="table" class="table-striped table-condensed table-bordered" style="width:100%;" onkeydown="keyDown(event)">
    <thead style="height:30px;color:white;">
		<tr>
			<!-- <th style="text-align: center; vertical-align: middle; width:40px;"><span>序号</span></th> -->
			<th style="text-align: center; vertical-align: middle; width:95px;"><span>药品编号</span></th>
			<th style="text-align: center; vertical-align: middle; width:150px;"><span>批准文号</span></th>
			<th style="text-align: center; vertical-align: middle; width:150px;"><span>药品名称</span></th>
			<th style="text-align: center; vertical-align: middle; width:150px;"><span>化学名称</span></th>
			<th style="text-align: center; vertical-align: middle; width:95px;"><span>批号</span></th>
			<th style="text-align: center; vertical-align: middle; width:130px;"><span>规格</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>包装单位</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>包装数量</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>含量单位</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>含量系数</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>基本单位</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>剂型</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>出库数量</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>库存数量</span></th>
			<th style="text-align: center; vertical-align: middle; width:62px;"><span>入库数量</span></th>
			<th style="text-align: center; vertical-align: middle; width:150px;"><span>入库日期</span></th>
			<th style="text-align: center; vertical-align: middle; width:83px;"><span>生产日期</span></th>
			<th style="text-align: center; vertical-align: middle; width:83px;"><span>到期时间</span></th>
			<th style="text-align: center; vertical-align: middle; width:80px;"><span>剩余时间</span></th>
		</tr>
	</thead>
	<tbody style="background-color: #F0FFFF;text-align: center; height:345px; overflow-y:auto;"></tbody>
</table>
</div>
<div id="ckTotal">当天所有药品总出库数量:<span id="outnums" style=" color:red; font-weight: bold; font-size:16px;"></span> <span style="font-weight: bold; font-size:16px;">该药品当天出库数量:</span><span  style="color:red; font-weight: bold; font-size:16px;" id="batchnums"></span><span style="font-weight: bold; font-size:16px;" id="batchnum"></span></div>
<button id="closeBtn">关闭</button>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript">
var list=<%=list%>;
var batchlist=<%=batchlist%>;
var outnums=<%=outnums%>;
var batchnums=<%=batchnums%>;
var $table = $('#table');
var onclickrow = ""; //选中行对象
var number = 1;
$(function() {
    //计算主体的宽度
    /* setHeight();
    $(window).resize(function() {
        setHeight();
    }); */
    /* 计算并设置table的高度 */
    resizeTableHeight();
    /*页面大小改变时 计算并设置table的高度 */
    $(window).resize(function(){
    	resizeTableHeight();
    });
    /*内容与表头一起滚动  */
    $(".fixedHeaderDiv").scrollLeft($(this).scrollLeft());
	addDoodsDetail(list);
	$("#outnums").html(outnums);
	$("#batchnums").html(batchnums);
	var batch="";
	for (var i = 0; i < batchlist.length; i++) {
		batch+=" 批号"+"<span style='color:red; font-size:16px;'>"+batchlist[i].barchno+"</span>"+"的出库数量:"+"<span  style='color:red; font-size:16px;'>"+batchlist[i].batchoutnum+"</span>";
	}
	$("#batchnum").html(batch);
}); 
/* 计算 设置table数据表的高度 */
function resizeTableHeight(){
    //$(".fixedHeaderDiv table").outerHeight(tableHeight);
    /* 出现自动出现滚动条时 会导致表头内容对不齐  现改为 开始就出现滚动条  */
    //$(".fixedHeaderDiv table").outerWidth($(".fixedHeaderDiv").outerWidth()-15);/* 表头留出右边15px的滚动条出现距离 */
}
/* 2019/8/16 lutain 点击关闭按钮关闭当前页面 */
$("#closeBtn").click(function(){
	//当你在iframe页面关闭自身时
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index); //再执行关闭 
});
 
function addDoodsDetail(list){
	 //显示条数
    var detail;
	var tablehtml = "";
    for (var i = 0; i < list.length; i++) {
		detail=list[i];
		var a= i+1;
        var timestamp2 = Date.parse(new Date(detail.valid));
        timestamp2 = timestamp2 / 1000;
        var nowday = getNowFormatDate();
        var timestamps = Date.parse(nowday);
	    timestamps = timestamps / 1000;
    	var time=(timestamp2-timestamps)/86400;
    	if(time<=0){
    		time='已过期';
    	}else{
    		time=time+"天";
    	}
		tablehtml += "<tr style=''>";
		 //序号
	   /*  tablehtml += '<td style="width:auto;" style="text-align:center;"><span>'+a+'</span></td>'; */
	    //药品编号
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.order_no +'>'+detail.order_no+'</span></td>';
	  	//批准文号
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.contry_code +'>'+detail.contry_code + '</span></td>';
	    //药品名称
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.good_name +'>' + detail.good_name + '</span></td>';
	    //化学名称
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.chemistry_name +'>' + detail.chemistry_name + '</span></td>';
	  	//批号
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.batchnum +' style="color:red;">'+detail.batchnum+'</span></td>';
	    //规格
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.pharm_spec +'>'+detail.pharm_spec+'</span></td>';
	    //包装单位
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.packing_unit +'>'+detail.packing_unit+'</span></td>';
	    //包装数量
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.packing_num +'>'+detail.packing_num+'</span></td>';
	    //含量单位
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.content_unit +'>'+detail.content_unit+'</span></td>';
	    //含量系数
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.content_coe +'>'+detail.content_coe+'</span></td>';
	 	//基本单位
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.company_min +'>'+detail.company_min+'</span></td>';
	  	//剂型
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.pharm_dos +'>'+detail.pharm_dos+'</span></td>';
	  	//出库数量
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.outnum +' style="color:red;">'+detail.outnum+'</span></td>';
	    //库存数量
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.batchnonum +' style="color:red;">'+detail.batchnonum+'</span></td>';
	  	//入库数量
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.quantity +'>'+detail.quantity+'</span></td>';
	    //入库时间
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.createtime +'>'+detail.createtime+'</span></td>';
	    //生产时间
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.createdate +'>'+detail.createdate+'</span></td>';
	    //到期时间
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+detail.valid +'>'+detail.valid+'</span></td>';
	    //剩余时间
	    tablehtml += '<td style="width:auto;" style="text-align:center;"><span title='+time +' style="color:red;">'+time+'</span></td>';
	    tablehtml += "</tr>";
	}
	
    $('#table').find('tbody').append(tablehtml);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - 65);
    $('#table').bootstrapTable('resetView', {
        height: baseHeight - serachH - 5
    });

}
</script>
</html>
