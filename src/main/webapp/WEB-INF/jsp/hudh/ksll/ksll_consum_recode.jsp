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
<title>内部消耗记录</title>
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
                <a href="javascript:void(0);" class="kqdsCommonBtn" id="query" onclick="refresh()">查询</a>
                <!-- <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="openDayin();" >今日药单</a> -->
            </div>
            <div class="formBox">
               	<div class="kv">
               		<div class="kv">
						<label>出库日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
					<div class="kv">
		                <label>出库科室</label>
		                <div class="kv-v">
		                    <select id="deptpart" name="deptpart"></select>
		                </div>
	           		</div>
					<div class="kv">
		                <label>出库人</label>
		                <div class="kv-v">
		                    <input type="text"  name="hzname" id="hzname"/>
		                </div>
	           		</div>
                </div>
            </div>
        </div> 
    <div class="tableBox">
        <table id="table" class="table-striped table-condensed table-bordered"></table>
    </div>

    <div class="tableHd">出库物品明细</div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ykzx/yk.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/ksll/ksll.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var apppath = apppath();
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = apppath + '/HUDH_KSllAct/findReplaceMentList.act';
var onclickrowOobj2 = "";
var goodsDetail = "";
var nowday;
//允许删除仓库出入库记录
var canDelCk = "<%=canDelCk%>";
$(function() {
	initDept($("#deptpart"));
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
        onLoadSuccess:function(){
        	setHeight();
        },
        columns: [
		{
    		title: '操作',
    		field: ' ',
    		align: 'center',
    		formatter: function(value, row, index) {
    			var html = '';
    			if(row.status == '0'){
    				html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:red;" onclick="updateStus(\'' + row.id + '\',2)">删除</a></span>';
    			}else {
    				html = '<span><a href="javascript:void(0);" mce_href="javascript:void(0);" style="color:black;" onclick="">--</a> </span>';
    			}
       		 	return html;
    		}
		},
		{
            title: 'id',
            field: 'id',
            visible: false
        },
        {
            title: '出库科室',
            field: 'deptpartname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '出库人',
            field: 'creatorname',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '出库时间',
            field: 'createtime',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '备注',
            field: 'remark',
            align: 'center',
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '出库方式',
            field: 'type',
            align: 'center',
            formatter:function(value){
            	if(value == '1'){
            		return '<span style="color:green">科室退还</span>';
            	}else if(value == '2'){
            		return '<span style="color:green">内部消耗</span>';
            	}
            }
        }
       ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj2 = $('#table').bootstrapTable('getData')[index];
        OrderDetail(onclickrowOobj2.id); 
    });
}
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
});
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        deptpart: $('#deptpart').val(),
        hzname: $('#hzname').val(),
        status: $('#status').val(),
        type: 2
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
function OrderDetail(parentId) {
    $("#divkdxm").html('<table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="230"></table>');
    var detailurl = apppath + '/HUDH_KSllAct/findReplaceMentDetailByParentId.act?parentId='+parentId;
    $("#dykdxm").bootstrapTable({
        url: detailurl,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	$("#total").html(data.length);
        	var nums=0,allmoney=0;
	        for(var i=0;i<data.length;i++){
	        	allmoney += Number(data[i].subtotal);
	        	nums += Number(data[i].num);
	        }
        	$("#allmoney").html(allmoney.toFixed(3));
        	$("#nums").html(nums);
        	setHeight();
        	/*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox1");
        },
        columns: [
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
			    formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '规格',
			    field: 'goodsnorms',
			    align: 'center',
	            formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			},
			{
			    title: '出库数量',
			    field: 'cknums',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
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
			    title: '单位',
			    field: 'goodsunit',
			    align: 'center',
			    
	            formatter:function(value){
	            	return '<span>'+value+'</span>';
	            }
			},
			{
			    title: '附加说明',
			    field: 'goodsremark',
			    align: 'center',
			    formatter:function(value){
	            	return '<span class="money">'+value+'</span>';
	            }
			}]
    });
}

function openDayin() {
    if (onclickrowOobj2 == "") {
        layer.alert('请选择需要打印的入库单！', {
            
        });
        return false;
    }
    // 先查询fyqrd页面在打印设置里 是a4还是a5，如果是a4则跳转a4界面
    var printSet = getPrintType("药品入库单");
    var costurl = "";
    // 默认 a5
    costurl = apppath + '/KQDS_Print_SetAct/toInGoodsPrintPage.act?printpage='+printSet.printurl+'&printType=' + printSet.printtype + '&incode=' + onclickrowOobj2.incode;
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
		title: '修改入库明细',
		shadeClose: true, //点击遮罩关闭层
		area : ['90%' , '90%'],
		content: apppath+'/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailEdit.act'
	});
}
//库房审批修改数据状态
function updateStus(id,flag){
	$.axseSubmit(apppath + '/HUDH_KSllAct/updateReplacementStatus.act', {id : id,status : flag} ,function() {},function(data) {
		layer.alert(data.retMsrg, {
            end: function() {
            	refresh();
            } 
        });
	},function(r){
	 	layer.alert('操作失败，请联系管理员');
 	});
}
</script>
</html>
