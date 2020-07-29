<!--wl整理  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String goodsid = request.getParameter("goodsid");
	if(goodsid == null){
		goodsid = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>收发存查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<style type="text/css">
	.fixed-table-container{
		border:none;
	}
	/* .tableBox{
		border:1px solid #ddd;
	}
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		border-top-left-radius: 6px;
		border-top-right-radius: 6px;
		overflow: hidden;
	} */
	.fixed-table-container {
	     border-left: 1px solid #ddd;
	     border-right: 1px solid #ddd;
	     border-bottom: 1px solid #ddd;
	     border-radius: 6px;
	     overflow: hidden;
 	}
</style>
<body>
<div id="container">
    <div class="tableHd">货品清单</div>
    <div class="tableBox" id="divkdxm">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>
    <div class="tableBox">
    	<table style="width: 100%">
       		<tr>
 				<td width="10%"><span style="color:#00A6C0;">共有记录：<lable id="total">0</lable>条</span></td>
 				<td width="10%"><span style="color:#00A6C0;">期初数量：<lable id="qcnums">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">期初金额：<lable id="qcmoney">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">入库数量：<lable id="innums">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">入库金额：<lable id="rkmoney">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">出库数量：<lable id="outnums">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">出库金额：<lable id="ckmoney">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">期末数量：<lable id="qmnums">0</lable></span></td>
 				<td width="10%"><span style="color:#00A6C0;">期末金额：<lable id="qmmoney">0</lable></span></td>
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
						<label>录单日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
               		<div class="kv">
						<label>收货仓库</label>
						<div class="kv-v">
							<select  name="house" id="house"></select>
						</div>
					</div>
					<div class="kv" id="goodsnameDiv" style="display: none;">
						<label>模糊查询</label>
						<div class="kv-v">
							  <input type="text" name="goodsname" id="goodsname" placeholder="商品名称/编号" style="width: 150px;" />
						</div>
					</div>
                </div>
            </div>
        </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var goodsid = "<%=goodsid%>";
var pageurl = contextPath + '/KQDS_Ck_GoodsAct/selectListSfc.act?goodsid=' + goodsid;
var nowday;
var qxnameArr = ['ck_sfc_scbb'];
var func = ['exportTable'];
var isClick = true;

$(function() {
	getButtonPowerByPriv(qxnameArr,func);//根据角色查询权限按钮
    getHouseSelect("house");
    //当前日期
    if (goodsid == "") {
        $("#goodsnameDiv").show();
    }
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
// 	setHeight();
// 	$("#divkdxm").css("height",$(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - 58);
    OrderDetail(goodsid);

    $(window).resize(function() {
//      setHeight();
     setWidth();

//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95;
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight(calculateHeight);
 });
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        house: $('#house').val(),
        goodsname: $('#goodsname').val()
    };
    return temp;
}
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,
    	offset:params.offset,
    	pageIndex : params.offset/params.limit + 1,
    	starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        house: $('#house').val(),
        goodsname: $('#goodsname').val()
    };
    return temp;
}
$('#query').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });

    $(".fixed-table-loading").css("display","block"); // 查询时间比较长时，如果第一次查询没有返回的情况下，发起第二次查询，仍要显示 Loading, please wait...
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});

var loadIndex='';
function download() {
	layer.msg('数据导出中，请等待');
	//loadIndex = layer.load(0, {shade: false});
	isClick = false;
}
function disload() {
	layer.close(loadIndex);
	layer.msg('数据导出完毕');
	isClick = true;
}
//导出
function exportTable() {
	if(isClick) {
		isClick = false;
		// console.log("生成报表")
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
		var url = pageurl+"&flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
		download();
		var xhr = new XMLHttpRequest();
		xhr.open('GET', url, true);    // 也可用POST方式
		xhr.responseType = "blob";
		xhr.onload = function () {
			if (this.status === 200) {
				var blob = this.response;
				// if (navigator.msSaveBlob == null) {
				var a = document.createElement('a');
				//var headerName = xhr.getResponseHeader("Content-disposition");
				//var fileName = decodeURIComponent(headerName).substring(20);
				a.download = "物资供应商";
				a.href = URL.createObjectURL(blob);
				$("body").append(a);    // 修复firefox中无法触发click
				a.click();
				URL.revokeObjectURL(a.href);
				$(a).remove();
				// } else {
				//     navigator.msSaveBlob(blob, "信息查询");
				// }
			}
			disload();
		};
		xhr.send();
	}
}
function setHeight(){
	var windowHeight=$(window).outerHeight();
	var tableHeight=$(window).outerHeight()-$(".searchWrap").outerHeight() - $(".tableHd").outerHeight() + 25;
	$("#table").bootstrapTable("resetView",{
		height:tableHeight
	});
}
function OrderDetail(goodsid) {
	$("#table").attr("data-height", $(window).height() - $(".searchWrap").outerHeight() - $(".tableHd").outerHeight() - 10);
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
//      queryParams: queryParams,
        contentType : "application/x-www-form-urlencoded",   //必须有
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
      //在表格底部显示分页工具栏
        singleSelect: true,
        paginationShowPageGo: true,
        queryParamsType:'limit',
        queryParams:queryParams,
        queryParams:queryParamsB,
        cache: false,
        striped: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	$("#total").html(data.length);
	        var qcnums=0,qcmoney=0,innums=0,rkmoney=0,outnums=0,ckmoney=0,qmnums=0,qmmoney=0;
	        for(var i=0;i<data.length;i++){
	        	qcnums += Number(data[i].qcnums);
	        	qcmoney += Number(data[i].qcmoney);
	        	innums += Number(data[i].rknums);
	        	rkmoney += Number(data[i].rkmoney);
	        	outnums += Number(data[i].cknums);
	        	ckmoney += Number(data[i].ckmoney);
	        	qmnums += Number(data[i].qmnums);
	        	qmmoney += Number(data[i].qmmoney);
	        }
	    	$("#qcnums").html(qcnums);
	    	$("#qcmoney").html(qcmoney.toFixed(3));
	    	$("#innums").html(innums);
	    	$("#rkmoney").html(rkmoney.toFixed(3));
	    	$("#outnums").html(outnums);
	    	$("#ckmoney").html(ckmoney.toFixed(3));
	    	$("#qmnums").html(qmnums);
	    	$("#qmmoney").html(qmmoney.toFixed(3));
	    	setHeight();
	    	/*表格载入时，设置表头的宽度 */
	        setTableHeaderWidth(".tableBox");
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
            		return '<span>'+value+'</span>';
            	}else{
            		return '<span>-</span>';
            	}

            }
        },
//         {
// 	          title: '库位',
// 	          field: 'kuwei',
// 	          align: 'center',
// 	          formatter: function(value, row, index) {
// 	                html = '<span class="source" >' + value + '</span>';
// 	                return html;
// 	            }
// 	       },
	       {
	            title: '一级类别',
	            field: 'firsttype',
	            align: 'center',
	            formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
	        },
	        {
	            title: '二级类别',
	            field: 'goodstypename',
	            align: 'center',
	            formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
	        }, {
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            sortable: true,
            formatter:function(value){
            	return '<span style="width:fit-content;position:relative;top:-1px;float:left;text-align:left;">'+value+'</span>'
            }
        },{
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span style="width:fit-content;position:relative;top:-1px;float:left;text-align:left;" title="'+value+'">' + value + '</span>';
                return html;
            }
        },
        {
            title: '规格',
            field: 'goodsnorms',
            align: 'center',
            sortable: true,
            formatter:function(value,row,index){
            	return '<span style="width:fit-content;position:relative;top:-1px;float:left;text-align:left;">'+value+'</span>';
            }
        },
        {
            title: '单位',
            field: 'goodsunit',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '期初数量',
            field: 'qcnums',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '期初单价',
            field: 'qcsprice',
            align: 'center',
            formatter:function(value){
            	return '<span class="money">'+value+'</span>'
            }
        },
        {
            title: '期初金额',
            field: 'qcmoney',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '入库数量',
            field: 'rknums',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '入库单价',
            field: 'rksprice',
            align: 'center',
            formatter:function(value){
            	return '<span class="money">'+value+'</span>'
            }
        },
        {
            title: '入库金额',
            field: 'rkmoney',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '出库数量',
            field: 'cknums',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '出库单价',
            field: 'cksprice',
            align: 'center',
            formatter:function(value){
            	return '<span class="money">'+value+'</span>'
            }
        },
        {
            title: '出库金额',
            field: 'ckmoney',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '期末数量',
            field: 'qmnums',
            align: 'center',
            formatter:function(value){
            	if(Number(value)<0){
            		return '<span class="money" style="color:red;">'+value+'</span>';
            	}else{
            		return '<span class="money">'+value+'</span>';
            	}
            }
        },
        {
            title: '期末单价',
            field: 'qmsprice',
            align: 'center',
            formatter:function(value){
            	return '<span class="money">'+value+'</span>'
            }
        },
        {
            title: '期末金额',
            field: 'qmmoney',
            align: 'center',
            formatter: function(value, row, index) {
          	  var html = "";
          	  if(Number(value)<0){
          		  html = '<span style="position:relative;top:2px;color:red;font-size: 14px !important;" >' + value + '</span>';
          	  }else{
          		  html =  '<span>'+value+'</span>' ;
          	  }
                return html;
            }
        }]
    });
}
</script>
</html>
