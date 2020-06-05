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
            	<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="openDayin();" >打印</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query" onclick="refresh()">查询</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="sendBack" onclick="sendBack()">退还</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="sendBackSearch" onclick="sendBackSearch()">退还查询</a>
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
					<!-- <div class="kv">
		                <label>入库方式</label>
		                <div class="kv-v">
		                    <select  name="intype" id="intype">
		                    	<option value="">请选择</option>
		                    	<option value="0">采购入库</option>
		                    	<option value="2">换货入库</option>
		                    	<option value="4">其他入库</option>
		                    </select>
		                </div>
		            </div> -->
               		<!-- 
               		<div class="kv">
						<label>入库仓库</label>
						<div class="kv-v">
							<select  name="inhouse" id="inhouse"></select>
						</div>
					</div> -->
					<div class="kv">
		                <label>供应商</label>
		                <div class="kv-v">
		                    <select  name="supplier" id="supplier"></select>
		                </div>
	           		</div>
	           		 <div class="kv" >
						<label>单据编号</label>
						<div class="kv-v">
							  <input type="text" name="incode" id="incode"  class="form-control" />
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
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">商品列表</div>
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
var pageurl = contextPath + '/HUDH_Goods_DoctorPickInAct/findAllGoodsDoctorPick.act';
var onclickrowOobj2 = "";
var onclickrowOobj3 = "";
var goodsuuid1 = "";
var id = "";//获取领料明细表id
//alert(goodsuuid1);
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
    getlist();
    OrderDetail();
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
function getlist() {
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        onLoadSuccess:function(data){
        	//alert(JSON.stringify(data));
        	setHeight();
        },
        columns: [
        /* {
		    title: '操作',
		    field: ' ',
		    align: 'center',
		    
		    formatter: function(value, row, index) {
		    	var html = '';
		    	if(canDelCk == "1"){
			    	html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="delAll(\'' + row.seqId + '\')">删除</a> </span>';
		    	}
		        return html;
		    }
		}, */
		{
            title: '单据编号',
            field: 'incode',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },/* {
            title: '入库方式',
            field: 'intype',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, */
        {
            title: '供应商',
            field: 'suppliername',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        /* {
            title: '入库仓库',
            field: 'housename',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, */
        {
            title: '附加说明',
            field: 'inremark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '摘要',
            field: 'summary',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领料人',
            field: 'userName',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '患者',
            field: 'userdocument',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '录单时间',
            field: 'createtime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '领料时间',
            field: 'rktime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '进货时间',
            field: 'stocktime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span >' + value.substring(0,10) + '</span>';
                return html;
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.incode); 
    });
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
        incode:$('#incode').val(),
        stock_starttime: $('#stock_starttime').val(),
        stock_endtime:$('#stock_endtime').val()
    };
    return temp;
}
function refresh(){
	//点击查询时，置空入库明细列表
	$('#dykdxm').find('tbody').html("");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function OrderDetail(incode) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = contextPath + '/GoodsDoctorPickInDetailAct/findDoctorPickInDetailByIncode.act?incode='+incode;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	//alert(JSON.stringify(data));
        	//console.log(data);
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].amount);
	        	nums += Number(data[i].quantity);
	        }
        	$("#allmoney").html(allmoney.toFixed(2));
        	$("#nums").html(nums);
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
			    title: '科室',
			    field: 'deptpartname',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			/* {
	            title: '库位',
	            field: 'kuwei',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
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
			    field: 'goodstypename',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	            	html = '<span class="name" title="'+value+'">' + value + '</span>';
	                return html;
	            }
			}, */
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
	                html = '<span class="name" style="width: 150px;" title="'+value+'">' + value + '</span>';
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
			    title: '领料单价',
			    field: 'nuitprice',
			    align: 'center',
	            formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			}, */
			{
			    title: '已领数量',
			    field: 'quantity',
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
			    title: '入库金额',
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
			    title: '有效期',
			    field: 'createdate',
			    align: 'center',
			    
			    formatter: function(value, row, index) {
	                html = '<span title="'+value+'">' + value + '</span>';
	                return html;
	            }
			},
			{
	            title: '批号',
	            field: 'batchnum',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	return '<span>'+value+'</span>';
	            	/* if(value){
	            		html = '<span class="remark" title="' + value + '">' + value + '</span>';
	                    return html;
	            	} */
	            }
	        },
	        {
	            title: '注册证号',
	            field: 'regisnum',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	if(value){
	            		/* html = '<span class="remark" title="' + value + '">' + value + '</span>';
	                    return html; */
	            		return '<span>'+value+'</span>';
	            	}
	            }
	        },
	        {
	            title: '产地',
	            field: 'productionplace',
	            align: 'center',
	            
	            formatter: function(value, row, index) {
	            	if(value){
	            		/* html = '<span class="remark" title="' + value + '">' + value + '</span>';
	                    return html; */
	            		return '<span>'+value+'</span>';
	            	}
	            }
	        },
			{
			    title: '入库备注',
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
        var index = $('#dykdxm').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj3 = $('#dykdxm').bootstrapTable('getData')[index];
        goodsuuid1 = onclickrowOobj3.goodsuuid;
        id = onclickrowOobj3.seqId;
        //alert(id);
    });
}
/**
 * 退还商品到科室
 */
function sendBack() {
	if (onclickrowOobj3 == "") {
        layer.alert('请选择需要退还的商品！', {
            
        });
        return false;
    }
	layer.open({
		type: 2,
		title: '商品退还',
		shadeClose: true, //点击遮罩关闭层
		area : ['80%' , '80%'],
		content: contextPath+'/HUDH_Goods_DoctorPickViewAct/toGoodsPickSendBack.act?goodsuuid1=' + goodsuuid1 + '&id=' + id
		/* success : function() {//layer 回调
			var url = contextPath + "/GoodsDoctorPickInDetailAct/findDoctorPickInDetailById.act";
			var param = {id : id};
			$.axseSubmit(url, param, function() {},
			function(r) {
				alert(JSON.stringify(r));
			},
			function() {
				layer.alert('退还失败' );
			});
		} */
	});
}

/**
 * 退还商品查询
 */
function sendBackSearch() {
	parent.layer.open({
		type: 2,
		title: '修改领料明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['80%' , '80%'],
		content: contextPath+'/HUDH_Goods_DoctorPickViewAct/toSendBackGoodsSearch.act'
	});
}
function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的入库单！', {
            
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("领料明细单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + onclickrowOobj2.incode;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1000px', '550px'],
        content: costurl
    });
}
function goodsEdit(index){
	goodsDetail = $('#dykdxm').bootstrapTable('getData')[index];
	// 弹出打印页面
	layer.open({
		type: 2,
		title: '修改领料明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: contextPath+'/HUDH_Goods_DoctorPickViewAct/toEditPickGoodsDetail.act'
	});
}
function delAll(id){
	var paramOrder ={
		inseqId : id//入库单主键
	}
	//询问框
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var url = contextPath + '/HUDH_Goods_DoctorPickInAct/deleteGoodsDoctorPickByIncode.act';
		    $.axse(url, paramOrder,
		    function(r) {
		        if (r.retState == "0") {
		            layer.alert('操作成功', {
		                  
		                end: function() {
		                	refresh();
		                }
		            });
		        } else{
		        	layer.alert(r.retMsrg, {
			              
			        });
		        }
		    },
		    function() {
		        layer.alert('请求失败', {
		              
		        });
		    });
	  });
 }
</script>
</html>
