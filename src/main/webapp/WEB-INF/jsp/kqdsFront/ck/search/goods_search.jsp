<!--wl整理-->
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
<title>当前库存</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!-- 跳页 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
<style type="text/css">
	/* .fixed-table-container thead th .sortable{
		padding-right:8px;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		overflow: hidden;
	} */
	.fixed-table-container thead th .sortable{
		padding-right: 0px;
	}
	.fixed-table-container {
	     border-left: 1px solid #ddd;
	     border-right: 1px solid #ddd;
	     border-bottom: 1px solid #ddd;
	     border-radius: 6px;
	     overflow: hidden;
 	}
</style>
</head>
<body>
	<div id="container">

		<div class="tableHd">商品列表</div>
		<div class="tableBox" id="divkdxm">
			<table id="table"
				class="table-striped table-condensed table-bordered" style="table-layout: fixed;border:1px solid blue;"></table>
		</div>
		<div id="totalTable" class="tableBox">
	    	<table style="width: 100%;"> 
	       		<tr>
	 				<td width="30%"><span style="color:#00A6C0;">共有记录<lable id="total">0</lable>条</span></td>
	 				<td width="30%"><span style="color:#00A6C0;">数量：<lable id="nums">0</lable></span></td>
	 				<td width="30%"><span style="color:#00A6C0;">金额：<lable id="allmoney">0</lable></span></td>
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
					<div class="kv ">
						<label>起始时间</label>
						<div class="kv-v">
							 <input type="text" id="qstime" name="qstime" style="width: 150px;"/>
						</div>
					</div>
					<div class="kv ">
						<label>截止时间</label>
						<div class="kv-v">
							 <input type="text" id="jztime" name="jztime" style="width: 150px;"/>
						</div>
					</div>
					<div class="kv " id="supplier_sechbox">
						<label>供应商</label>
						<div class="kv-v">
							 <select  name="supplier" id="supplier" style="width: 150px;"></select>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/selectGoodsType.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<!-- 跳页 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectList.act?current=1';//2019.07.11 lwg 添加current字段识别是当前库存操作
var nowday;
var qxnameArr = ['ck_dqkc_scbb'];
var func = ['exportTable'];
var menuid = parent.menuid;
var type;
$(function() {
// 	getSupplierSelect("supplier");
	getSupplierSelect2("supplier");
	getButtonPowerByPriv(qxnameArr,func);//根据角色查询权限按钮
	getHouseSelect("sshouse");
	if(menuid==603100){
		type = '2';
	 }else{	   
		type = '1';
	 }
	$("#jztime").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
	$("#qstime").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
	/* 初始化表格 */
    OrderDetail();
	/* 页面重置时 计算表格高度 */
	$("#divkdxm").css("height",$(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - 58);	
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight);
     $("#divkdxm").outerHeight(calculateHeight);
 });
});
/* 计算表格高度*/

// function setHeight(){
// 	$(".fixed-table-container").outerHeight($(window).height()-120-$("#totalTable").outerHeight());
// }

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	/* limit: params.limit,
    	offset:params.offset,
    	pageIndex : params.offset/params.limit + 1, */
        goodstype: $('#goodstype').val(),
        sshouse: $('#sshouse').val(),
        jztime: $("#jztime").val(),
        qstime: $("#qstime").val(),
        supplier: $("#supplier").val(),
        queryInput: $('#goodsname').val(),
        type:type
    };
    //console.log(temp.pageSize);
   // console.log(temp.offset);
    //console.log(temp.pageIndex);
    return temp;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,
    	offset:params.offset,
    	pageIndex : params.offset/params.limit + 1,
        goodstype: $('#goodstype').val(),
        sshouse: $('#sshouse').val(),
        jztime: $("#jztime").val(),
        qstime: $("#qstime").val(),
        supplier: $("#supplier").val(),
        queryInput: $('#goodsname').val(),
        type:type
    };
    //console.log(temp.pageSize);
   // console.log(temp.offset);
    //console.log(temp.pageIndex);
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
    $("#supplier").select2("val", " ");//清空
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
	location.href = pageurl+"&flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
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
        /* queryParams: queryParams, */
        dataType: "json",
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 50,
        pageList : [10, 15, 20, 25,50],//可以选择每页大小
      //在表格底部显示分页工具栏 
        singleSelect: true,
        paginationShowPageGo: true,
        queryParamsType:'limit',
        queryParams:queryParamsB,
        cache: false,
        striped: true,
        sidePagination: "server",
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log(JSON.stringify(data)+"--------------data");
        	//layer.alert("当前库存==="+JSON.stringify(data));
        	 $("#total").html(data.total);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.rows.length;i++){
	        	//layer.alert("当前库存==="+JSON.stringify(data.rows[i]));
	        	allmoney += Number(data.rows[i].kcmoney);
	        	nums += Number(data.rows[i].nums);
	        } 
        	//$("#allmoney").html(Number(data.nums[0].kcmoney).toFixed(3));
        	//$("#nums").html(data.nums[0].nums);
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	$("#table").bootstrapTable("resetView",{
        		height:$(window).height()-$(".searchWrap").outerHeight()
        	});
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
			    title: '所属仓库',
			    field: 'sshousename',
			    align: 'center',
			    sortable: true,
			    formatter:function(value){
			    	if(value){
			    		return "<span>"+value+"</span>";
			    	}
			    }
			},
// 			{
// 	          title: '库位',
// 	          field: 'kuwei',
// 	          align: 'center',
// 	          formatter: function(value, row, index) {
// 	                html = '<span class="name" >' + value + '</span>';
// 	                return html;
// 	            }
// 	     	},
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
	            field: 'goodstypename',
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
	            	return '<span style="width:fit-content;postiion:realtive;top:-1px;float:left;text-align:left;">'+value+'</span>';
	            }
			},
			{
			    title: '商品名称',
			    field: 'goodsname',
			    align: 'center',
			    sortable: true,
	            formatter:function(value){
	            	return '<span style="width:fit-content;postiion:realtive;top:-1px;float:left;text-align:left;">'+value+'</span>';
	            }
			},
			{
			    title: '商品规格',
			    field: 'goodsnorms',
			    align: 'center',
			    sortable: true,
	            formatter:function(value){
	            	return '<span style="width:fit-content;postiion:realtive;top:-1px;float:left;text-align:left;">'+value+'</span>';
	            }
			},
			{
	            title: '到期标识',
	            field: 'yxdate',
	            align: 'center',
	            sortable: true,
	            formatter: function(value, row, index) {
	            	if(value){
	            		var stringTime = value;
	                    var timestamp2 = Date.parse(new Date(stringTime));
	                    //console.log(timestamp2);
	                    timestamp2 = timestamp2 / 1000;
	                    var nowday = getNowFormatDate();
	                    var timestamps = Date.parse(nowday);
	            	    timestamps = timestamps / 1000;
	                    //console.log(timestamp2+"---"+timestamps);
	                	var time=timestamp2-timestamps;
	               	 if (time<=864000) {
	                     return '<img class="iscreatelclj" onclick="particulars(\''+row.seq_id+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/caution.jpg/>';
	                 }else{
	                	 return '<img class="iscreatelclj" onclick="particulars(\''+row.seq_id+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/particulars.jpg/>';
	                 }
	            	}else{
	            		return '<span>-</span>';
	            	}
	           }
	        },
	        {
			    title: '批号更改',
			    field: 'nums',
			    align: 'center',
			    sortable: true,
			    formatter: function(value, row, index) {
		        	  if(Number(value)>0){
		        		  return '<img class="iscreatelclj" onclick="updatePh(\''+row.seq_id+'\')" style="cursor: pointer;" src= ' +contextPath + '/static/image/kqdsFront/tag/particulars1.jpg/>';
		        	  }else{
		        		  return '<span>-</span>';
		        	  }
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
		        	  if(Number(value)<0){
		        		  html = '<span style="font-weight:bold;color:red;font-size: 12px !important;" >' + value + '</span>';
		        	  }else{
		        		  html =  '<span>'+value+'</span>' ;
		        	  }
		              return html;
		          }
			},
			{
		      title: '厂家',
		      field: 'suppliername',
		      align: 'center',
		      width : '200px',
		      formatter: function(value, row, index) {
		    	 if(value!=undefined){
		            return '<span>'+value+'</span>';
		    	 }else{
		    		return '<span>--</span>';
		    	 }
		      }
		    },
			{
			    title: '单价',
			    field: 'goodsprice',
			    align: 'center',
			    sortable: true,
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '当前库存金额',
			    field: 'kcmoney',
			    align: 'center',
			    sortable: true,
			    formatter: function(value, row, index) {
		        	  var html = "";
		        	  if(Number(value)<0){
		        		  html = '<span style="color:red;font-size: 12px !important;" >' + value + '</span>';
		        	  }else{
		        		  html = '<span>'+ value +'</span>';
		        	  }
		              return html;
		          }
			},
	       {
	          title: '备注',
	          field: 'remark',
	          align: 'center',
	          formatter: function(value, row, index) {
	                html = '<span class="remark" >' + value + '</span>';
	                return html;
	            }
	       },
	       ]
    });
}
//2019-12-17 展示商品详情
function particulars(obj){
    layer.open({
        type: 2,
        title: '商品详情',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '350px'],
        content: contextPath + '/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailByGoodsuuid.act?goodsuuid='+obj
    });
}
function updatePh(obj){
    layer.open({
        type: 2,
        title: '商品详情',
        maxmin: true,
        shadeClose: false,
        //点击遮罩关闭层
        area: ['90%', '600px'],
        content: contextPath + '/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetail.act?goodsuuid='+obj
    });updatePh
}
</script>
</html>
