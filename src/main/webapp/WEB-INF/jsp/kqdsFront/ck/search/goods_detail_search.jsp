<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品资料</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<style>
	.fixed-table-container thead th .sortable{
		padding-right:8px;
	}
</style>
</head>
<body>
	<div id="container">
		<!--查询条件-->
		<div class="searchWrap">
			<div class="cornerBox">查询条件</div>
			<div class="btnBar">
				<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
				<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			</div>
			<div class="formBox">
				<div class="kv">
					<div class="kv ">
						<label>商品类别</label>
						<div class="kv-v">
							 <input type="hidden" name="goodstype" id="goodstype" />
				             <input type="text" name="goodstypename" id="goodstypename" readonly="readonly" onclick="selectGoodsTypezTreeInit();">
						</div>
					</div>
					<div class="kv">
						<label>模糊查询</label>
						<div class="kv-v">
							  <input type="text" name="goodsname" id="goodsname" placeholder="商品名称/编号" style="width: 150px;" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="tableHd">商品列表</div>
		<div class="tableBox" id="divkdxm">
			<table id="table"
				class="table-striped table-condensed table-bordered"></table>
		</div>
		<div id="totalTable" class="tableBox">
	    	<table style="width: 100%"> 
	       		<tr>
	 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
	       		</tr> 
	       	</table>
	    </div>
	</div>
<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectGoodsDetailList.act';
var nowday;
var qxnameArr = ['ck_spzl_scbb'];
var func = ['exportTable'];
$(function() {
	getButtonPowerByPriv(qxnameArr,func);//根据角色查询权限按钮
	/* 初始化表格 */
    OrderDetail();
	/* 页面重置时 计算表格高度 */
	$(window).resize(function(){
		setHeight();
	});
});
/* 计算表格高度*/
function setHeight(){
	$(".fixed-table-container").outerHeight($(window).height()-120-$("#totalTable").outerHeight());
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        goodstype: $('#goodstype').val(),
        queryInput: $('#goodsname').val()
    };
    return temp;
}
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,
    	offset:params.offset,
    	pageIndex : params.offset/params.limit + 1,
    	goodstype: $('#goodstype').val(),
        queryInput: $('#goodsname').val()
    };
    return temp;
}
$('#query').on('click',
		function() {
		    $('#table').bootstrapTable('refresh', {
		        'url': pageurl
		    });
		});
		$('#clean').on('click',
		function() {
		    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
		});
//导出
function exportTable() {
	var fieldArr=[];
	var fieldnameArr=[];
	$('#table thead tr th').each(function () {
		var field = $(this).attr("data-field");
		if(field!=""){
			fieldArr.push(field);//获取字段
			fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
		}
	});
	var param  = JsontoUrldata(queryParams());
	location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}
function OrderDetail() {

    $("#divkdxm").attr("data-height", $(window).height() - 200);
    
	/*wl----首次加载时 计算table高度  */
	var tableHeight=0;/* 计算table需要的高度 */
	/* 根据当前页面 计算出table需要的高度 */
	tableHeight=$(window).height()-120-$("#totalTable").outerHeight();
	/* 框架要使用改table */
	$("#divkdxm").html("<table id='table' class='table-striped table-condensed table-bordered' data-height='"+tableHeight+"'></table>");
	/*wl----首次加载时 计算table高度————————————结束  */
	
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        queryParamsType:'limit',
        queryParams:queryParamsB,
        cache: false,
        striped: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        },
        columns: [
			{
				title : '序号',
				align: "center",
				width: 40,
				formatter: function (value, row, index) {
					/* return index + 1; */
					var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
			    	var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
			    	return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
				}
			},
			{
			    title: '一级类别',
			    field: 'firsttype',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '二级类别',
			    field: 'goodstype',
			    align: 'center',
			    
			    sortable: true,
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
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '商品规格',
			    field: 'goodsnorms',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '单位',
			    field: 'goodsunit',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '上限值',
			    field: 'maxnums',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '上限报警',
			    field: 'maxgj',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '下限值',
			    field: 'minnums',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
			{
			    title: '下限报警',
			    field: 'mingj',
			    align: 'center',
			    
			    sortable: true,
			    formatter:function(value){
			    	return '<span>'+value+'</span>';
			    }
			},
	       {
	          title: '备注',
	          field: 'remark',
	          align: 'center',
	          
	          formatter: function(value, row, index) {
	                html = '<span class="time" >' + value + '</span>';
	                return html;
	            }
	       }
			]
    });
}
</script>
</html>
