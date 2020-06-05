<!-- wl整理 -->
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
<title>库存报警</title>
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
	<div id="container" style="padding-bottom:0;">
		<div class="tableHd">商品列表</div>
		<div class="tableBox" id="divkdxm">
			<table id="table"
				class="table-striped table-condensed table-bordered"></table>
		</div>
		<div id="totalTable" class="tableBox">
	    	<table style="width: 100%"> 
	       		<tr>
	 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
	 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
	       		</tr> 
	       	</table>
	    </div>
	    		<!--查询条件-->
		<div class="searchWrap">
			<div class="cornerBox">查询条件</div>
			<div class="btnBar">
				<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
				<a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			</div>
			<div class="formBox">
				<div class="kv">
					<div class="kv">
						<label>仓库</label>
						<div class="kv-v">
							<select name="sshouse" id="sshouse"></select>
						</div>
					</div>
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
	</div>
<div id="menuContent" class="menuContent" style="background:#DDDDDD;display:none; position: absolute;">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
</div>	
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectGoodsGjList.act';
var nowday;
var qxnameArr = ['ck_bjcx_scbb'];
var func = ['exportTable'];
$(function() {
	getButtonPowerByPriv(qxnameArr,func);//根据角色查询权限按钮
	getHouseSelect("sshouse");
	/* 初始化表格 */
    OrderDetail();
	/* 页面重置时 计算表格高度 */
	$(window).resize(function(){
		setHeight();
	});
	
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight);
 });
});
/* 计算表格高度*/
function setHeight(){
	$(".fixed-table-container").outerHeight($(window).height()-120-$("#totalTable").outerHeight());
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	gjtype:'min',
        goodstype: $('#goodstype').val(),
        sshouse: $('#sshouse').val(),
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

   
	
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0;
	        for(var i=0;i<data.length;i++){
	        	nums += Number(data[i].nums);
	        }
        	$("#nums").html(nums);
        	$("#table").bootstrapTable("resetView",{
        		height:$(window).height()-$(".searchWrap").outerHeight()-55
        	});
        },
        columns: [
			{
			    title: '所属仓库',
			    field: 'housename',
			    align: 'center',
			    
			    sortable: true,
	            formatter:function(value){
	            	if(value){
	            		return '<span>'+value+'</span>';
	            	}else{
	            		return '<span>-</span>';
	            	}
	            	
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
	            	return '<span style="text-align:left;float:left;position:relative;top:-1px;">'+value+'</span>';
	            }
			},
			{
			    title: '商品名称',
			    field: 'goodsname',
			    align: 'center',
			    
			    sortable: true,
	            formatter:function(value){
	            	return '<span style="text-align:left;float:left;position:relative;top:-1px;">'+value+'</span>';
	            }
			},
			{
			    title: '商品规格',
			    field: 'goodsnorms',
			    align: 'center',
			    
			    sortable: true,
	            formatter:function(value){
	            	return '<span style="text-align:left;float:left;position:relative;top:-1px;">'+value+'</span>';
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
			    title: '当前库存',
			    field: 'nums',
			    align: 'center',
			    
			    sortable: true,
			    formatter: function(value, row, index) {
		        	  var html = "";
		        	  if(Number(value)<Number(row.minnums)){
		        		  html = '<span style="position:relative;top:2px;color:red;font-size: 14px !important;" >' + value + '</span>';
		        	  }else{
		        		  html =  '<span>'+value+'</span>' ;
		        	  }
		              return html;
		          }
			},
			{
			    title: '下限报警值',
			    field: 'minnums',
			    align: 'center',
			    
			    sortable: true,
			    formatter: function(value, row, index) {
		        	  var html = "";
		        	  if(Number(value)<0){
		        		  html = '<span style="position:relative;top:2px;color:red;font-size: 14px !important;" >' + value + '</span>';
		        	  }else{
		        		  html =  '<span>'+value+'</span>' ;
		        	  }
		              return html;
		          }
			}
			]
    });
}
</script>
</html>
