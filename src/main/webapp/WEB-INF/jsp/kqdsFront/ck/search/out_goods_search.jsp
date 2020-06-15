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
<title>出库查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/select2.css" />
</head>
<style type="text/css">
td>span.source{
	width:auto !important;
	} 
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
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered" data-height="200"></table>
    </div>

    <div class="tableHd">货品清单</div>
    <div class="tableBox" id="divkdxm" data-height="200">
        <table id="dykdxm" class="table-striped table-condensed table-bordered"></table>
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
       <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
           		<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="openDayin();" >打印</a>
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
		                <label>出库方式</label>
		                <div class="kv-v">
		                    <select  name="outtype" id="outtype">
		                    	<option value="">请选择</option>
		                    	<option value="1">领用出库</option>
		                    	<option value="3">换货出库</option>
		                    	<option value="5">退货出库</option>
		                    	<option value="7">其他出库</option>
		                    </select>
		                </div>
		            </div>
               		<!-- <div class="kv">
						<label>出库仓库</label>
						<div class="kv-v">
							<select  name="outhouse" id="outhouse"></select>
						</div>
					</div> -->
					<div class="kv">
		                <label>供应商</label>
		                <div class="kv-v">
		                    <select  name="supplier" id="supplier"></select>
		                </div>
	           		</div>
					<div class="kv">
		                <label>领用部门</label>
		                <div class="kv-v">
		                 <select name="sqdeptid" id="sqdeptid" ></select>
		                </div>
		            </div>
		            <div class="kv">
						<label>领料人</label>
						<div class="kv-v">
							  <input type="hidden" name="llr" id="llr" placeholder="领料人" title="领料人" class="form-control" />
							  <input type="text"   id="llrDesc" name="llrDesc" placeholder="领料人" readonly style="width: 120px;"onClick="javascript:single_select_user(['llr', 'llrDesc'],'single',1);"  ></input>	
						</div>
					</div>
					 <div class="kv">
						<label>领用医生</label>
						<div class="kv-v">
							  <input type="hidden" name="sqdoctor" id="sqdoctor" placeholder="领用医生" title="领用医生" class="form-control" />
							  <input type="text"   id="sqdoctorDesc" name="sqdoctorDesc" placeholder="领用医生" readonly style="width: 120px;"onClick="javascript:single_select_user(['sqdoctor', 'sqdoctorDesc'],'single',1);"  ></input>	
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
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/ck/ck.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/select2.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '';
var onclickrowOobj2 = "";
var currentRowIsKsll = ""; //当前选中行是否是科室领料的数据，如果是则不允许删除
var nowday;
var menuid = parent.menuid;
var type;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
// 	getDeptSelect("sqdeptid");
	getSupplierSelectKeshi("sqdeptid");//领用部门
	getHouseSelect("outhouse");
	getSupplierSelect2("supplier");
	if(menuid == 603100){
		type = '2';
	}else{
		type = '1';
	}
	pageurl = contextPath + '/KQDS_Ck_Goods_OutAct/inSearchList.act?type=' + type;
    //当前日期
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
    getlist();
    OrderDetail();
    
    $(window).resize(function() {
//      setHeight();
     setWidth();
     
//     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 95; 
     var calculateHeight = $(window).height() - $(".costHd").outerHeight() - $(".searchWrap").outerHeight() - $(".operateModel").outerHeight() - 60;
     $(".fixed-table-container").outerHeight((calculateHeight)/2);
 });
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
        onLoadSuccess:function(){
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
            field: 'outcode',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '出库方式',
            field: 'outtype',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领用部门',
            field: 'sqdeptid',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领料人',
            field: 'llr',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领用医生',
            field: 'sqdoctor',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '供应商',
            field: 'suppliername',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
       /*  {
            title: '出库仓库',
            field: 'housename',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, */
        {
            title: '附加说明',
            field: 'outremark',
            align: 'center',
            
            formatter: function(value, row, index) {
            	html = '<span class="remark" title="' + value + '" title="'+value+'">' + value + '</span>';
                return html;
            }
        },
        {
            title: '摘要',
            field: 'zhaiyao',
            align: 'center',
            
            formatter: function(value, row, index) {
            	html = '<span class="remark" title="' + value + '" title="'+value+'">' + value + '</span>';
                return html;
            }
        },
        {
            title: '出库人',
            field: 'createuser',
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
        },{
            title: '出库时间',
            field: 'cktime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0,10) + '</span>';
                return html;
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        currentRowIsKsll = row.isksll;
        OrderDetail(onclickrowOobj2.outcode); //"\'"+onclickrowOobj2.costno+"\'"
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#supplier").select2("val", " ");//清空
    $("#sqdeptid").select2("val", " ");//清空
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        outtype: $('#outtype').val(),
        outhouse:$('#outhouse').val(),
        supplier:$('#supplier').val(),
        sqdeptid: $('#sqdeptid').val(),
        llr: $('#llr').val(),
        type:type,
        sqdoctor: $('#sqdoctor').val()
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
function OrderDetail(outcode) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="200"></table>');
    var detailurl =contextPath + '/KQDS_Ck_Goods_Out_DetailAct/inSearchList.act?outcode=' + outcode;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].ckmoney);
	        	nums += Number(data[i].outnum);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight();
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
            title: '出库仓库',
            field: 'housename',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
//         {
//             title: '库位',
//             field: 'kuwei',
//             align: 'center',
//             formatter: function(value, row, index) {
//             	html = '<span class="name" title="'+value+'">' + value + '</span>';
//                 return html;
//             }
//         },
        {
            title: '入库编号',
            field: 'addnumber',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '入库时间',
            field: 'addtime',
            align: 'center',
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0,10) + '</span>';
                return html;
            }
        },{
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
            	html = '<span class="source" title="'+value+'">' + value + '</span>';
                return html;
            }
		},{
            title: '商品编号',
            field: 'goodscode',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },{
            title: '商品名称',
            field: 'goodsname',
            align: 'center',
            formatter: function(value, row, index) {
            	html = '<span class="source" title="'+value+'">' + value + '</span>';
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
        {
            title: '单价',
            field: 'outprice',
            align: 'center',
            formatter:function(value){
            	return '<span class="money">'+value+'</span>';
            }
        },
        {
            title: '出库数量',
            field: 'outnum',
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
            title: '批号',
            field: 'ph',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领用时间',
            field: 'cktime',
            align: 'center',
            
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0,10) + '</span>';
                return html;
            }
        },
        {
            title: '领用部门',
            field: 'sqdeptid',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领料人',
            field: 'llr',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '领用医生',
            field: 'sqdoctor',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '申请备注',
            field: 'sqremark',
            align: 'center',
            
            formatter: function(value, row, index) {
                html = '<span class="remark" title="' + value + '">' + value + '</span>';
                return html;
            }
        }]
    });
}
function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的出库单！', {
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("商品出库单");
    var costurl = "";
    // 默认 a5
    costurl = contextPath + '/KQDS_Print_SetAct/toOutGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&outcode=' + onclickrowOobj2.outcode;
    // 弹出打印页面
    parent.layer.open({
        type: 2,
        title: '打印',
        shadeClose: true,
        //点击遮罩关闭层
        area: ['1300px', '550px'],
        content: costurl
    });
}
function goodsEdit(index){
	//判断是否科室领料
	if(currentRowIsKsll == 1) {
		layer.alert("科室领料的数据禁止修改!");return;
	}
	goodsDetail = $('#dykdxm').bootstrapTable('getData')[index];
	// 弹出打印页面
	layer.open({
		type: 2,
		title: '修改出库明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: contextPath+'/KQDS_Ck_Goods_Out_DetailAct/toCkOutGoodsDetailEdit.act'
	});
}
function delAll(id){
	//判断是否科室领料
	if(currentRowIsKsll == 1) {
		layer.alert("科室领料的数据禁止删除!");return;
	}
	var paramOrder ={
			outseqId : id//入库单主键
	}
	//询问框
	  layer.confirm('确定删除？', {
	    btn: ['删除','放弃'] //按钮
	  }, function(){
		  var url = contextPath + '/KQDS_Ck_Goods_OutAct/deleteAllGoodsOut.act';
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
